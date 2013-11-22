package de.anjaro.pi4j;

import java.util.EnumSet;
import java.util.logging.Logger;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinMode;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiGpioProvider;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.pi4j.io.gpio.event.PinEventType;
import com.pi4j.io.gpio.impl.PinImpl;

import de.anjaro.controller.IAnjaroController;
import de.anjaro.event.SimpleEvent;
import de.anjaro.feature.module.AbstractSensor;
import de.anjaro.feature.module.ISensor;

public class DigitalPinChangeSensor extends AbstractSensor implements GpioPinListenerDigital, ISensor {

	private static final Logger LOG = Logger.getLogger(DigitalPinChangeSensor.class.getName());

	private GpioController gpioController;
	private GpioPinDigitalInput inputPin;
	private final Pin pin;

	public DigitalPinChangeSensor(final String pId, final int pPin) {
		super(pId);
		if (pPin < 1 || pPin > 20) {
			throw new IllegalArgumentException("Pin number must be between 1 and 20. See Pi4j for further details");
		}
		this.pin = new PinImpl(RaspiGpioProvider.NAME, pPin, "GPIO " + (pPin), 
				EnumSet.of(PinMode.DIGITAL_INPUT, PinMode.DIGITAL_OUTPUT),
				PinPullResistance.all());
	}

	@Override
	public void handleGpioPinDigitalStateChangeEvent(final GpioPinDigitalStateChangeEvent pEvent) {
		LOG.entering(DigitalPinChangeSensor.class.getName(), "handleGpioPinDigitalStateChangeEvent");
		if (pEvent.getEventType().equals(PinEventType.DIGITAL_STATE_CHANGE)) {
			if (pEvent.getState().equals(PinState.HIGH)) {
				this.controller.fireEvent(new SimpleEvent(super.getId(), "high"));
			} else {
				this.controller.fireEvent(new SimpleEvent(super.getId(), "low"));
			}
		}
		LOG.exiting(DigitalPinChangeSensor.class.getName(), "handleGpioPinDigitalStateChangeEvent");
	}



	@Override
	public void init(final IAnjaroController pController) throws Exception {
		LOG.entering(DigitalPinChangeSensor.class.getName(), "init");
		this.gpioController = GpioFactoryHelper.getInstance();
		super.init(pController);
		LOG.exiting(DigitalPinChangeSensor.class.getName(), "init");
	}

	@Override
	public void shuthdown() {
		LOG.entering(DigitalPinChangeSensor.class.getName(), "shuthdown");
		this.gpioController.shutdown();
		LOG.exiting(DigitalPinChangeSensor.class.getName(), "shuthdown");

	}

	@Override
	public void run() {
		LOG.entering(DigitalPinChangeSensor.class.getName(), "run");
		this.inputPin = this.gpioController.provisionDigitalInputPin(this.pin, PinPullResistance.PULL_DOWN);
		this.inputPin.addListener(this);
		LOG.exiting(DigitalPinChangeSensor.class.getName(), "run");
	}





}
