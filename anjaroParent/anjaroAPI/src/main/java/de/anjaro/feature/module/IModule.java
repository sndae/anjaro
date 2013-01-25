package de.anjaro.feature.module;

import de.anjaro.controller.IAnjaroController;

/**
 * The Interface IModule.
 */
public interface IModule extends Runnable {

	/**
	 * Inits the.
	 *
	 * @param pController the controller
	 * @throws Exception the exception
	 */
	void init(IAnjaroController pController) throws Exception;

}
