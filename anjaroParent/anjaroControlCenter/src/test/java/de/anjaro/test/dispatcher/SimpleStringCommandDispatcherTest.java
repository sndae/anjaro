package de.anjaro.test.dispatcher;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import de.anjaro.dispatcher.impl.SimpleStringCommandDispatcher;
import de.anjaro.model.Command;
import de.anjaro.model.Direction;
import de.anjaro.model.Speed;

@RunWith(JUnit4.class)
public class SimpleStringCommandDispatcherTest {

	@Test
	public void testCommandSuccess() {
		final SimpleStringCommandDispatcher dispatcher = new SimpleStringCommandDispatcher();
		final Command command = dispatcher.getCommand("feature;method;$ENUM$de.anjaro.model.Speed.speed10;$ENUM$de.anjaro.model.Direction.forward");
		Assert.assertEquals("feature", command.getFeatureName());
		Assert.assertEquals("method", command.getMethod());
		Assert.assertEquals(Speed.speed10, command.getParams()[0]);
		Assert.assertEquals(Direction.forward, command.getParams()[1]);
	}

	@Test
	public void testCommandResultSuccess() {
		// TODO
	}

}
