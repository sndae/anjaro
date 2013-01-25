package de.anjaro.remote.impl;

import java.io.BufferedReader;

import de.anjaro.dispatcher.ICommandDispatcher;

/**
 * The Class SocketListenerThread opens a listener Socket and forwards all incoming commands to the.
 *
 * {@link ICommandDispatcher}.
 * The Socket reads whole lines using {@link BufferedReader#readLine()}. So, you should send the
 * commands in one line.
 * 
 * Socket can be closed, when sending EXIT as command.
 */
//class SocketListenerThread implements IInboundAdapter<byte[]> {
class SocketListenerThread  {
	//
	//	/** The listener port properties key => anjaro.connector.socket.listener.port */
	//	private final String LISTENER_PORT = "anjaro.connector.socket.listener.port";
	//
	//	/** The dispatcher. */
	//	private ICommandDispatcher<byte[], byte[]> dispatcher;
	//
	//	/** The Constant LOG. */
	//	private static final Logger LOG = Logger.getLogger(SocketListenerThread.class.getName());
	//
	//	/** The client socket. */
	//	private Socket clientSocket;
	//
	//	/** The server socket. */
	//	private ServerSocket serverSocket;
	//
	//	/** port number, the socket is listening on. Default is 4444 */
	//	private final int port;
	//
	//	private IAnjaroController controller;
	//
	//	/**
	//	 * Instantiates a new socket listener thread.
	//	 *
	//	 * @param pDispatcher the dispatcher
	//	 */
	//	SocketListenerThread(final ICommandDispatcher<byte[], byte[]> pDispatcher) {
	//		LOG.entering(SocketListenerThread.class.getName(), "SocketListenerThread");
	//		this.dispatcher = pDispatcher;
	//		this.port = Integer.parseInt(System.getProperty(this.LISTENER_PORT, String.valueOf(4444)));
	//		LOG.exiting(SocketListenerThread.class.getName(), "SocketListenerThread");
	//	}
	//
	//
	//	/* (non-Javadoc)
	//	 * @see java.lang.Runnable#run()
	//	 */
	//	@Override
	//	public void run() {
	//		LOG.entering(SocketListenerThread.class.getName(), "run");
	//		this.openSocket();
	//	}
	//
	//
	//
	//
	//	@Override
	//	public String getName() {
	//		return "Server socket inbound adapter";
	//	}
	//
	//
	//	@Override
	//	public void init(final IAnjaroController pController) throws Exception {
	//		this.controller = pController;
	//
	//	}
	//
	//
	//	@Override
	//	public void setCommandDispatcher(final ICommandDispatcher<byte[], byte[]> pCommandDispatcher) {
	//		this.dispatcher = pCommandDispatcher;
	//
	//	}
	//
	//
	//	/**
	//	 * Open socket.
	//	 */
	//	private void openSocket() {
	//		LOG.entering(SocketListenerThread.class.getName(), "openSocket");
	//		this.serverSocket = null;
	//		this.clientSocket = null;
	//		OutputStream out = null;
	//		BufferedReader in = null;
	//		try {
	//			this.serverSocket = new ServerSocket(this.port);
	//			this.clientSocket = this.serverSocket.accept();
	//			out = this.clientSocket.getOutputStream();
	//			in = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
	//			String inputLine;
	//
	//			out.write("READY\n".getBytes());
	//
	//			while ((inputLine = in.readLine()) != null) {
	//				final int byteInt = in.read();
	//				LOG.info("Received command: " + inputLine);
	//				byte[] result;
	//				try {
	//					final Command command = this.dispatcher.getCommand(inputLine.getBytes());
	//					final CommandResult commandResult = this.controller.execute(command);
	//					result = this.dispatcher.getCommandResult(commandResult);
	//				} catch (final Exception e) {
	//					// TODO not nice....
	//					result = e.getMessage().getBytes();
	//				}
	//				out.write(result);
	//			}
	//			LOG.info("Exit command from client received or line is null. Will close sockets now");
	//		} catch (final IOException e) {
	//			// TODO Auto-generated catch block
	//			e.printStackTrace();
	//		} finally {
	//			try {
	//				out.close();
	//				in.close();
	//				this.clientSocket.close();
	//				this.serverSocket.close();
	//			} catch (final Exception e) {
	//				LOG.throwing(this.getClass().getName(), "openSocket", e);
	//			}
	//
	//		}
	//	}
	//
	//	/**
	//	 * Shut down.
	//	 */
	//	@Override
	//	public void shutDown() {
	//		LOG.entering(this.getClass().getName(), "shutDown");
	//		try {
	//			this.clientSocket.shutdownInput();
	//			this.clientSocket.close();
	//			this.serverSocket.close();
	//		} catch (final IOException e) {
	//			LOG.throwing(this.getClass().getName(), "shutDown", e);
	//		}
	//	}

}
