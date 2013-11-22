package de.anjaro.controller.impl;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.anjaro.config.IConfigService;
import de.anjaro.controller.IAnjaroController;
import de.anjaro.event.IEvent;
import de.anjaro.event.IEventListener;
import de.anjaro.feature.IFeature;
import de.anjaro.feature.module.ISensor;
import de.anjaro.model.Command;
import de.anjaro.model.CommandResult;
import de.anjaro.remote.IAdapter;
import de.anjaro.remote.IInboundAdapter;
import de.anjaro.remote.IOutboundAdapter;
import de.anjaro.util.CommandResultHelper;
import de.anjaro.util.DefaultAnjaroError;
import de.anjaro.util.IShutdownListener;

public class DefaultControllerImpl implements IAnjaroController {

	private static final Logger LOG = Logger.getLogger(DefaultControllerImpl.class.getName());

	private IConfigService configService;

	private Map<String, IFeature> featureMap;

	private ExecutorService inboundAdapterExecutorService;

	private ExecutorService sensorExecutorService;

	private IOutboundAdapter outboundAdapter;

	private final Map<String, List<IEventListener>> eventMap = new HashMap<String, List<IEventListener>>();

	private final List<IShutdownListener> shutDownListenerList = new ArrayList<IShutdownListener>();

	private Map<String, ISensor> sensorMap = new HashMap<String, ISensor>();

	@Override
	public void init(final IConfigService pConfigService) throws Exception {
		LOG.entering(DefaultControllerImpl.class.getName(), "init");
		this.configService = pConfigService;
		this.sensorMap = this.configService.getSensorMap() == null ? new HashMap<String, ISensor>() : this.configService.getSensorMap();


		LOG.fine("Init features");
		this.featureMap = new HashMap<String, IFeature>();
		for (final IFeature feature : this.configService.getFeatureList()) {
			LOG.fine("Initialize " + feature.getName());
			feature.init(this);
			this.featureMap.put(feature.getName(), feature);
		}
		LOG.fine("Init inbound adapters");
		final List<IInboundAdapter> adapterList = this.configService.getInboundAdapterList();
		if ((adapterList != null) && (adapterList.size() > 0)) {
			this.inboundAdapterExecutorService = Executors.newFixedThreadPool(adapterList.size());
			for (final IInboundAdapter<? extends Object> adapter : adapterList) {
				LOG.fine("Initialize " + adapter.getName());
				adapter.init(pConfigService);
				this.inboundAdapterExecutorService.execute(adapter);
			}
		}
		LOG.fine("Init outbound adapters");
		if (this.configService.getOutboundAdapter() != null) {
			this.outboundAdapter = this.configService.getOutboundAdapter();
			this.outboundAdapter.init(this.configService);
		}

		LOG.fine("Init Sensors");
		if (this.sensorMap.size() > 0) {
			this.sensorExecutorService = Executors.newFixedThreadPool(this.sensorMap.size());
			for (final String sensorId : this.sensorMap.keySet()) {
				LOG.fine("Init sensor: " + sensorId);
				final ISensor sensor = this.sensorMap.get(sensorId);
				sensor.init(this);
				this.sensorExecutorService.execute(sensor);
			}
		}

		LOG.exiting(DefaultControllerImpl.class.getName(), "init");
	}

	@Override
	public void shutdown() {
		LOG.entering(DefaultControllerImpl.class.getName(), "shutdown");
		LOG.fine("Shutdown adapters");
		if ((this.configService.getInboundAdapterList() != null) && !this.configService.getInboundAdapterList().isEmpty()) {
			for (final IAdapter<? extends Object> adapter : this.configService.getInboundAdapterList()) {
				LOG.fine("Shutdown" + adapter.getName());
				adapter.shutDown();
			}
			this.inboundAdapterExecutorService.shutdown();
			try {
				this.inboundAdapterExecutorService.awaitTermination(2000, TimeUnit.MILLISECONDS);
			} catch (final InterruptedException e) {
				LOG.throwing(this.getClass().getName(), "shutdown", e);
			}
		}
		LOG.fine("Shutdown features");
		for (final IFeature feature : this.configService.getFeatureList()) {
			LOG.fine("Shutdown " + feature.getName());
			feature.shutDown();
		}

		LOG.fine("Shutdown sensors");
		for (final String sensor : this.configService.getSensorMap().keySet()) {
			this.configService.getSensorMap().get(sensor).shuthdown();
		}
		for (final IShutdownListener sdl : this.shutDownListenerList) {
			sdl.shutDown();
		}
	}



