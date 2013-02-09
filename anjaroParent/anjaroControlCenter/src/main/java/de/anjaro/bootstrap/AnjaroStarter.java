package de.anjaro.bootstrap;

import java.io.File;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

import de.anjaro.config.IConfigService;
import de.anjaro.controller.IAnjaroController;
import de.anjaro.util.AnjaroConstants;
import de.anjaro.util.AnjaroFormatter;
import de.anjaro.util.IShutdownListener;

/**
 * The Class AnjaroStarter. This is the central starting class for the anjaro
 * system.
 */
public class AnjaroStarter implements IShutdownListener {

	/** The Constant LOG. */
	private static final Logger LOG = Logger.getLogger(AnjaroStarter.class.getName());

	/** The controller. */
	private IAnjaroController controller;

	private boolean shutDown = false;

	private Thread mainThread;

	public static void main(final String[] args) {
		final AnjaroStarter starter = new AnjaroStarter();
		starter.startAnjaro();

	}

	public void startAnjaro() {
		final ConsoleHandler handler = new ConsoleHandler();
		handler.setFormatter(new AnjaroFormatter());
		LOG.addHandler(handler);
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
			this.daemonize();
			this.addShutdownHook();
			LOG.fine("##########  Anjaro is running now ...");
			LOG.removeHandler(handler);
			this.waitForShutdown();
			LOG.fine("Shutdown now");
			this.controller.shutdown();
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

	private void addShutdownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				AnjaroStarter.this.doShutdown();
			}
		});
	}

	protected void doShutdown() {
		LOG.entering(this.getClass().getName(), "doShutdown");
		this.controller.shutdown();
		try {
			this.getMainDaemonThread().join();
			LOG.exiting(this.getClass().getName(), "doShutdown");

		} catch (final InterruptedException e) {
			LOG.throwing(this.getClass().getName(), "doShutdown", e);
			LOG.severe("Interrupted while waiting for main thread to shutdown");
		}

	}

	private Thread getMainDaemonThread() {
		return this.mainThread;
	}

	private void daemonize() {
		getPidFile().deleteOnExit();
		this.mainThread = Thread.currentThread();
		System.out.close();
		System.err.close();
	}

	private static File getPidFile() {
		String fileName = System.getProperty("daemon.file");
		File file;
		if (fileName == null) {
			LOG.info("vm argument daemon.file not set. Use default anjaro.pid");
			fileName = "anajaro.pid";
		}
		file = new File(fileName);
		return file;
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
