package de.anjaro.feature.impl;

import java.io.IOException;
import java.util.logging.Logger;

import de.anjaro.controller.IAnjaroController;
import de.anjaro.feature.ISimpleTwoMotorFeature;
import de.anjaro.model.Direction;

/**
 * This class is using ther really nice Servoblaster implementation. To get this
 * running, servoblaster deamon must be running on the Rasperyy Pi.
 * <p>
 * For further information to Servoblaster see
 * https://github.com/richardghirst/PiBits/tree/master/ServoBlaster
 * </p>
 * <p>
 * As pins, P16 and P18 are used. These are the pins GPIO 23 and GPIO 24.
 * </p>
 * 
 * @author jpasquali
 * 
 */
public class ServoblasterTwoMotorFeature implements ISimpleTwoMotorFeature {

	private static final Logger LOG = Logger.getLogger(ServoblasterTwoMotorFeature.class.getName());

	private int rightMediumValue = 103;
	private int leftMediumValue = 103;

	private final String rightMotor = "echo 5=";
	private final String leftMotor = "echo 6=";

	private Direction rightDirection = null;
	private Direction leftDirection = null;

	private int speed = 0;


	@Override
	public String getName() {
		return "twoMotorFeature";
	}

	@Override
	public String getDescription() {
		return "Running servo motors on pins GPIO 23 and GPIO 24";
	}

	@Override
	public void shutDown() {
		LOG.entering(ServoblasterTwoMotorFeature.class.getName(), "shutDown");
		this.stop();
		LOG.exiting(ServoblasterTwoMotorFeature.class.getName(), "shutDown");
	}

	@Override
	public void init(final IAnjaroController pController) throws Exception {
		// nothing to do
	}

	private String calculateValue(final Direction pDirection, final int pSpeed, final boolean pIsRight) {
		int value = pIsRight ? this.rightMediumValue : this.leftMediumValue;
		if (pSpeed  == 0 || pDirection == null) {
			value = 0;
		} else if ((Direction.forward.equals(pDirection) && pIsRight) || (Direction.backward.equals(pDirection) && !pIsRight)) {
			value = value - 5 * pSpeed;
		} else {
			value = value + 5 * pSpeed;
		}
		return String.valueOf(value);
	}

	private void run(final String pMotor, final Direction pDirection) {
		this.run(pMotor, pDirection, false);
	}

	private void run(final String pMotor, final Direction pDirection, final boolean pStop) {
		LOG.entering(ServoblasterTwoMotorFeature.class.getName(), "run");
		final boolean isRight = pMotor.contains("5") ? true : false; 
		Direction direction = pDirection;
		if (direction == null) {
			if (isRight) {
				direction = this.rightDirection;
			} else {
				direction = this.leftDirection;
			}
		}
		try {

			final String[] commands = new String[3];
			commands[0] = "/bin/bash";
			commands[1] = "-c";
			commands[2] = pMotor.concat(this.calculateValue(direction, pStop ? 0 : this.speed, isRight)).concat(" > /dev/servoblaster");
			LOG.fine(commands[2]);
			new ProcessBuilder(commands).start();
			if (isRight) {
				this.rightDirection = direction;
			} else  {
				this.leftDirection = direction;
			}
		} catch (final IOException e) {
			LOG.severe("Unable to run motor" + e.getMessage());
		}
		LOG.exiting(ServoblasterTwoMotorFeature.class.getName(), "run");
	}

	@Override
	public void forward() {
		this.run(this.rightMotor, Direction.forward);
		this.run(this.leftMotor, Direction.forward);

	}

	@Override
	public void backward() {
		this.run(this.rightMotor, Direction.backward);
		this.run(this.leftMotor, Direction.backward);
	}

	@Override
	public void right() {
		this.run(this.leftMotor, Direction.forward);
		this.run(this.rightMotor, Direction.backward);
	}

	@Override
	public void left() {
		this.run(this.leftMotor, Direction.backward);
		this.run(this.rightMotor, Direction.forward);
	}

	@Override
	public int getSpeed() {
		return this.speed;
	}

	@Override
	public int getRightMediumValue() {
		return this.rightMediumValue;
	}

	@Override
	public void setRightMediumValue(final Integer pRightMediumValue) {
		this.rightMediumValue = pRightMediumValue == null ? 0 : pRightMediumValue.intValue();
		this.run(this.rightMotor, this.rightDirection);
	}

	@Override
	public int getLeftMediumValue() {
		return this.leftMediumValue;
	}

	@Override
	public void setLeftMediumValue(final Integer pLeftMediumValue) {
		this.leftMediumValue = pLeftMediumValue == null ? 0 : pLeftMediumValue.intValue();
		this.run(this.leftMotor, this.leftDirection);
	}

	@Override
	public void stop() {
		this.run(this.rightMotor, null, true);
		this.run(this.leftMotor, null, true);
	}

	@Override
	public void setSpeed(final Integer pSpeed) {
		this.speed = pSpeed == null ? 0 : pSpeed.intValue();
		this.run(this.leftMotor, null);
		this.run(this.rightMotor, null);
	}

}
