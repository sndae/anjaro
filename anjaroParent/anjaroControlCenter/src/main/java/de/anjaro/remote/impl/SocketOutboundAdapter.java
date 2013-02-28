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
	private Socket socket;

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
		this.socket = new Socket(this.hostname, this.port);
	}

	@Override
	public void setCommandDispatcher(final ICommandDispatcher<byte[]> pCommandDispatcher) {
		this.commandDispatcher = pCommandDispatcher;

	}

	@Override
	public void shutDown() {
		try {
			this.socket.close();
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public CommandResult sendCommand(final Command pCommand) {
		CommandResult result;
		try {
			final byte[] command = this.commandDispatcher.getCommand(pCommand);
			this.socket.getOutputStream().write(command);
			boolean readNext = true;
			final ByteArrayOutputStream bout = new ByteArrayOutputStream();
			while(readNext) {
				final int part = this.socket.getInputStream().read();
				if (part != 167) {
					bout.write(part);
				} else {
					readNext = false;
				}
			}
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
		}
		return result;

	}

}
