package de.anjaro.test.sensor;

import de.anjaro.event.SimpleEvent;
import de.anjaro.feature.module.AbstractSensor;

public class ClickSensor extends AbstractSensor {


	public ClickSensor(final String pId) {
		super(pId);
	}

	@Override
	public void run() {
		this.controller.fireEvent(new SimpleEvent(super.getId(), "MyValue"));
	}

	@Override
	public void shuthdown() {
		// TODO Auto-generated method stub

	}


}
