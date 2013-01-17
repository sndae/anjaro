package de.anjaro.model;

import java.io.Serializable;

public class MotorStatus implements Serializable {

	private static final long serialVersionUID = -2066685690254514667L;
	private Direction direction;
	private Speed speed;
	private boolean active;


	public Direction getDirection() {
		return this.direction;
	}
	public void setDirection(final Direction pDirection) {
		this.direction = pDirection;
	}
	public Speed getSpeed() {
		return this.speed;
	}
	public void setSpeed(final Speed pSpeed) {
		this.speed = pSpeed;
	}
	public boolean isActive() {
		return this.active;
	}
	public void setActive(final boolean pActive) {
		this.active = pActive;
	}



}
