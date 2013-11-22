package de.anjaro.test.sensor;

import de.anjaro.controller.IAnjaroController;
import de.anjaro.event.IEvent;
import de.anjaro.event.IEventListener;
import de.anjaro.event.SimpleEvent;
import de.anjaro.feature.IFeature;

public class SensorTestFeature implements IFeature, IEventListener {

	private final SensorTest test;


	public SensorTestFeature(final SensorTest pTest) {
		super();
		this.test = pTest;
	}

	@Override
	public String getName() {
		return "sensorTestFeature";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void shutDown() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(final IAnjaroController pController) throws Exception {
		pController.registerEventListener(SimpleEvent.EVENT_NAME, this);

	}

	@Override
	public void onEvent(final IEvent pEvent) {
		this.test.setMe();
	}

}
