package de.anjaro.test.adapter;

import java.util.List;
import java.util.Properties;

import de.anjaro.config.IConfigService;
import de.anjaro.controller.IAnjaroController;
import de.anjaro.feature.IFeature;
import de.anjaro.remote.IInboundAdapter;
import de.anjaro.remote.IOutboundAdapter;
import de.anjaro.remote.impl.SocketInboundAdapter;

public class SocketAdapterTestConfig implements IConfigService {

	private final Properties props;
	private final IAnjaroController controller = new TestController();

	public SocketAdapterTestConfig() {
		this.props = new Properties();
		this.props.setProperty("hostname", "localhost");
		this.props.setProperty("port", "4321");
		this.props.setProperty(SocketInboundAdapter.LISTENER_PORT, "4321");
	}

	@Override
	public List<IFeature> getFeatureList() {
		return null;
	}

	@Override
	public List<IInboundAdapter> getInboundAdapterList() {
		return null;
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
	public String getProperty(final String pKey) {
		return this.props.getProperty(pKey);
	}

}
