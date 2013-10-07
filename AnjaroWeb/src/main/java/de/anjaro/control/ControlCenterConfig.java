package de.anjaro.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import de.anjaro.config.IConfigService;
import de.anjaro.controller.IAnjaroController;
import de.anjaro.controller.impl.DefaultControllerImpl;
import de.anjaro.feature.IFeature;
import de.anjaro.remote.IInboundAdapter;
import de.anjaro.remote.IOutboundAdapter;
import de.anjaro.remote.impl.SocketOutboundAdapter;

public class ControlCenterConfig implements IConfigService {

	private Properties properties;



	public ControlCenterConfig() {
		this.properties = new Properties();
		this.properties.setProperty("hostname", "192.168.2.118");
		this.properties.setProperty("port", "4321");
	}

	@Override
	public List<IFeature> getFeatureList() {
		return new ArrayList<IFeature>();
	}

	@Override
	public List<IInboundAdapter> getInboundAdapterList() {
		return new ArrayList<IInboundAdapter>();
	}

	@Override
	public IOutboundAdapter getOutboundAdapter() {
		return new SocketOutboundAdapter();
	}

	@Override
	public IAnjaroController getController() {
		return new DefaultControllerImpl();
	}


	public Properties getProperties() {
		return this.properties;
	}

	public void setProperties(final Properties pProperties) {
		this.properties = pProperties;
	}

	@Override
	public String getProperty(final String pKey) {
		return this.properties.getProperty(pKey);
	}

}
