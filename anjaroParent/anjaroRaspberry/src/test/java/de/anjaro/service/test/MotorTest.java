package de.anjaro.service.test;

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


	@Test
	public void testRightMotor() {
		final ITwoMotorFeature service = new RaspberryServoMotorFeature();
		Assert.assertEquals(2, Thread.activeCount());

		service.runRightMotor(Direction.forward, Speed.speed1);

		Assert.assertTrue(Thread.activeCount() == 3);

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
	}


}
