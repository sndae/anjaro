package de.anjaro.config;

import java.util.List;
import java.util.Map;

import de.anjaro.controller.IAnjaroController;
import de.anjaro.feature.IFeature;
import de.anjaro.feature.module.ISensor;
import de.anjaro.remote.IInboundAdapter;
import de.anjaro.remote.IOutboundAdapter;
import de.anjaro.util.AnjaroConstants;


/**
 * Common config interface. To keep it simple, the configuration will be loaded from a class.
 * For each robot mode, there might be a config class, which "orchestrates" all needed features
 * and adapters together.
 * The config will be used to initialize the robot.
 * The config will normally identified by the JVM parameter {@link AnjaroConstants#ARG_CONFIG_CLASS}
 * <p/>
 * So, if your config has the name de.test.MyTestConfig, you will have to add the jvm parameter:
 * <p/>
 * <code>java -cp ... -Danjaro.config.class=de.test.MyTestConfig</code>
 * <p/>
 * The config class must have a default constructor.
 * @see AnjaroStarter
 * 
 * @author Joachim Pasquali
 */
public interface IConfigService {


	/**
	 * Gets the feature list, which are supported by this configuration.
	 * Must not be null. Otherwise the robot does not have any functionality.
	 * 
	 * @See IFeature
	 *
	 * @return the feature list
	 */
	List<IFeature> getFeatureList();

	/**
	 * Gets the adapter list, which are listen for external calls. This can be Bluetooth adapter, System
	 * IO adapter, Socket adapter, etc.
	 * Can be null.
	 * 
	 * @See IInboundAdapter
	 *
	 * @return the adapter list
	 */
	List<IInboundAdapter> getInboundAdapterList();


	/**
	 * A robot can forward commands, to the outbound adapter, if the 
	 * requested feature is not registered in the controller
	 * 
	 * @return
	 */
	IOutboundAdapter getOutboundAdapter();

	/**
	 * Returns the list of all sensors of the robot.
	 * Each sensor should have a unique id. The controller
	 * will not check this. It will just overwrite sensors with the same sensor id.
	 * So, take care of this in your configuration.
	 * 
	 * @return map of senor id sensore pairs
	 */
	Map<String, ISensor> getSensorMap();


	/**
	 * Gets the controller.
	 * Take a look into the anjaroControlCenter project. There you will find a default implementation,
	 * which will work for the most of the cases.
	 * 
	 * @see IAnjaroController
	 *
	 * @return the controller
	 */
	IAnjaroController getController();

	String getProperty(String pKey);
}
