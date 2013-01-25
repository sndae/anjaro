package de.anjaro.service.test;

import static de.anjaro.util.AnjaroConstants.ARG_TEST_MODE;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import de.anjaro.feature.ITwoMotorFeature;
import de.anjaro.feature.impl.RaspberryServoMotorFeature;
import de.anjaro.model.Direction;
import de.anjaro.model.MotorStatus;
import de.anjaro.model.Speed;

/**
 * 
 * @author Joachim Pasquali
 *
 */
@RunWith(JUnit4.class)
public class MotorTest {

	public MotorTest() {
		System.setProperty(ARG_TEST_MODE, "true");
	}

	@Test
	public void testRightMotor() {
		try {
			final ITwoMotorFeature service = new RaspberryServoMotorFeature();
			service.init(null);
			final int activeCount = Thread.activeCount();

			service.runRightMotor(Direction.forward, Speed.speed1);

			Assert.assertEquals(activeCount + 1, Thread.activeCount());

			synchronized (this) {
				try {
					Thread.sleep(20);
				} catch (final InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			MotorStatus rStatus = service.getRightMotorStatus();
			Assert.assertTrue(rStatus.isActive());
			Assert.assertEquals(rStatus.getDirection(), Direction.forward);
			Assert.assertEquals(rStatus.getSpeed(), Speed.speed10);

			Assert.assertFalse(service.getLeftMotorStatus().isActive());

			service.runRightMotor(Direction.backward, Speed.speed1);


			synchronized (this) {
				try {
					Thread.sleep(20);
				} catch (final InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			rStatus = service.getRightMotorStatus();

			Assert.assertTrue(rStatus.isActive());
			Assert.assertEquals(rStatus.getDirection(), Direction.backward);
			Assert.assertEquals(rStatus.getSpeed(), Speed.speed10);
			Assert.assertFalse(service.getLeftMotorStatus().isActive());

			service.stopAllMotors();
		} catch (final Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}


}
