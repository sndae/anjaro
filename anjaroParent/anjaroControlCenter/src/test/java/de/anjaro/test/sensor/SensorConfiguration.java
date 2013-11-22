package de.anjaro.test.sensor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.anjaro.config.IConfigService;
import de.anjaro.controller.IAnjaroController;
import de.anjaro.feature.IFeature;
import de.anjaro.feature.module.ISensor;
import de.anjaro.remote.IInboundAdapter;
import de.anjaro.remote.IOutboundAdapter;

public class SensorConfiguration implements IConfigService {

	private final SensorTest test;

	public SensorConfiguration(final SensorTest pTest) {
		super();
		this.test = pTest;
	}

	@Override
	public List<IFeature> getFeatureList() {
		final List<IFeature> featureList = new ArrayList<IFeature>();
		featureList.add(new SensorTestFeature(this.test));
		return featureList;
	}

	@Override
	public List<IInboundAdapter> getInboundAdapterList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IOutboundAdapter getOutboundAdapter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, ISensor> getSensorMap() {
		final Map<String, ISensor> sensorMap = new HashMap<String, ISensor>();
		final ISensor clickSensor = new ClickSensor("testSensor");
		sensorMap.put(clickSensor.getId(), clickSensor);
		return sensorMap;
	}

	@Override
	public IAnjaroController getController() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getProperty(final String pKey) {
		// TODO Auto-generated method stub
		return null;
	}

}

