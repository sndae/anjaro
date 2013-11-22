package de.anjaro.feature.module;

import java.util.Properties;

/**
 * The Interface ISensor. A sensor is everything, which receives information from the outside world,
 * which could change the behaviour of the robot.
 * This can be light sensors, ultra sonic sensors (e.g. distance measurement), etc.
 * 
 * @author Joachim Pasquali
 */
public interface ISensor extends IModule {

	String getId();


	void adjust(Properties pValues);

}
