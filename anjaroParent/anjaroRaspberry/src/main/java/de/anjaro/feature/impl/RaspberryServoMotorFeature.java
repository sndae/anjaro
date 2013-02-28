package de.anjaro.feature.impl;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import de.anjaro.controller.IAnjaroController;
import de.anjaro.feature.ITwoMotorFeature;
import de.anjaro.gpio.GpioFileWriter;
import de.anjaro.gpio.GpioPin;
import de.anjaro.model.Direction;
import de.anjaro.model.MotorStatus;
import de.anjaro.model.Speed;

/**
 * The Class RaspberryServoMotorFeature will run 2 servo motors. The servos only have
 * forward, backward and stop. Speed information will be ignored, as it is not that
 * easy in Java to define pin high peaks between 1ms and 2 ms.
 * To have more control over the speed, a jni based motor service is needed.
 * E.g. using pi4j project may enable more sofisticated operations.
 * 
 * <p>
 * Advantage of this class is, that it is simple to use without any native library
 * and also no 3rd party library
 * </p>
 * <p><b>IMPORTANT</b><br/>
 * As pins, P16 and P18 are used. These are the pins GPIO 23 and GPIO 24.
 * <br/>GPIO pins have changed between different revisions of the Raspberry. So, you will have to
 * tell the motor service, which revision of the Raspberry you are using.
 * You can figure this out using: <code>cat /proc/cpuinfo </code>on the Raspberry Pi itself.
 * <br/>
 * For the motor service, it is not really important, as GPIO 23 and GPIO 24 have not changed,
 * so you can choose whatever Revision you want :)
 * 
 * <br/>
 * For further details please see http://www.raspberrypi.org/archives/1929
 * </p>
 * 
 * 
 */
public class RaspberryServoMotorFeature implements ITwoMotorFeature {

	/** The Constant LOG. */
	private static final Logger LOG = Logger.getLogger(RaspberryServoMotorFeature.class.getName());

	/** The right motor pin. */
	private final GpioPin rightMotorPin = GpioPin.p16;

	/** The left motor pin. */
	private final GpioPin leftMotorPin = GpioPin.p18;

	/** The right motor. */
	private RaspberryServoMotorActor rightMotor;

	/** The left motor. */
	private RaspberryServoMotorActor leftMotor;

	/** The right executor service. */
	private final ExecutorService rightExecutorService = Executors.newSingleThreadExecutor();

	/** The left executor service. */
	private final ExecutorService leftExecutorService = Executors.newSingleThreadExecutor();


	@Override
	public void init(final IAnjaroController pController) throws Exception {
		LOG.entering(RaspberryServoMotorFeature.class.getName(), "init");
		GpioFileWriter.initializePin(this.rightMotorPin);
		GpioFileWriter.initializePin(this.leftMotorPin);
		LOG.exiting(RaspberryServoMotorFeature.class.getName(), "init");
	}



	/* (non-Javadoc)
	 * @see de.anjaro.service.IAnjaroService#getName()
	 */
	@Override
	public String getName() {
		return "twoMotorFeature";
	}

	/* (non-Javadoc)
	 * @see de.anjaro.service.IAnjaroService#getDescription()
	 */
	@Override
	public String getDescription() {
		return "Motor service running on GPIO pins 16 (gpio 23) and 18 (gpio 24)";
	}

	/* (non-Javadoc)
	 * @see de.anjaro.service.IMotorService#runAllMotor(de.anjaro.model.Direction, de.anjaro.model.Speed)
	 */
	@Override
	public void runAllMotor(final Direction pDirection, final Speed pSpeed) {
		LOG.entering(RaspberryServoMotorFeature.class.getName(), "runAllMotor");
		this.runRightMotor(pDirection, pSpeed);
		this.runLeftMotor(pDirection, pSpeed);
	}

	/* (non-Javadoc)
	 * @see de.anjaro.service.IMotorService#runRightMotor(de.anjaro.model.Direction, de.anjaro.model.Speed)
	 */
	@Override
	public void runRightMotor(final Direction pDirection, final Speed pSpeed) {
		LOG.entering(RaspberryServoMotorFeature.class.getName(), "runRightMotor");
		this.stopMotor(this.rightMotor, this.rightExecutorService);
		this.rightMotor = new RaspberryServoMotorActor(pDirection, this.rightMotorPin, false);
		this.rightExecutorService.execute(this.rightMotor);
	}

