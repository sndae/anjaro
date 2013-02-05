package de.anjaro.template;

import java.util.ArrayList;
import java.util.List;

import de.anjaro.config.IConfigService;
import de.anjaro.controller.IAnjaroController;
import de.anjaro.controller.impl.DefaultControllerImpl;
import de.anjaro.dispatcher.impl.ObjectSerializeCommandDispatcher;
import de.anjaro.feature.IFeature;
import de.anjaro.feature.impl.RaspberryServoMotorFeature;
import de.anjaro.remote.IAdapter;
import de.anjaro.remote.impl.BluetoothInboundAdapter;

public class BTRemoteRobot implements IConfigService {

	@Override
	public List<IFeature> getFeatureList() {
		final List<IFeature> result = new ArrayList<IFeature>();
		result.add(new RaspberryServoMotorFeature());
		return result;
	}



	@Override
	@SuppressWarnings("rawtypes")
	public List<IAdapter> getAdapterList() {
		final List<IAdapter> resultList = new ArrayList<IAdapter>();
		final BluetoothInboundAdapter btAdapter = new BluetoothInboundAdapter();
		btAdapter.setCommandDispatcher(new ObjectSerializeCommandDispatcher());
		resultList.add(btAdapter);
		return resultList;
	}



	@Override
	public IAnjaroController getController() {
		return new DefaultControllerImpl();
	}







}
