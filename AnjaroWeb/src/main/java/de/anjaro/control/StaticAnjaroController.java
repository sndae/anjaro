package de.anjaro.control;

import java.io.Serializable;

import de.anjaro.config.IConfigService;
import de.anjaro.controller.IAnjaroController;
import de.anjaro.model.Command;
import de.anjaro.model.CommandResult;
import de.anjaro.model.Speed;
import de.anjaro.util.CommandResultHelper;
import de.anjaro.util.DefaultAnjaroError;

public class StaticAnjaroController implements Serializable{
	private static final long serialVersionUID = -4508332548886007502L;
	private transient static IAnjaroController CONTROLLER;
	private static boolean testMode = false;
	private static boolean testControllerActive = false;
	private static boolean testIsRight = false;
	private static boolean testIsLeft = false;
	private static boolean testIsForward = false;
	private static boolean testIsBackward = false;


	public static synchronized void initController() throws Exception {
		if (testMode) {
			testControllerActive = true;
			return;
		}
		if (CONTROLLER == null) {
			final IConfigService service = new ControlCenterConfig();
			CONTROLLER = service.getController();
			CONTROLLER.init(service);
		}
	}

	public static boolean isControllerActive() {
		if(testMode) {
			return testControllerActive;
		}
		return CONTROLLER != null;
	}

	private static synchronized Command getCommand() {
		final Command command = new Command();
		command.setFeatureName("twoMotorFeature");
		command.setParams(Speed.speed10);
		return command;
	}


	public static synchronized CommandResult backward() {
		final Command command = getCommand();
		command.setMethod("backward");
		testIsBackward = true;
		return execute(command);

	}

	public static synchronized CommandResult right() {
		final Command command = getCommand();
		command.setMethod("right");
		testIsRight = true;
		return execute(command);

	}

	private static synchronized CommandResult execute(final Command pCommand) {
		CommandResult result;
		if (testMode) {
			result = CommandResultHelper.createResult(DefaultAnjaroError.success, null);
		} else {
			if (CONTROLLER == null) {
				try {
					initController();
				} catch (final Exception e) {
					// TODO
					e.printStackTrace();
				}
			}
			result = CONTROLLER.execute(pCommand);
		}
		return result;

	}

	public static synchronized CommandResult left() {
		final Command command = getCommand();
		command.setMethod("left");
		testIsLeft = true;
		return execute(command);
	}

	public static synchronized CommandResult forward() {
		final Command command = getCommand();
		command.setMethod("forward");
		testIsForward = true;
		return execute(command);
	}

	public static synchronized CommandResult stop() {
		final Command command = new Command();
		command.setFeatureName("twoMotorFeature");
		command.setMethod("stopAllMotors");
		testIsBackward = false;
		testIsForward = false;
		testIsLeft = false;
		testIsRight = false;
		return execute(command);
	}

	public static synchronized void shutDown() {
		if (testMode) {
			testIsBackward = false;
			testIsForward = false;
			testIsLeft = false;
			testIsRight = false;

			testControllerActive = false;
			return;
		}
		try {
			if (CONTROLLER != null) {
				CONTROLLER.shutdown();
			}
		} finally {
			CONTROLLER = null;
		}
	}

	public static boolean isRight() {
		return testIsRight;
	}

	public static boolean isLeft() {
		return testIsLeft;
	}

	public static boolean isForward() {
		return testIsForward;
	}

	public static boolean isBackward() {
		return testIsBackward;
	}

	public static boolean isStop() {
		return !(testIsBackward || testIsForward || testIsRight || testIsLeft);
	}

}
