package de.anjaro.test.controller;

import java.util.ArrayList;
import java.util.List;

import de.anjaro.config.IConfigService;
import de.anjaro.controller.IAnjaroController;
import de.anjaro.controller.impl.DefaultControllerImpl;
import de.anjaro.feature.IFeature;
import de.anjaro.remote.IAdapter;

public class TestConfiguration implements IConfigService {

	@Override
	public List<IFeature> getFeatureList() {
		final List<IFeature> result = new ArrayList<IFeature>();
		result.add(new TestFeature());
		return result;
	}

	@Override
	public List<IAdapter> getAdapterList() {
		return null;
	}

	@Override
	public IAnjaroController getController() {
		return new DefaultControllerImpl();
	}

}
