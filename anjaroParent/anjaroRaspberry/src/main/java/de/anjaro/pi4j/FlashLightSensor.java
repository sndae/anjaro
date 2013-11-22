package de.anjaro.pi4j;

import java.util.logging.Logger;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPinDigitalMultipurpose;
import com.pi4j.io.gpio.PinMode;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

import de.anjaro.controller.IAnjaroController;
import de.anjaro.feature.module.AbstractSensor;

public class FlashLightSensor extends AbstractSensor implements GpioPinListenerDigital {

	private static final Logger LOG = Logger.getLogger(FlashLightSensor.class.getName());

	private GpioController controller;
	private GpioPinDigitalMultipurpose resistorPin;
	//private GpioPinDigitalInput resistorPin;

	private long startTime;

	public FlashLightSensor(final String pId) {
		super(pId);
	}

	@Override
	public void handleGpioPinDigitalStateChangeEvent(final GpioPinDigitalStateChangeEvent pArg0) {
		LOG.entering(FlashLightSensor.class.getName(), "handleGpioPinDigitalStateChangeEvent");
		final long time = System.currentTimeMillis() - this.startTime;
		LOG.warning("State" + pArg0.getState());
		LOG.warning("Time: " + time); 

		LOG.exiting(FlashLightSensor.class.getName(), "handleGpioPinDigitalStateChangeEvent");
	}



	@Override
	public void init(final IAnjaroController pController) throws Exception {
		this.controller = GpioFactoryHelper.getInstance();
		//this.resistorPin = this.controller.provisionDigitalInputPin(RaspiPin.GPIO_01, PinPullResistance.PULL_DOWN);
		this.resistorPin = this.controller.provisionDigitalMultipurposePin(RaspiPin.GPIO_01, PinMode.DIGITAL_OUTPUT, PinPullResistance.PULL_UP);
		super.init(pController);
	}

	@Override
	public void run() {
		LOG.entering(FlashLightSensor.class.getName(), "run");
		try {
			this.measure();
		} catch (final InterruptedException e) {
			LOG.severe("error");
			e.printStackTrace();
		}
		LOG.exiting(FlashLightSensor.class.getName(), "run");

	}

	private void measure() throws InterruptedException {
		LOG.entering(FlashLightSensor.class.getName(), "measure");

		this.resistorPin.addListener(this);
		this.startTime = System.currentTimeMillis();
		this.resistorPin.high();
		Thread.sleep(1000);
		this.resistorPin.setMode(PinMode.DIGITAL_INPUT);

		LOG.exiting(FlashLightSensor.class.getName(), "measure");
	}

	@Override
	public void shuthdown() {
		this.controller.shutdown();
	}

}
