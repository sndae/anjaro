package de.anjaro.remote;

import de.anjaro.controller.IAnjaroController;
import de.anjaro.dispatcher.ICommandDispatcher;

public interface IAdapter<C> extends Runnable {

	String getName();

	void init(IAnjaroController pController) throws Exception;


	void setCommandDispatcher(ICommandDispatcher<C> pCommandDispatcher);

	void shutDown();

}
