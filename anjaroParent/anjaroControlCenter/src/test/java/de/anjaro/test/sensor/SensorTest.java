package de.anjaro.test.sensor;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import de.anjaro.controller.impl.DefaultControllerImpl;

@RunWith(JUnit4.class)
public class SensorTest {

	private boolean wasSet = false;

	@Before
	public void init() {
		this.wasSet = false;
	}


	@Test
	public void testSensor() {
		final SensorConfiguration sensorConfiguration = new SensorConfiguration(this);
		final DefaultControllerImpl controller = new DefaultControllerImpl();
		try {
			controller.init(sensorConfiguration);
			synchronized (this) {
				Thread.currentThread().sleep(1000);
				Assert.assertTrue(this.wasSet);
			}
		} catch (final Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setMe() {
		this.wasSet = true;
	}

}

