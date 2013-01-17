package de.anjaro.feature;

import de.anjaro.model.Direction;
import de.anjaro.model.MotorStatus;
import de.anjaro.model.Speed;

public interface ITwoMotorFeature extends IFeature {
	/**
	 * Start the right and left motors. If motors are already running, the speed / direction changes
	 * based on the parameter.
	 * The implementation of this interface will have to take care to stop motor first
	 * before changing the direction
	 *
	 * @param pDirection the direction
	 * @param speed the speed
	 */
	void runAllMotor(Direction pDirection, Speed speed);


	/**
	 * Like {@link #runAllMotor(Direction, Speed)}, but only right motor.
	 *
	 * @param pDirection the direction
	 * @param pSpeed the speed
	 */
	void runRightMotor(Direction pDirection, Speed pSpeed);


	/**
	 * Like {@link #runAllMotor(Direction, Speed)}, but only left motor.
	 *
	 * @param pDirection the direction
	 * @param pSpeed the speed
	 */
	void runLeftMotor(Direction pDirection, Speed pSpeed);

	/**
	 * Stops right and left motors.
	 */
	void stopAllMotors();

	/**
	 * Stops right motor.
	 */
	void stopRightMotor();

	/**
	 * Stops left motor.
	 */
	void stopLeftMotor();

	/**
	 * Returns the status of the right motor.
	 *
	 * @return the right motor status
	 */
	MotorStatus getRightMotorStatus();

	/**
	 * Returns the status of the left motor.
	 *
	 * @return the left motor status
	 */
	MotorStatus getLeftMotorStatus();


}
