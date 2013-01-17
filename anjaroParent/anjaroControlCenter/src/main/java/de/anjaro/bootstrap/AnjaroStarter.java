package de.anjaro.bootstrap;

import java.util.logging.Logger;

import de.anjaro.controller.IAnjaroController;
import de.anjaro.dispatcher.ICommandDispatcher;

/**
 * The Class AnjaroStarter. This is the central starting class for the anjaro
 * system.
 */
public class AnjaroStarter {

	/** The Constant LOG. */
	private static final Logger LOG = Logger.getLogger(AnjaroStarter.class.getName());

	/** The already cleanedup. */
	private static boolean alreadyCleanedup = false;

	/** The controller. */
	private static IAnjaroController controller;

	/** The dispatcher. */
	private static ICommandDispatcher commandDispatcher;

	/**
	 * The main method.
	 * <p>
	 * First, the configuration will be initialized. To do so, the
	 * {@link ARG_CONFIG_CLASS} VM parameter will be checked. If null, the
	 * standard {@link PropertiesConfig} will be initialized. Out of this
	 * config, the controller, command dispatcher and the connector will be read
	 * and initialized.
	 * </p>
	 * <p>
	 * Furthermore, a stoplistener will be created.
	 * </p>
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(final String[] args) {
		//		LOG.entering("de.anjaro.bootstrap.AnjaroStarter", "main");
		//		final String configClass = System.getProperty(ARG_CONFIG_CLASS);
		//		IConfigService configService = null;
		//		if (configClass != null) {
		//			try {
		//				configService = (IConfigService) Class.forName(configClass).newInstance();
		//			} catch (final Exception e) {
		//				LOG.severe("Unable to initialize config class: " + configClass);
		//				LOG.throwing(AnjaroStarter.class.getName(), "Main", e);
		//				System.exit(-1);
		//			}
		//		} else {
		//			try {
		//				configService = new PropertiesConfig();
		//			} catch (final Exception e) {
		//				LOG.severe("Unable to initialize PropertiesConfig.");
		//				LOG.throwing(AnjaroStarter.class.getName(), "Main", e);
		//				System.exit(-1);
		//			}
		//		}
		//
		//		// TODO: setup shutdown hook (socket listener or similar
		//		try {
		//
		//			controller = configService.getController();
		//			controller.init(configService);
		//			LOG.finer("-- Controller initialized: " + controller.getClass().getName());
		//
		//			commandDispatcher = configService.getcomCommandDispatcher();
		//			commandDispatcher.setController(controller);
		//			LOG.finer("Commadn dispatcher initialized: " + commandDispatcher.getClass().getName());
		//
		//			remoteConnector = configService.getRemoteConnector();
		//			remoteConnector.setCommandDispatcher(commandDispatcher);
		//			LOG.finer("Remote connector initialized: " + remoteConnector.getClass().getName());
		//
		//			final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		//			String line = "";
		//			while (!line.equals("exit")) {
		//				line = reader.readLine();
		//				if (line.equals("rf")) {
		//					System.out.println("running the right motor");
		//					controller.runRightMotor(Direction.forward, Speed.speed10);
		//				} else if (line.equals("lf")) {
		//					System.out.println("running the left motor");
		//					controller.runLeftMotor(Direction.forward, Speed.speed10);
		//				}
		//			}
		//		} catch (final Throwable e) {
		//			LOG.severe("Unexpected error occurred: " + e.getMessage());
		//			LOG.throwing(AnjaroStarter.class.getName(), "main", e);
		//			System.exit(-1);
		//		} finally {
		//			remoteConnector.shutDown();
		//			controller.shutdown();
		//			alreadyCleanedup = true;
		//		}

	}

	/**
	 * will shutdown connector and controller, if not yet done.
	 */
	@Override
	protected void finalize() throws Throwable {
		if (!alreadyCleanedup) {
			//			remoteConnector.shutDown();
			controller.shutdown();
		}
	}

}
