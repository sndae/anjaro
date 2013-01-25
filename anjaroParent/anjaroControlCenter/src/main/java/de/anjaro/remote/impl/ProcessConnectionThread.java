package de.anjaro.remote.impl;

//import java.awt.Robot;
//import java.awt.event.KeyEvent;
//import java.io.InputStream;

public class ProcessConnectionThread implements Runnable {

	//	private final StreamConnection mConnection;

	// Constant that indicate command from devices
	private static final int EXIT_CMD = -1;
	private static final int KEY_RIGHT = 1;
	private static final int KEY_LEFT = 2;

	//	public ProcessConnectionThread(final StreamConnection connection) {
	//		this.mConnection = connection;
	//	}

	@Override
	public void run() {
		//		try {
		//
		//			// prepare to receive data
		//			final InputStream inputStream = this.mConnection.openInputStream();
		//
		//			System.out.println("waiting for input");
		//
		//			while (true) {
		//				final int command = inputStream.read();
		//
		//				if (command == EXIT_CMD) {
		//					System.out.println("finish process");
		//					break;
		//				}
		//
		//				this.processCommand(command);
		//			}
		//		} catch (final Exception e) {
		//			e.printStackTrace();
		//		}
	}

	/**
	 * Process the command from client
	 * 
	 * @param command
	 *            the command code
	 */
	//	private void processCommand(final int command) {
	//		try {
	//			final Robot robot = new Robot();
	//			switch (command) {
	//			case KEY_RIGHT:
	//				robot.keyPress(KeyEvent.VK_RIGHT);
	//				System.out.println("Right");
	//				// release the key after it is pressed. Otherwise the event just
	//				// keeps getting trigged
	//				robot.keyRelease(KeyEvent.VK_RIGHT);
	//				break;
	//			case KEY_LEFT:
	//				robot.keyPress(KeyEvent.VK_LEFT);
	//				System.out.println("Left");
	//				// release the key after it is pressed. Otherwise the event just
	//				// keeps getting trigged
	//				robot.keyRelease(KeyEvent.VK_LEFT);
	//				break;
	//			}
	//		} catch (final Exception e) {
	//			e.printStackTrace();
	//		}
	//	}
}
