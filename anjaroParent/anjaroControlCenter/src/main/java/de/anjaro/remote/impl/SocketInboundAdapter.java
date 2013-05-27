package de.anjaro.remote.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.anjaro.config.IConfigService;
import de.anjaro.dispatcher.ICommandDispatcher;
import de.anjaro.model.Command;
import de.anjaro.model.CommandResult;
import de.anjaro.remote.IInboundAdapter;

public class SocketInboundAdapter implements IInboundAdapter<byte[]> {

	public static final String LISTENER_PORT = "anjaro.connector.socket.listener.port";
	private static final Logger LOG = Logger.getLogger(SocketInboundAdapter.class.getName());
	private ICommandDispatcher<byte[]> commandDispatcher;
	private int listenerPort;
	/** The client socket. */
	private Socket clientSocket;

	/** The server socket. */
	private ServerSocket serverSocket;

	private IConfigService configService;

	@Override
	public void init(final IConfigService pConfig) throws Exception {
		try {
			this.listenerPort = Integer.parseInt(pConfig.getProperty(this.LISTENER_PORT));
			LOG.fine("Listening port is: " + this.listenerPort);
		} catch (final Exception e) {
			throw new IllegalArgumentException(this.LISTENER_PORT + " must be available as key in IConfigService#getProperty() and must be an int value");
		}
		this.configService = pConfig;
	}

	@Override
	public void setCommandDispatcher(final ICommandDispatcher<byte[]> pCommandDispatcher) {
		this.commandDispatcher = pCommandDispatcher;
	}

	@Override
	public String getName() {
		return "socket inbound adapter";
	}

	@Override
	public void run() {
		LOG.entering(this.getClass().getName(), "run");
		this.openSocket();
	}

	/**
	 * Open socket.
	 */
	private void openSocket() {
		LOG.entering(this.getClass().getName(), "openSocket");
		this.serverSocket = null;
		this.clientSocket = null;
		OutputStream out = null;
		InputStream in = null;
		try {
			this.serverSocket = new ServerSocket(this.listenerPort);
			LOG.fine("Socket successfully created on port: " + this.listenerPort);
			this.clientSocket = this.serverSocket.accept();

			while (true) {
				LOG.fine("Got connection on accepted server socket. Start reading now");
				out = this.clientSocket.getOutputStream();
				in = this.clientSocket.getInputStream();
				//			final boolean readNext = true;
				final ByteArrayOutputStream bout = new ByteArrayOutputStream();
				int b;
				while ((b = in.read()) != 167) {
					bout.write(b);
				}
				bout.flush();
				bout.close();
				final Command command = this.commandDispatcher.getCommand(bout.toByteArray());
				if (LOG.isLoggable(Level.INFO)) {
					LOG.info("Received command over socket: " + command);
				}
				final CommandResult result = this.configService.getController().execute(command);
				if (LOG.isLoggable(Level.INFO)) {
					LOG.info("Send back result over socket: " + result);
				}
				final byte[] resultByte = this.commandDispatcher.getCommandResult(result);
				out.write(resultByte);
				out.write(167);
				out.flush();
			}

		} catch (final Exception e) {
			LOG.info("Exit command from client received or line is null. Will close sockets now");
		} finally {
			try {
				this.clientSocket.close();
				this.serverSocket.close();
			} catch (final Exception e) {
				LOG.throwing(this.getClass().getName(), "openSocket", e);
			}
			this.openSocket();
		}
	}

	@Override
	public void shutDown() {
		LOG.entering(this.getClass().getName(), "shutDown");
		try {
			if (this.clientSocket != null) {
				this.clientSocket.shutdownInput();
				this.clientSocket.close();
			}
			if (this.serverSocket != null) {
				this.serverSocket.close();
			}
		} catch (final IOException e) {
			LOG.throwing(this.getClass().getName(), "shutDown", e);
		}
	}
}
