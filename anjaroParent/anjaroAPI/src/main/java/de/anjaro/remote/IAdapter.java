package de.anjaro.remote;

import de.anjaro.controller.IAnjaroController;
import de.anjaro.dispatcher.ICommandDispatcher;

public interface IAdapter extends Runnable {

	void init(IAnjaroController pController);


	void setCommandDispatcher(ICommandDispatcher pCommandDispatcher);

}
