package de.anjaro.remote.impl;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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

class SocketInboundAdapter implements IInboundAdapter<byte[]> {

	private final String LISTENER_PORT = "anjaro.connector.socket.listener.port";
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
		BufferedReader in = null;
		try {
			this.serverSocket = new ServerSocket(this.listenerPort);
			this.clientSocket = this.serverSocket.accept();
			out = this.clientSocket.getOutputStream();
			in = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));

			boolean readNext = true;
			final ByteArrayOutputStream bout = new ByteArrayOutputStream();
			while(readNext) {
				final int part = in.read();
				if (part != 167) {
					bout.write(part);
				} else {
					readNext = false;
				}
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
			out.flush();

			LOG.info("Exit command from client received or line is null. Will close sockets now");
		} catch (final Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				out.close();
				in.close();
				this.clientSocket.close();
				this.serverSocket.close();
			} catch (final Exception e) {
				LOG.throwing(this.getClass().getName(), "openSocket", e);
			}
		}
	}

	@Override
	public void shutDown() {
		LOG.entering(this.getClass().getName(), "shutDown");
		try {
			this.clientSocket.shutdownInput();
			this.clientSocket.close();
			this.serverSocket.close();
		} catch (final IOException e) {
			LOG.throwing(this.getClass().getName(), "shutDown", e);
		}
	}
}
