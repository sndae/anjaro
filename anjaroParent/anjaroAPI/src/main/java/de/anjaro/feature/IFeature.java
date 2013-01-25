package de.anjaro.feature;

import de.anjaro.controller.IAnjaroController;


/**
 * A feature is a behavior of the robot.
 * Features can be defined in the configuration and will than be
 * started by the system during startup.
 * This enables the developer, to run the robot in an autonomous mode.
 * A feature normally starts several sensors and actors. It steers the
 * behavior of the robot by analysing the sensor results and sending commands
 * to the controller.
 * 
 * @author Joachim Pasquali
 *
 */
public interface IFeature {

	/**
	 * Gets the name.
	 *
	 * @return The name of the feature
	 */
	String getName();

	/**
	 * Gets the description.
	 *
	 * @return short description of what the feature is doing
	 */
	String getDescription();


	/**
	 * Shut down.
	 */
	void shutDown();


	/**
	 * Inits the feature.
	 *
	 * @param pController the controller
	 * @throws Exception the exception
	 */
	void init(IAnjaroController pController) throws Exception;



}
