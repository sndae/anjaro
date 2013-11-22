package de.anjaro.template;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

import de.anjaro.config.IConfigService;
import de.anjaro.controller.IAnjaroController;
import de.anjaro.controller.impl.DefaultControllerImpl;
import de.anjaro.dispatcher.impl.SimpleStringCommandDispatcher;
import de.anjaro.feature.IFeature;
import de.anjaro.feature.impl.RaspberryLedLightFeature;
import de.anjaro.feature.impl.RaspberryServoMotorFeature;
import de.anjaro.feature.module.ISensor;
import de.anjaro.remote.IInboundAdapter;
import de.anjaro.remote.IOutboundAdapter;
import de.anjaro.remote.impl.SocketInboundAdapter;

public class SocketInboundRobot implements IConfigService {

	private Properties props;
	private final IAnjaroController controller = new DefaultControllerImpl();
	private static final Logger LOG = Logger.getLogger(SocketInboundRobot.class.getName());

	@Override
	public List<IFeature> getFeatureList() {
		final List<IFeature> result = new ArrayList<IFeature>();
		result.add(new RaspberryServoMotorFeature());
		result.add(new RaspberryLedLightFeature());
		return result;
	}

	@Override
	public List<IInboundAdapter> getInboundAdapterList() {
		final List<IInboundAdapter> resultList = new ArrayList<IInboundAdapter>();
		final SocketInboundAdapter socketAdapter = new SocketInboundAdapter();
		socketAdapter.setCommandDispatcher(new SimpleStringCommandDispatcher());
		resultList.add(socketAdapter);
		return resultList;
	}

	@Override
	public IOutboundAdapter getOutboundAdapter() {
		return null;
	}

	@Override
	public IAnjaroController getController() {
		return this.controller;
	}

	@Override
	public synchronized String getProperty(final String pKey) {
		if (this.props == null) {
			try {
				this.props = new Properties();
				this.props.load(this.getClass().getClassLoader().getResourceAsStream("raspberry-config.properties"));
			} catch (final IOException e) {
				// TODO: handle exception properly
				LOG.throwing(this.getClass().getName(), "getProperty", e);
				return null;
			}
		}
		return this.props.getProperty(pKey);
	}

	@Override
	public Map<String, ISensor> getSensorMap() {
		// TODO Auto-generated method stub
		return null;
	}

}
