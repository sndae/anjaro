package de.anjaro.feature.module;

import de.anjaro.controller.IAnjaroController;

public interface IModule extends Runnable {

	void init(IAnjaroController pController) throws Exception;

}
