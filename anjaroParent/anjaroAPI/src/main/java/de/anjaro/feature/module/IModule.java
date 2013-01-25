package de.anjaro.feature.module;

import de.anjaro.controller.IAnjaroController;

/**
 * The Interface IModule. A module can be a sensor or an actor.
 * 
 * @see IActor
 * @see ISensor
 * 
 * @author Joachim Pasquali
 */
public interface IModule extends Runnable {

	/**
	 * Inits the module
	 *
	 * @param pController the controller
	 * @throws Exception the exception
	 */
	void init(IAnjaroController pController) throws Exception;

}
