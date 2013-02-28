package de.anjaro.template;

import java.util.ArrayList;
import java.util.List;

import de.anjaro.config.IConfigService;
import de.anjaro.controller.IAnjaroController;
import de.anjaro.controller.impl.DefaultControllerImpl;
import de.anjaro.dispatcher.impl.SimpleStringCommandDispatcher;
import de.anjaro.feature.IFeature;
import de.anjaro.feature.impl.RaspberryServoMotorFeature;
import de.anjaro.remote.IInboundAdapter;
import de.anjaro.remote.IOutboundAdapter;
import de.anjaro.remote.impl.SystemInInboundAdapter;

public class SystemInServoRobot implements IConfigService {

	@Override
	public List<IFeature> getFeatureList() {
		final List<IFeature> result = new ArrayList<IFeature>();
		result.add(new RaspberryServoMotorFeature());
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<IInboundAdapter> getInboundAdapterList() {
		final List<IInboundAdapter> resultList = new ArrayList<IInboundAdapter>();
		final SystemInInboundAdapter sysInAdapter = new SystemInInboundAdapter();
		sysInAdapter.setCommandDispatcher(new SimpleStringCommandDispatcher());
		resultList.add(sysInAdapter);
		return resultList;
	}

	@Override
	public IAnjaroController getController() {
		return new DefaultControllerImpl();
	}

	@Override
	public IOutboundAdapter<String> getOutboundAdapter() {
		return null;
	}

	@Override
	public String getProperty(final String pKey) {
		return null;
	}



}
