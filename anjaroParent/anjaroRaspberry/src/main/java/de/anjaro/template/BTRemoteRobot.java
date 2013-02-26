package de.anjaro.template;

import java.util.ArrayList;
import java.util.List;

import de.anjaro.config.IConfigService;
import de.anjaro.controller.IAnjaroController;
import de.anjaro.controller.impl.DefaultControllerImpl;
import de.anjaro.dispatcher.impl.ObjectSerializeCommandDispatcher;
import de.anjaro.feature.IFeature;
import de.anjaro.feature.impl.RaspberryLedLightFeature;
import de.anjaro.feature.impl.RaspberryServoMotorFeature;
import de.anjaro.remote.IInboundAdapter;
import de.anjaro.remote.IOutboundAdapter;
import de.anjaro.remote.impl.BluetoothInboundAdapter;

public class BTRemoteRobot implements IConfigService {

	@Override
	public List<IFeature> getFeatureList() {
		final List<IFeature> result = new ArrayList<IFeature>();
		result.add(new RaspberryServoMotorFeature());
		result.add(new RaspberryLedLightFeature());
		return result;
	}



	@Override
	@SuppressWarnings("rawtypes")
	public List<IInboundAdapter> getInboundAdapterList() {
		final List<IInboundAdapter> resultList = new ArrayList<IInboundAdapter>();
		final BluetoothInboundAdapter btAdapter = new BluetoothInboundAdapter();
		btAdapter.setCommandDispatcher(new ObjectSerializeCommandDispatcher());
		resultList.add(btAdapter);
		return resultList;
	}




	@Override
	public IOutboundAdapter<String> getOutboundAdapter() {
		return null;
	}



	@Override
	public IAnjaroController getController() {
		return new DefaultControllerImpl();
	}







}
