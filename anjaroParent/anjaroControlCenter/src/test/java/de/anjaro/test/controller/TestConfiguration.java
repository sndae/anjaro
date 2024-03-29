package de.anjaro.test.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.anjaro.config.IConfigService;
import de.anjaro.controller.IAnjaroController;
import de.anjaro.controller.impl.DefaultControllerImpl;
import de.anjaro.feature.IFeature;
import de.anjaro.feature.module.ISensor;
import de.anjaro.remote.IInboundAdapter;
import de.anjaro.remote.IOutboundAdapter;

public class TestConfiguration implements IConfigService {

	@Override
	public List<IFeature> getFeatureList() {
		final List<IFeature> result = new ArrayList<IFeature>();
		result.add(new TestFeature());
		return result;
	}

	@Override
	public List<IInboundAdapter> getInboundAdapterList() {
		return null;
	}

	@Override
	public IAnjaroController getController() {
		return new DefaultControllerImpl();
	}

	@Override
	public IOutboundAdapter getOutboundAdapter() {
		return null;
	}

	@Override
	public String getProperty(final String pKey) {
		return null;
	}

	@Override
	public Map<String, ISensor> getSensorMap() {
		// TODO Auto-generated method stub
		return null;
	}



}
