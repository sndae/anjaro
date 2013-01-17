package de.anjaro.dispatcher;

import de.anjaro.controller.IAnjaroController;

public interface ICommandDispatcher {

	byte[] execute(byte[] pCommand);

	void setController(final IAnjaroController pController);
}
