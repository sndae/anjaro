package de.anjaro.template;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import de.anjaro.config.IConfigService;
import de.anjaro.controller.IAnjaroController;
import de.anjaro.controller.impl.DefaultControllerImpl;
import de.anjaro.feature.IFeature;
import de.anjaro.feature.impl.ServoblasterTwoMotorFeature;
import de.anjaro.feature.impl.UltrasonicMotorFeature;
import de.anjaro.feature.module.ISensor;
import de.anjaro.pi4j.DigitalPinChangeSensor;
import de.anjaro.remote.IInboundAdapter;
import de.anjaro.remote.IOutboundAdapter;

public class UlrasonicMotorRobot implements IConfigService {

	private static final Logger LOG = Logger.getLogger(UlrasonicMotorRobot.class.getName());

	@Override
	public List<IFeature> getFeatureList() {
		final List<IFeature> result = new ArrayList<IFeature>();
		result.add(new ServoblasterTwoMotorFeature());
		result.add(new UltrasonicMotorFeature());
		return result;
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
	public Map<String, ISensor> getSensorMap() {
		LOG.entering(UlrasonicMotorRobot.class.getName(), "getSensorMap");
		final DigitalPinChangeSensor rightSensor = new DigitalPinChangeSensor(UltrasonicMotorFeature.RIGHT_MOTOR_SENSOR, 1);
		final DigitalPinChangeSensor leftSensor = new DigitalPinChangeSensor(UltrasonicMotorFeature.LEFT_MOTOR_SENSOR, 2);
		final Map<String, ISensor> sensorMap = new HashMap<String, ISensor>();
		sensorMap.put(rightSensor.getId(), rightSensor);
		sensorMap.put(leftSensor.getId(), leftSensor);
		LOG.exiting(UlrasonicMotorRobot.class.getName(), "getSensorMap");
		return sensorMap;
	}

	@Override
	public IAnjaroController getController() {
		return new DefaultControllerImpl();
	}

	@Override
	public String getProperty(final String pKey) {
		// TODO Auto-generated method stub
		return null;
	}

}
