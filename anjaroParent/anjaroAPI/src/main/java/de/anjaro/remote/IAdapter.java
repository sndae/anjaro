package de.anjaro.remote;

import de.anjaro.controller.IAnjaroController;
import de.anjaro.dispatcher.ICommandDispatcher;

/**
 * The Interface IAdapter.
 *
 * @param <C> the generic type
 */
public interface IAdapter<C> {

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	String getName();

	/**
	 * Inits the.
	 *
	 * @param pController the controller
	 * @throws Exception the exception
	 */
	void init(IAnjaroController pController) throws Exception;


	/**
	 * Sets the command dispatcher.
	 *
	 * @param pCommandDispatcher the new command dispatcher
	 */
	void setCommandDispatcher(ICommandDispatcher<C> pCommandDispatcher);

	/**
	 * Shut down.
	 */
	void shutDown();

}
