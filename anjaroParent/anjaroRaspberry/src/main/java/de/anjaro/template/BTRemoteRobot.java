package de.anjaro.template;

import java.util.ArrayList;
import java.util.List;

import de.anjaro.config.IConfigService;
import de.anjaro.feature.IFeature;
import de.anjaro.feature.impl.RaspberryServoMotorFeature;
import de.anjaro.remote.IAdapter;

public class BTRemoteRobot implements IConfigService {

	@Override
	public List<IFeature> getFeatureList() {
		final List<IFeature> result = new ArrayList<IFeature>();
		result.add(new RaspberryServoMotorFeature());
		return result;
	}

	@Override
	public List<IAdapter> getAdapterList() {
		// TODO Auto-generated method stub
		return null;
	}





}
