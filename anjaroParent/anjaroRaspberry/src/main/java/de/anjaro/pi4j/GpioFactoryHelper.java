package de.anjaro.pi4j;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;

public class GpioFactoryHelper {

	public static synchronized GpioController getInstance() {
		return GpioFactory.getInstance();
	}

}
