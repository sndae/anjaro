package de.anjaro.controller.impl;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.anjaro.config.IConfigService;
import de.anjaro.controller.IAnjaroController;
import de.anjaro.feature.IFeature;
import de.anjaro.model.Command;
import de.anjaro.model.CommandResult;
import de.anjaro.remote.IAdapter;
import de.anjaro.util.CommandResultHelper;
import de.anjaro.util.DefaultAnjaroError;
import de.anjaro.util.IShutdownListener;

public class DefaultControllerImpl implements IAnjaroController {

	private static final Logger LOG = Logger.getLogger(DefaultControllerImpl.class.getName());

	private IConfigService configService;

	private Map<String, IFeature> featureMap;

	private ExecutorService executorService;

	private final List<IShutdownListener> shutDownListenerList = new ArrayList<IShutdownListener>();

	@Override
	public void init(final IConfigService pConfigService) throws Exception {
		LOG.entering(DefaultControllerImpl.class.getName(), "init");
		LOG.fine("Init features");
		this.configService = pConfigService;
		this.featureMap = new HashMap<String, IFeature>();
		for (final IFeature feature : this.configService.getFeatureList()) {
			LOG.fine("Initialize " + feature.getName());
			feature.init(this);
			this.featureMap.put(feature.getName(), feature);
		}
		LOG.fine("Init adapters");
		final List<IAdapter> adapterList = this.configService.getAdapterList();
		if (adapterList != null && adapterList.size() > 0) {
			this.executorService = Executors.newFixedThreadPool(adapterList.size());
			for (final IAdapter<? extends Object> adapter : adapterList) {
				LOG.fine("Initialize " + adapter.getName());
				adapter.init(this);
				this.executorService.execute(adapter);
			}
		}
		LOG.exiting(DefaultControllerImpl.class.getName(), "init");
	}

	@Override
	public void shutdown() {
		LOG.entering(DefaultControllerImpl.class.getName(), "shutdown");
		LOG.fine("Shutdown adapters");
		if (this.configService.getAdapterList() != null && !this.configService.getAdapterList().isEmpty()) {
			for (final IAdapter<? extends Object> adapter : this.configService.getAdapterList()) {
				LOG.fine("Shutdown" + adapter.getName());
				adapter.shutDown();
			}
			this.executorService.shutdown();
			try {
				this.executorService.awaitTermination(2000, TimeUnit.MILLISECONDS);
			} catch (final InterruptedException e) {
				LOG.throwing(this.getClass().getName(), "shutdown", e);
			}
		}
		LOG.fine("Shutdown features");
		for (final IFeature feature : this.configService.getFeatureList()) {
			LOG.fine("Shutdown " + feature.getName());
			feature.shutDown();
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
		if (featureName == null || this.featureMap.get(featureName) == null) {
			result = CommandResultHelper.createResult(DefaultAnjaroError.featureNotAvailable, new Object[] { featureName });
			LOG.severe(result.getErrorMessage());
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
					method = feature.getClass().getMethod(pCommand.getMethod(), (Class<?>) null);
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
}