	/* (non-Javadoc)
	 * @see de.anjaro.service.IMotorService#runLeftMotor(de.anjaro.model.Direction, de.anjaro.model.Speed)
	 */
	@Override
	public void runLeftMotor(final Direction pDirection, final Speed pSpeed) {
		LOG.entering(RaspberryServoMotorFeature.class.getName(), "runLeftMotor");
		this.stopMotor(this.leftMotor, this.leftExecutorService);
		this.leftMotor = new RaspberryServoMotorActor(pDirection, this.leftMotorPin, true);
		this.leftExecutorService.execute(this.leftMotor);
	}


	/**
	 * Stop motor.
	 *
	 * @param pThread the thread
	 * @param pExecutorService the executor service
	 */
	private void stopMotor(final RaspberryServoMotorActor pThread, final ExecutorService pExecutorService) {
		LOG.entering(RaspberryServoMotorFeature.class.getName(), "stopMotor");
		if (pThread != null) {
			pThread.stop();
			try {
				pExecutorService.awaitTermination(30, TimeUnit.MILLISECONDS);
			} catch (final InterruptedException e) {
				LOG.warning("Executorservice shutdown interupted " + e.getMessage());
			}
		}
	}



	/* (non-Javadoc)
	 * @see de.anjaro.service.IMotorService#stopAllMotors()
	 */
	@Override
	public void stopAllMotors() {
		LOG.entering(RaspberryServoMotorFeature.class.getName(), "stopAllMotors");
		this.stopRightMotor();
		this.stopLeftMotor();
	}

	/* (non-Javadoc)
	 * @see de.anjaro.service.IMotorService#stopRightMotor()
	 */
	@Override
	public void stopRightMotor() {
		LOG.entering(RaspberryServoMotorFeature.class.getName(), "stopRightMotor");
		this.stopMotor(this.rightMotor, this.rightExecutorService);
	}

	/* (non-Javadoc)
	 * @see de.anjaro.service.IMotorService#stopLeftMotor()
	 */
	@Override
	public void stopLeftMotor() {
		LOG.entering(RaspberryServoMotorFeature.class.getName(), "stopLeftMotor");
		this.stopMotor(this.leftMotor, this.leftExecutorService);
	}

	/* (non-Javadoc)
	 * @see de.anjaro.service.IMotorService#getRightMotorStatus()
	 */
	@Override
	public MotorStatus getRightMotorStatus() {
		LOG.entering(RaspberryServoMotorFeature.class.getName(), "getRightMotorStatus");
		return this.getStatus(this.rightMotor);
	}

	/**
	 * Gets the status.
	 *
	 * @param pMotor the motor
	 * @return the status
	 */
	private MotorStatus getStatus(final RaspberryServoMotorActor pMotor) {
		LOG.entering(RaspberryServoMotorFeature.class.getName(), "getStatus");
		MotorStatus status = new MotorStatus();
		if (pMotor != null) {
			status = pMotor.getStatus();
		} else {
			status.setActive(false);
			status.setSpeed(Speed.speed0);
		}
		return status;
	}

	/* (non-Javadoc)
	 * @see de.anjaro.service.IMotorService#getLeftMotorStatus()
	 */
	@Override
	public MotorStatus getLeftMotorStatus() {
		LOG.entering(RaspberryServoMotorFeature.class.getName(), "getLeftMotorStatus");
		return this.getStatus(this.leftMotor);
	}

	/* (non-Javadoc)
	 * @see de.anjaro.service.IMotorService#onShutDown()
	 */
	@Override
	public void shutDown() {
		LOG.entering(RaspberryServoMotorFeature.class.getName(), "shutDown");
		this.stopAllMotors();
		try {
			GpioFileWriter.releasePin(this.rightMotorPin);
			GpioFileWriter.releasePin(this.leftMotorPin);
		} catch (final IOException e) {
			LOG.severe("Unable to shut down GPIO pins.");
			LOG.throwing(this.getClass().getName(), "onShutDown", e);
		}
	}



	@Override
	public void forward(final Speed pSpeed) {
		this.runAllMotor(Direction.forward, pSpeed);
	}



	@Override
	public void backward(final Speed pSpeed) {
		this.runAllMotor(Direction.backward, pSpeed);

	}



	@Override
	public void left(final Speed pSpeed) {
		this.runRightMotor(Direction.forward, pSpeed);
		this.runLeftMotor(Direction.backward, pSpeed);

	}



	@Override
	public void right(final Speed pSpeed) {
		this.runRightMotor(Direction.backward, pSpeed);
		this.runLeftMotor(Direction.forward, pSpeed);
	}





}
