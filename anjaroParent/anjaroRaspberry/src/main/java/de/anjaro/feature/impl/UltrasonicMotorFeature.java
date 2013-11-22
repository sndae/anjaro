package de.anjaro.feature.impl;

import java.util.logging.Logger;

import de.anjaro.controller.IAnjaroController;
import de.anjaro.event.IEvent;
import de.anjaro.event.IEventListener;
import de.anjaro.feature.IFeature;
import de.anjaro.feature.ISimpleTwoMotorFeature;
import de.anjaro.model.Command;

/**
 * 
 * Works together with the {@link ISimpleTwoMotorFeature}.
 * 
 * You will have to make a configservice with 2 sensor Ids with the name {@link #RIGHT_MOTOR_SENSOR} and
 * {@link #LEFT_MOTOR_SENSOR}
 * 
 * @author jpasquali
 * 
 */
public class UltrasonicMotorFeature implements IFeature, IEventListener {

	private static final Logger LOG = Logger.getLogger(UltrasonicMotorFeature.class.getName());

	private IAnjaroController controller;

	public static final String RIGHT_MOTOR_SENSOR = "RIGHT_MOTOR_SENSOR";
	public static final String LEFT_MOTOR_SENSOR = "LEFT_MOTOR_SENSOR";

	private boolean rightSensorContact = false;
	private boolean leftSensorContact = false;
	private boolean isProcessing;
	private final Command command = new Command(ISimpleTwoMotorFeature.NAME, ISimpleTwoMotorFeature.COMMANDS.stop.name());

	@Override
	public void onEvent(final IEvent<?> pEvent) {
		LOG.entering(UltrasonicMotorFeature.class.getName(), "onEvent");
		if (pEvent.getSensorId().equals(RIGHT_MOTOR_SENSOR)) {
			this.rightSensorContact = pEvent.getValue().equals("high");
		} else {
			this.leftSensorContact = pEvent.getValue().equals("high");
		}
		try {
			if (this.requestProcessHandle()) {
				if (this.rightSensorContact && this.leftSensorContact) {
					this.backward();
					this.command.setMethod(ISimpleTwoMotorFeature.COMMANDS.left.name());
					if (this.rightSensorContact) {
						this.left();
					} else {
						this.right();
					}
				} else if (this.leftSensorContact) {
					this.right();
				} else if (this.rightSensorContact) {
					this.left();
				} 
				this.command.setMethod(ISimpleTwoMotorFeature.COMMANDS.forward.name());
				this.controller.execute(this.command);
				this.isProcessing = false;
			}
		} catch (final InterruptedException e) {
			LOG.throwing(this.getClass().getName(), "onEvent", e);
		}
		LOG.exiting(UltrasonicMotorFeature.class.getName(), "onEvent");
	}

	private synchronized void left() throws InterruptedException {
		LOG.entering(UltrasonicMotorFeature.class.getName(), "left");
		this.command.setMethod(ISimpleTwoMotorFeature.COMMANDS.left.name());
		this.controller.execute(this.command);
		Thread.sleep(1000);
		if (this.rightSensorContact) {
			this.left();
		}
		LOG.exiting(UltrasonicMotorFeature.class.getName(), "left");
	}

	private synchronized void right() throws InterruptedException {
		LOG.entering(UltrasonicMotorFeature.class.getName(), "right");
		this.command.setMethod(ISimpleTwoMotorFeature.COMMANDS.right.name());
		this.controller.execute(this.command);
		Thread.sleep(1000);
		if (this.leftSensorContact) {
			this.right();
		}
		LOG.exiting(UltrasonicMotorFeature.class.getName(), "right");
	}



	private synchronized void backward() throws InterruptedException {
		LOG.entering(UltrasonicMotorFeature.class.getName(), "backward");
		this.command.setMethod(ISimpleTwoMotorFeature.COMMANDS.backward.name());
		this.controller.execute(this.command);
		Thread.sleep(1000);
		if (this.rightSensorContact && this.leftSensorContact) {
			this.backward();
		}
		LOG.exiting(UltrasonicMotorFeature.class.getName(), "backward");
	}

	/**
	 * Request processing. If processing is already in process, this method will return 
	 * false. Otherwise true.
	 * 
	 * @return
	 */
	private synchronized boolean requestProcessHandle() {
		boolean plsProceed = false;
		if (!this.isProcessing) {
			this.isProcessing = true;
			plsProceed = true;
		}
		return plsProceed;
	}

	@Override
	public String getName() {
		return "Ultrasonic motor feature";
	}

	@Override
	public String getDescription() {
		return "based on events with sensor id RIGHT_MOTOR_SENSOR and LEFT_MOTOR_SENSOR, this feature moves the robot"
				+ "forward, right, left to avoid hitting something";
	}

	@Override
	public void shutDown() {
		//nothing to do

	}

	@Override
	public void init(final IAnjaroController pController) throws Exception {
		LOG.entering(UltrasonicMotorFeature.class.getName(), "init");
		this.controller = pController;
		LOG.exiting(UltrasonicMotorFeature.class.getName(), "init");

	}

}
