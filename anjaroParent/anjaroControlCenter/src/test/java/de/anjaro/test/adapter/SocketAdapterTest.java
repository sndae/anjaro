package de.anjaro.test.adapter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import de.anjaro.config.IConfigService;
import de.anjaro.dispatcher.impl.ObjectSerializeCommandDispatcher;
import de.anjaro.model.Command;
import de.anjaro.model.CommandResult;
import de.anjaro.remote.impl.SocketInboundAdapter;
import de.anjaro.remote.impl.SocketOutboundAdapter;

@RunWith(JUnit4.class)
public class SocketAdapterTest {

	private final IConfigService configService = new SocketAdapterTestConfig();

	@Test
	public void testSocketAdapter() {
		try {
			final SocketOutboundAdapter outbound = new SocketOutboundAdapter();
			outbound.setCommandDispatcher(new ObjectSerializeCommandDispatcher());
			outbound.init(this.configService);

			final SocketInboundAdapter inbound = new SocketInboundAdapter();
			inbound.setCommandDispatcher(new ObjectSerializeCommandDispatcher());
			inbound.init(this.configService);
			final ExecutorService executor = Executors.newSingleThreadExecutor();

			executor.execute(inbound);
			final Command command = new Command();
			command.setFeatureName("myFeature");
			command.setMethod("myMethod");

			final CommandResult result = outbound.sendCommand(command);
			Assert.assertEquals(TestController.ERROR_MSG, result.getErrorMessage());



		} catch (final Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
