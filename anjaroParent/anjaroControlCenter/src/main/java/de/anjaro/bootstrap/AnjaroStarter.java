package de.anjaro.bootstrap;

import java.util.logging.Logger;

import de.anjaro.config.IConfigService;
import de.anjaro.controller.IAnjaroController;
import de.anjaro.util.AnjaroConstants;
import de.anjaro.util.IShutdownListener;

/**
 * The Class AnjaroStarter. This is the central starting class for the anjaro
 * system.
 */
public class AnjaroStarter implements IShutdownListener {

	/** The Constant LOG. */
	private static final Logger LOG = Logger.getLogger(AnjaroStarter.class.getName());

	/** The already cleanedup. */
	private boolean alreadyCleanedup = false;

	/** The controller. */
	private IAnjaroController controller;

	private boolean shutDown = false;


	public static void main(final String[] args) {
		final AnjaroStarter starter = new AnjaroStarter();
		starter.startAnjaro();

	}

	public void startAnjaro() {
		LOG.entering("de.anjaro.bootstrap.AnjaroStarter", "main");
		final String configClass = System.getProperty(AnjaroConstants.ARG_CONFIG_CLASS);
		if (configClass == null) {
			throw new IllegalArgumentException(AnjaroConstants.ARG_CONFIG_CLASS + " must be set");
		}
		try {
			LOG.fine("Read config from java vm properties");
			final IConfigService config = (IConfigService) Class.forName(configClass).newInstance();
			LOG.fine("Get controller from config");
			this.controller = config.getController();

			if (this.controller == null) {
				throw new IllegalArgumentException("Controller must not be null. Please check your ConfigService class");
			}
			LOG.fine("Initialize controller");
			this.controller.init(config);
			this.controller.addShutdownListener(this);
			LOG.fine("##########  Anjaro is running now ...");
			this.waitForShutdown();
			LOG.fine("Shutdown now");
			this.controller.shutdown();
			this.alreadyCleanedup = true;
			LOG.fine("Shutdown complete. Bye!");
		} catch (final InstantiationException e) {
			LOG.throwing(AnjaroStarter.class.getName(), "main", e);
			throw new IllegalArgumentException("Unable to initialize " + configClass, e);
		} catch (final IllegalAccessException e) {
			LOG.throwing(AnjaroStarter.class.getName(), "main", e);
			throw new IllegalArgumentException("I do not have access to " + configClass, e);
		} catch (final ClassNotFoundException e) {
			LOG.throwing(AnjaroStarter.class.getName(), "main", e);
			throw new IllegalArgumentException("Cannot find class " + configClass + ". Please check parameter " + AnjaroConstants.ARG_CONFIG_CLASS, e);
		} catch (final ClassCastException e) {
			LOG.throwing(AnjaroStarter.class.getName(), "main", e);
			throw new IllegalArgumentException(configClass + " is no instance of " + IConfigService.class.getName(), e);
		} catch (final Exception e) {
			LOG.throwing(AnjaroStarter.class.getName(), "main", e);
			throw new IllegalArgumentException("Unexpected initialization error while initializing Anjaro", e);
		}
	}

	/**
	 * will shutdown connector and controller, if not yet done.
	 */
	@Override
	protected void finalize() throws Throwable {
		if (!this.alreadyCleanedup) {
			this.controller.shutdown();
		}
	}

	private synchronized void waitForShutdown() {
		try {
			while (!this.shutDown) {
				Thread.sleep(500);
			}
		} catch (final InterruptedException e) {
			LOG.throwing(this.getClass().getName(), "waitForShutdown", e);
			System.exit(-1);
		}
	}

	@Override
	public void shutDown() {
		this.shutDown = true;

	}


}
