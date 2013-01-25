package de.anjaro.feature.impl;

import static de.anjaro.util.AnjaroConstants.ARG_TEST_MODE;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import de.anjaro.controller.IAnjaroController;
import de.anjaro.feature.ITwoMotorFeature;
import de.anjaro.gpio.GpioPin;
import de.anjaro.gpio.Revision;
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

	/** The Constant REVISION_PROP_NAME. => anjaro.raspberry.revision*/
	private static final String REVISION_PROP_NAME = "anjaro.raspberry.revision";

	private static final String RASPBERRY_CONFIG = "raspberry-config.properties";

	/** The Constant EXPORT. */
	private static final String EXPORT = "/sys/class/gpio/export";

	/** The Constant UNEXPORT. */
	private static final String UNEXPORT = "/sys/class/gpio/unexport";

	/** The Constant DIRECTION. */
	private static final String DIRECTION = "/sys/class/gpio/gpio%S/direction";


	/** The revision. */
	private Revision revision;

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

	/** The config. */
	private Properties config;


	@Override
	public void init(final IAnjaroController pController) throws Exception {
		LOG.entering(RaspberryServoMotorFeature.class.getName(), "init");
		this.config = new Properties();
		// TODO to make secure: can we evaluate revision label using runtime execute?
		this.config.load(this.getClass().getClassLoader().getResourceAsStream(RASPBERRY_CONFIG));
		this.revision = Revision.valueOf(this.config.getProperty(REVISION_PROP_NAME));
		if (this.revision == null) {
			throw new IllegalArgumentException("Revision must not be null. Please check " + RASPBERRY_CONFIG);
		}
		for (final Iterator<Object> it = this.config.keySet().iterator(); it.hasNext(); ) {
			final String key = (String) it.next();
			System.setProperty(key, this.config.getProperty(key));
		}
		this.initializePin(this.rightMotorPin);
		this.initializePin(this.leftMotorPin);
		LOG.exiting(RaspberryServoMotorFeature.class.getName(), "init");
	}



	/* (non-Javadoc)
	 * @see de.anjaro.service.IAnjaroService#getName()
	 */
	@Override
	public String getName() {
		return "Java local servo motor service.";
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
		this.rightMotor = new RaspberryServoMotorActor(pDirection, this.rightMotorPin, this.revision, false);
		this.rightExecutorService.execute(this.rightMotor);
	}

	/* (non-Javadoc)
	 * @see de.anjaro.service.IMotorService#runLeftMotor(de.anjaro.model.Direction, de.anjaro.model.Speed)
	 */
	@Override
	public void runLeftMotor(final Direction pDirection, final Speed pSpeed) {
		LOG.entering(RaspberryServoMotorFeature.class.getName(), "runLeftMotor");
		this.stopMotor(this.leftMotor, this.leftExecutorService);
		this.leftMotor = new RaspberryServoMotorActor(pDirection, this.leftMotorPin, this.revision, true);
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
		if ((pThread != null)) {
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
			this.write(UNEXPORT, this.rightMotorPin.getPinName(this.revision));
			this.write(UNEXPORT, this.leftMotorPin.getPinName(this.revision));
		} catch (final IOException e) {
			LOG.severe("Unable to shut down GPIO pins.");
			LOG.throwing(this.getClass().getName(), "onShutDown", e);
		}
	}



	/**
	 * Initialize pin.
	 *
	 * @param pPin the pin
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void initializePin(final GpioPin pPin) throws IOException {
		LOG.entering(RaspberryServoMotorFeature.class.getName(), "initializePin");
		this.write(EXPORT, pPin.getPinName(this.revision));
		this.write(String.format(DIRECTION, pPin.getPinName(this.revision)), "out");
	}

	/**
	 * Write.
	 *
	 * @param pFilePath the file path
	 * @param pValue the value
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void write(final String pFilePath, final String pValue) throws IOException {
		LOG.entering(RaspberryServoMotorFeature.class.getName(), "write");
		final OutputStream out = this.getOutputStream(pFilePath);
		out.write(pValue.getBytes());
		out.flush();
		out.close();
	}

	/**
	 * For testing purposes. This is not nice, but I found no nicer way so far...
	 *
	 * @param pPath the path
	 * @return the output stream
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private OutputStream getOutputStream(final String pPath) throws IOException {
		LOG.entering(RaspberryServoMotorFeature.class.getName(), "getOutputStream");
		OutputStream out;
		if (System.getProperty(ARG_TEST_MODE) != null && System.getProperty(ARG_TEST_MODE).equalsIgnoreCase("true")) {
			out = new ByteArrayOutputStream();
		} else {
			out = new FileOutputStream(pPath);
		}
		return out;
	}
}
