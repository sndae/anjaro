package de.anjaro.config;

import java.util.List;

import de.anjaro.controller.IAnjaroController;
import de.anjaro.feature.IFeature;
import de.anjaro.remote.IAdapter;


/**
 * Common config interface. To keep it simple, the configuration will be loaded from a class.
 * For each robot mode, there might be a config class, which "orchestrates" all needed features
 * and adapters together.
 * The config will be used to initialize the robot.
 * 
 * @author Joachim Pasquali
 */
public interface IConfigService {


	/**
	 * Gets the feature list.
	 *
	 * @return the feature list
	 */
	List<IFeature> getFeatureList();

	/**
	 * Gets the adapter list.
	 *
	 * @return the adapter list
	 */
	List<IAdapter> getAdapterList();

	/**
	 * Gets the controller.
	 *
	 * @return the controller
	 */
	IAnjaroController getController();

}
