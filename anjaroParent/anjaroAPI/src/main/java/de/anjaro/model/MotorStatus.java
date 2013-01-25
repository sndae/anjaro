package de.anjaro.model;

import java.io.Serializable;

/**
 * The Class MotorStatus.
 */
public class MotorStatus implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2066685690254514667L;
	
	/** The direction. */
	private Direction direction;
	
	/** The speed. */
	private Speed speed;
	
	/** The active. */
	private boolean active;


	/**
	 * Gets the direction.
	 *
	 * @return the direction
	 */
	public Direction getDirection() {
		return this.direction;
	}
	
	/**
	 * Sets the direction.
	 *
	 * @param pDirection the new direction
	 */
	public void setDirection(final Direction pDirection) {
		this.direction = pDirection;
	}
	
	/**
	 * Gets the speed.
	 *
	 * @return the speed
	 */
	public Speed getSpeed() {
		return this.speed;
	}
	
	/**
	 * Sets the speed.
	 *
	 * @param pSpeed the new speed
	 */
	public void setSpeed(final Speed pSpeed) {
		this.speed = pSpeed;
	}
	
	/**
	 * Checks if is active.
	 *
	 * @return true, if is active
	 */
	public boolean isActive() {
		return this.active;
	}
	
	/**
	 * Sets the active.
	 *
	 * @param pActive the new active
	 */
	public void setActive(final boolean pActive) {
		this.active = pActive;
	}



}
