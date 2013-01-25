package de.anjaro.test.dispatcher;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import de.anjaro.dispatcher.impl.ObjectSerializeCommandDispatcher;
import de.anjaro.model.Command;
import de.anjaro.model.CommandResult;
import de.anjaro.model.Speed;
import de.anjaro.util.CommandResultHelper;
import de.anjaro.util.DefaultAnjaroError;

@RunWith(JUnit4.class)
public class ObjectSerializeCommandDispatcherTest {


	@Test
	public void testObjectSerirializeCommandSuccess() {
		try {
			final Command command = new Command();
			command.setFeatureName("feature");
			command.setMethod("method");
			command.setParams(new Serializable[]{Speed.speed0});
			final ByteArrayOutputStream bout = new ByteArrayOutputStream();

			final ObjectOutputStream oout = new ObjectOutputStream(bout);
			oout.writeObject(command);
			bout.flush();
			bout.close();

			final ObjectSerializeCommandDispatcher dispatcher = new ObjectSerializeCommandDispatcher();
			final Command commandDisp = dispatcher.getCommand(bout.toByteArray());

			Assert.assertEquals(command.getFeatureName(), commandDisp.getFeatureName());
			Assert.assertEquals(command.getParams()[0], commandDisp.getParams()[0]);
			Assert.assertEquals(command.getMethod(), commandDisp.getMethod());

		} catch (final Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}

	}

	@Test
	public void testObjectSerializeCommandResultSuccess() {
		try {
			final ObjectSerializeCommandDispatcher objectSerializeCommandDispatcher = new ObjectSerializeCommandDispatcher();
			final CommandResult result = CommandResultHelper.createResult(DefaultAnjaroError.featureNotAvailable, "myFeature");
			final byte[] byteResult = objectSerializeCommandDispatcher.getCommandResult(result);
			final ObjectInputStream oin = new ObjectInputStream(new ByteArrayInputStream(byteResult));
			final CommandResult resultDisp = (CommandResult) oin.readObject();
			Assert.assertEquals(resultDisp.getErrorCode(), DefaultAnjaroError.featureNotAvailable.getErrorCode());
			Assert.assertEquals(resultDisp.getErrorMessage(), DefaultAnjaroError.featureNotAvailable.getErrorMessage());
		} catch (final Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}


}
