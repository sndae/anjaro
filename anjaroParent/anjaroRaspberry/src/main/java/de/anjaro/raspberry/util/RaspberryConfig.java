package de.anjaro.raspberry.util;

import java.io.IOException;
import java.util.Properties;

import de.anjaro.gpio.Revision;

public class RaspberryConfig {

	private static Properties config;
	private static Revision revision;


	/** The Constant REVISION_PROP_NAME. => anjaro.raspberry.revision*/
	public static final String REVISION_PROP_NAME = "anjaro.raspberry.revision";

	public static final String RASPBERRY_CONFIG = "raspberry-config.properties";



	public static final String getProperty(final String pKey) throws IOException {
		if (config == null) {
			synchronized (pKey) {
				config = new Properties();
				config.load(RaspberryConfig.class.getClassLoader().getResourceAsStream(RASPBERRY_CONFIG));
			}
		}
		return config.getProperty(pKey);
	}


	public static final Revision getRevision() throws IOException {
		if (revision == null) {
			revision = Revision.valueOf(getProperty(REVISION_PROP_NAME));
			if (revision == null) {
				throw new IllegalArgumentException("Revision must not be null. Please check " + RASPBERRY_CONFIG);
			}
		}
		return revision;
	}


}
