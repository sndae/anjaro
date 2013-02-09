package de.anjaro.feature.impl;

import java.io.IOException;
import java.util.logging.Logger;

import de.anjaro.controller.IAnjaroController;
import de.anjaro.feature.IFeature;
import de.anjaro.gpio.GpioFileWriter;
import de.anjaro.gpio.GpioPin;

public class RaspberryLedLightFeature implements IFeature {

	private final GpioPin pin = GpioPin.p22;

	private static final Logger LOG = Logger.getLogger(RaspberryLedLightFeature.class.getName());

	@Override
	public String getName() {
		return "ledFeature";
	}

	@Override
	public String getDescription() {
		return "set's gpio GPIO25 (pin 22) on high when init and low, when shutdown";
	}

	@Override
	public void shutDown() {
		LOG.entering(this.getClass().getName(), "shutDown");
		try {
			GpioFileWriter.setValue(false, this.pin);
			GpioFileWriter.releasePin(this.pin);
		} catch (final IOException e) {
			LOG.throwing(this.getClass().getName(), "shutDown", e);
			LOG.warning("unable to release LED pin 22");
		} finally {
			LOG.exiting(this.getClass().getName(), "shutDown");
		}
	}

	@Override
	public void init(final IAnjaroController pController) throws Exception {
		LOG.entering(this.getClass().getName(), "init");
		GpioFileWriter.initializePin(this.pin);
		GpioFileWriter.setValue(true, this.pin);
		LOG.exiting(this.getClass().getName(), "init");

	}

}
