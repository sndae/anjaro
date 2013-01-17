package de.anjaro.config;

import java.util.List;

import de.anjaro.feature.IFeature;
import de.anjaro.remote.IAdapter;
import de.anjaro.util.AnjaroConstants;


/**
 * Common config interface. It will be initialized from the JobStarter during start of
 * Anjaro.
 * The config will be loaded based on the parameter {@link AnjaroConstants#ARG_CONFIG_CLASS}
 * If not set, the default Properties config service will be used, which will load the
 * file {@link AnjaroConstants#DEFAULT_CONFIG_NAME}, if no vm argument {@link AnjaroConstants#ARG_CONFIG_NAME} is set.
 * Otherwise, the file name specified in this vm argument will be loaded
 * 
 * @author Joachim Pasquali
 */
public interface IConfigService {



	List<IFeature> getFeatureList();

	List<IAdapter> getAdapterList();

}
