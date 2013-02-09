package de.anjaro.feature.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Logger;

import de.anjaro.controller.IAnjaroController;
import de.anjaro.feature.module.IActor;
import de.anjaro.gpio.GpioFileWriter;
import de.anjaro.gpio.GpioPin;
import de.anjaro.model.Direction;
import de.anjaro.model.MotorStatus;
import de.anjaro.model.Speed;

/**
 * The Class RaspberryServoMotorActor. For each motor, one thread will of this class
 * will be started.
 * In case, the motor does not run properly, 2 parameters can be adjusted.<br/>
 * Normally, we get a good speed forward and backward using 0 and 200 for values.
 * A servo is running in one direction, when having 1ms high peak followed by 10 to 20ms
 * low signal. The other direction we do have full speed, when having 2ms high peal followed
 * by 10 to 20ms 0 signal. However, it is not easy in java to match this short peak times,
 * as {@link Thread#sleep(long, int)} does not really work (I tried out).
 * To have a work around, I used Thread.sleep(0), which takes some microseconds and
 * a loop running around it. 0 is a good loop count for one direction and 200 seems to be good for the other direction.
 * However, it might be, that for other servos, these values should be changed.
 * <br/> Use {@link #LOOP_HIGH_PROP} and {@link #LOOP_LOW_PROP} in the raspberry properties file
 */
public class RaspberryServoMotorActor implements IActor {

	/** The Constant LOG. */
	private static final Logger LOG = Logger.getLogger(RaspberryServoMotorActor.class.getName());

	/** The Constant LOOP_LOW_PROP. => anjaro.raspberry.servo.low.loop.value */
	private static final String LOOP_LOW_PROP = "anjaro.raspberry.servo.low.loop.value";

	/** The Constant LOOP_HIGH_PROP. => anjaro.raspberry.servo.high.loop.value*/
	private static final String LOOP_HIGH_PROP = "anjaro.raspberry.servo.high.loop.value";


	/** The status. */
	private final MotorStatus status = new MotorStatus();

	/** The running. */
	private boolean running = true;

	/** The pin. */
	private final GpioPin pin;


	/** The forward. */
	private final int forward;

	/** The backward. */
	private final int backward;




	@Override
	public void init(final IAnjaroController pController) throws Exception {

	}

	/**
	 * Instantiates a new raspberry servo motor thread. Reads {@link #LOOP_HIGH_PROP} and {@link #LOOP_LOW_PROP} from system properties
	 * to setup servo speed.
	 * 
	 *
	 * @param pDirection the direction forward or backward...
	 * @param pPin the pin where the motor is connected
	 * @param pRevision the revision Revision of the raspberry pi board as described in {@link RaspberryServoMotorFeature}
	 * @param pChangeDirection true, if the motor should run in the opposite direction than normal
	 */
	public RaspberryServoMotorActor(final Direction pDirection, final GpioPin pPin, final boolean pChangeDirection) {
		LOG.entering(RaspberryServoMotorActor.class.getName(), "RaspberryServoMotorActor");
		this.status.setDirection(pDirection);
		this.status.setSpeed(Speed.speed0);
		this.pin = pPin;
		int lowValue = 0;
		try {
			lowValue = Integer.parseInt(System.getProperty(LOOP_LOW_PROP, "0"));
		} catch (final NumberFormatException e) {
			LOG.warning("Unable to evaluate low value. Must be integer.I will use default value. Low was: " + System.getProperty(LOOP_LOW_PROP) );
		}
		int highValue = 200;
		try {
			highValue = Integer.parseInt(System.getProperty(LOOP_HIGH_PROP, "200"));
		} catch (final NumberFormatException e) {
			LOG.warning("Unable to evaluate high value. Must be integer. Use default values. High was: " + System.getProperty(LOOP_HIGH_PROP));
		}
		if (pChangeDirection) {
			this.forward = highValue;
			this.backward = lowValue;
		} else {
			this.forward = lowValue;
			this.backward = highValue;
		}
		LOG.exiting(RaspberryServoMotorActor.class.getName(), "RaspberryServoMotorActor");

	}


	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		LOG.entering(RaspberryServoMotorActor.class.getName(), "run");
		Thread.currentThread().setName("ServoMotorThreadForPin" + this.pin.name());
		synchronized (this) {
			this.status.setActive(true);

			int counter = this.forward;
			if (this.status.getDirection().equals(Direction.backward)) {
				counter = this.backward;
			}
			this.status.setSpeed(Speed.speed10);
			OutputStream out = null;
			try {
				out = GpioFileWriter.getOutputStream(this.pin);
				while (this.running) {
					out.write("1".getBytes());
					for (int i = 0; i < counter; i++) {
						Thread.sleep(0);
					}
					out.write("0".getBytes());
					Thread.sleep(10);
				}
				// wait another 10 millis, so parent get wait
				this.status.setActive(false);
				this.status.setSpeed(Speed.speed0);
			} catch (final FileNotFoundException e) {
				LOG.throwing(this.getClass().getName(), "run", e);
			} catch (final IOException e) {
				LOG.throwing(this.getClass().getName(), "run", e);
			} catch (final InterruptedException e) {
				LOG.throwing(this.getClass().getName(), "run", e);
			} finally {
				try {
					if (out != null) {
						out.close();
					}
				} catch (final IOException e) {
					LOG.warning("Unable to close outputstream");
					LOG.throwing(this.getClass().getName(), "run", e);
				}
			}

		}

	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public MotorStatus getStatus() {
		LOG.entering(RaspberryServoMotorActor.class.getName(), "getStatus");
		return this.status;
	}

	/**
	 * Stop.
	 */
	public void stop() {
		LOG.entering(RaspberryServoMotorActor.class.getName(), "stop");
		this.running = false;
	}

}
