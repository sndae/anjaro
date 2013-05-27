package de.anjaro.remote.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

import de.anjaro.config.IConfigService;
import de.anjaro.dispatcher.ICommandDispatcher;
import de.anjaro.dispatcher.impl.ObjectSerializeCommandDispatcher;
import de.anjaro.exception.DispatcherException;
import de.anjaro.model.Command;
import de.anjaro.model.CommandResult;
import de.anjaro.remote.IOutboundAdapter;
import de.anjaro.util.CommandResultHelper;
import de.anjaro.util.DefaultAnjaroError;

public class SocketOutboundAdapter implements IOutboundAdapter<byte[]> {

	private static final Logger LOG = Logger.getLogger(SocketOutboundAdapter.class.getName());
	private ICommandDispatcher<byte[]> commandDispatcher = new ObjectSerializeCommandDispatcher();

	private String hostname;
	private int port;

	@Override
	public String getName() {
		return "socketOutboundAdapter";
	}

	@Override
	public void init(final IConfigService pConfig) throws Exception {
		LOG.entering(this.getClass().getName(), "init");
		this.hostname = pConfig.getProperty("hostname");
		try {
			this.port = Integer.parseInt(pConfig.getProperty("port"));
		} catch (final Exception e) {
			throw new IllegalArgumentException("Property 'port' must be available and an int value in IConfigService#getProperty()", e);
		}
		if (this.hostname == null) {
			throw new IllegalArgumentException("Property 'hostname' must be available in IConfigService#getProperty()");
		}
	}

	@Override
	public void setCommandDispatcher(final ICommandDispatcher<byte[]> pCommandDispatcher) {
		this.commandDispatcher = pCommandDispatcher;

	}

	@Override
	public void shutDown() {
	}


	@Override
	public CommandResult sendCommand(final Command pCommand) {
		CommandResult result;
		Socket socket = null;
		try {
			final byte[] command = this.commandDispatcher.getCommand(pCommand);
			socket = new Socket(this.hostname, this.port);
			socket.getOutputStream().write(command);
			socket.getOutputStream().write(167);
			socket.getOutputStream().flush();
			final ByteArrayOutputStream bout = new ByteArrayOutputStream();
			int b;
			while((b =  socket.getInputStream().read()) != 167 ) {
				bout.write(b);
			}
			socket.getInputStream().close();
			bout.flush();
			bout.close();
			try {
				result = this.commandDispatcher.getCommandResult(bout.toByteArray());
			} catch (final Exception e) {
				e.printStackTrace();
				result = CommandResultHelper.createResult(DefaultAnjaroError.unableToConvertCommandResult, e.getMessage());
			}
		} catch (final DispatcherException e) {
			e.printStackTrace();
			result = CommandResultHelper.createResult(DefaultAnjaroError.unableToConvertToCommand, e.getMessage());
		} catch (final IOException e) {
			e.printStackTrace();
			result = CommandResultHelper.createResult(DefaultAnjaroError.unknownOrTechnicalError, e.getMessage());
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (final IOException e) {
					LOG.throwing(this.getClass().getName(), "sendCommand", e);
					result = CommandResultHelper.createResult(DefaultAnjaroError.unknownOrTechnicalError, e.getMessage());
				}
			}
		}
		return result;
	}

}
