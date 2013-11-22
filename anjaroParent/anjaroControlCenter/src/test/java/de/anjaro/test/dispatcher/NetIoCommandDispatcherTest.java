package de.anjaro.test.dispatcher;



import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import de.anjaro.dispatcher.impl.NetioCommandDispatcher;
import de.anjaro.model.CommandResult;

@RunWith(JUnit4.class)
public class NetIoCommandDispatcherTest {

	@Test
	public void testNetioDispatcher() {
		final NetioCommandDispatcher dispatcher = new NetioCommandDispatcher();

		final CommandResult result = new CommandResult();
		result.setErrorCode(0);
		result.setSuccessResult(new Integer(4));

		final byte[] byteResult = dispatcher.getCommandResult(result);

		Assert.assertEquals("4", new String(byteResult));
	}
}