	@Override
	public void addShutdownListener(final IShutdownListener pShutdownListener) {
		this.shutDownListenerList.add(pShutdownListener);
	}

	@Override
	public CommandResult execute(final Command pCommand) {
		LOG.entering(DefaultControllerImpl.class.getName(), "execute");
		final String featureName = pCommand.getFeatureName();
		CommandResult result = new CommandResult();
		if ((featureName == null) || (this.featureMap.get(featureName) == null)) {
			if ((featureName != null) && (this.outboundAdapter != null)) {
				result = this.outboundAdapter.sendCommand(pCommand);
			} else {
				result = CommandResultHelper.createResult(DefaultAnjaroError.featureNotAvailable, new Object[] { featureName });
				LOG.severe(result.getErrorMessage());
			}
		} else {
			final IFeature feature = this.featureMap.get(featureName);
			final String methodString = pCommand.getMethod();
			try {
				if (LOG.isLoggable(Level.FINER)) {
					LOG.finer("Method:" + methodString);
				}
				Method method = null;
				if ((pCommand.getParams() != null) && (pCommand.getParams().length > 0)) {
					final Class<?>[] clazzArray = new Class[pCommand.getParams().length];
					for (int i = 0; i < pCommand.getParams().length; i++) {
						if (LOG.isLoggable(Level.FINER)) {
							LOG.finer("Param class" + pCommand.getParams()[i].getClass().getName());
						}
						clazzArray[i] = pCommand.getParams()[i].getClass();
					}
					method = feature.getClass().getMethod(pCommand.getMethod(), clazzArray);
				} else {
					method = feature.getClass().getMethod(pCommand.getMethod(), null);
				}

				final Serializable objResult = (Serializable) method.invoke(feature, (Object[]) pCommand.getParams());
				result.setSuccessResult(objResult);
			} catch (final Exception e) {
				result = CommandResultHelper.createResult(DefaultAnjaroError.unableToCallMethod, new Object[] { methodString, e.getMessage() });
				LOG.severe(result.getErrorMessage());
				LOG.throwing(this.getClass().getName(), "execute", e);
			}
		}
		LOG.exiting(DefaultControllerImpl.class.getName(), "execute");
		return result;
	}

	@Override
	public void fireEvent(final IEvent<?> pEvent) {
		LOG.entering(DefaultControllerImpl.class.getName(), "fireEvent");
		if (LOG.isLoggable(Level.FINER)) {
			LOG.finer("Event was fired: " +  pEvent.getName() + " from Sensor: " + pEvent.getSensorId() + " and value: " + pEvent.getValue());
		}
		final List<IEventListener> eventListenerList = this.eventMap.get(pEvent.getName());
		if (eventListenerList != null) {
			for (final IEventListener el : eventListenerList) {
				el.onEvent(pEvent);
			}
		} else {
			LOG.warning("No eventlistener found for event" + pEvent.toString());
		}
		LOG.exiting(DefaultControllerImpl.class.getName(), "fireEvent");

	}

	@Override
	public void registerEventListener(final String pEventName, final IEventListener pListener) {
		LOG.entering(DefaultControllerImpl.class.getName(), "registerEventListener");
		List<IEventListener> listenerList = this.eventMap.get(pEventName);
		if (this.eventMap.get(pEventName) == null) {
			listenerList = new ArrayList<IEventListener>();
			this.eventMap.put(pEventName, listenerList);
		}
		listenerList.add(pListener);
		LOG.exiting(DefaultControllerImpl.class.getName(), "registerEventListener");
	}



	@Override
	public void adjustSensor(final String pSensorId, final Properties pValues) {
		LOG.entering(DefaultControllerImpl.class.getName(), "adjustSensor");
		final ISensor sensor = this.sensorMap.get(pSensorId);
		if (sensor != null) {
			sensor.adjust(pValues);
		} else {
			LOG.warning("Unable to find Sensor with id: " + pSensorId);
		}
		LOG.exiting(DefaultControllerImpl.class.getName(), "adjustSensor");
	}


}
