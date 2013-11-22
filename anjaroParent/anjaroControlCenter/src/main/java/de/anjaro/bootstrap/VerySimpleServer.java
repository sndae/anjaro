package de.anjaro.bootstrap;



//Tue Nov  2 18:33:43 EST 2004
//
//Written by Sean R. Owens, sean at guild dot net, released to the
//public domain.  Share and enjoy.  Since some people argue that it is
//impossible to release software to the public domain, you are also free
//to use this code under any version of the GPL, LPGL, or BSD licenses,
//or contact me for use of another license.
//http://darksleep.com/player

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

//An example of a very simple socket server.  Start by looking at the
//static main() method at the bottom of this file.
public class VerySimpleServer {
	private int serverPort = 0;
	private ServerSocket serverSock = null;
	private final boolean toggleme = true;

	public VerySimpleServer(final int serverPort) {
		this.serverPort = serverPort;

		try {
			this.serverSock = new ServerSocket(serverPort);
		}
		catch (final IOException e){
			e.printStackTrace(System.err);
		}
	}

	// All this method does is wait for some bytes from the
	// connection, read them, then write them back again, until the
	// socket is closed from the other side.
	public void handleConnection(final InputStream sockInput, final OutputStream sockOutput) {
		while(true) {
			final byte[] buf=new byte[1024];
			int bytes_read = 0;
			try {
				// This call to read() will wait forever, until the
				// program on the other side either sends some data,
				// or closes the socket.
				bytes_read = sockInput.read(buf, 0, buf.length);

				// If the socket is closed, sockInput.read() will return -1.
				if(bytes_read < 0) {
					System.err.println("Tried to read from socket, read() returned < 0,  Closing socket.");
					return;
				}
				System.err.println("Received "+bytes_read
						+" bytes, sending them back to client, data="
						+(new String(buf, 0, bytes_read)));
				Thread.sleep(500);
				sockOutput.write(new String("4").getBytes());
				// This call to flush() is optional - we're saying go
				// ahead and send the data now instead of buffering
				// it.
				sockOutput.flush();
			}
			catch (final Exception e){
				System.err.println("Exception reading from/writing to socket, e="+e);
				e.printStackTrace(System.err);
				return;
			}
		}

	}

	public void waitForConnections() {
		Socket sock = null;
		InputStream sockInput = null;
		OutputStream sockOutput = null;
		while (true) {
			try {
				// This method call, accept(), blocks and waits
				// (forever if necessary) until some other program
				// opens a socket connection to our server.  When some
				// other program opens a connection to our server,
				// accept() creates a new socket to represent that
				// connection and returns.
				sock = this.serverSock.accept();
				System.err.println("Have accepted new socket.");

				// From this point on, no new socket connections can
				// be made to our server until we call accept() again.

				sockInput = sock.getInputStream();
				sockOutput = sock.getOutputStream();
			}
			catch (final IOException e){
				e.printStackTrace(System.err);
			}

			// Do something with the socket - read bytes from the
			// socket and write them back to the socket until the
			// other side closes the connection.
			this.handleConnection(sockInput, sockOutput);

			// Now we close the socket.
			try {
				System.err.println("Closing socket.");
				sock.close();
			}
			catch (final Exception e){
				System.err.println("Exception while closing socket.");
				e.printStackTrace(System.err);
			}

			System.err.println("Finished with socket, waiting for next connection.");
		}
	}

	public static void main(final String argv[]) {
		final int port = Integer.parseInt(argv[0]);
		final VerySimpleServer server = new VerySimpleServer(port);
		server.waitForConnections();
	}
}
