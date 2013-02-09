package de.anjaro.gpio;

import static de.anjaro.util.AnjaroConstants.ARG_TEST_MODE;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Logger;

import de.anjaro.feature.impl.RaspberryServoMotorFeature;
import de.anjaro.raspberry.util.RaspberryConfig;

public class GpioFileWriter {


	private static final Logger LOG = Logger.getLogger(GpioFileWriter.class.getName());

	/** The Constant EXPORT. */
	private static final String EXPORT = "/sys/class/gpio/export";

	/** The Constant UNEXPORT. */
	private static final String UNEXPORT = "/sys/class/gpio/unexport";

	/** The Constant DIRECTION. */
	private static final String DIRECTION = "/sys/class/gpio/gpio%S/direction";

	/** The Constant VALUE. */
	private static final String VALUE = "/sys/class/gpio/gpio%s/value";


	/**
	 * Initialize pin.
	 *
	 * @param pPin the pin
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static final void initializePin(final GpioPin pPin) throws IOException {
		LOG.entering(GpioFileWriter.class.getName(), "initializePin");
		try {
			GpioFileWriter.write(EXPORT, pPin.getPinName(RaspberryConfig.getRevision()));
		} catch (final Exception e) {
			GpioFileWriter.write(UNEXPORT, pPin.getPinName(RaspberryConfig.getRevision()));
			GpioFileWriter.write(EXPORT, pPin.getPinName(RaspberryConfig.getRevision()));
		}
		GpioFileWriter.write(String.format(DIRECTION, pPin.getPinName(RaspberryConfig.getRevision())), "out");
	}

	public static final void releasePin(final GpioPin pPin) throws IOException {
		GpioFileWriter.write(UNEXPORT, pPin.getPinName(RaspberryConfig.getRevision()));
	}

	public static final void setValue(final boolean pValue, final GpioPin pPin) throws IOException {
		final String formattedOut = String.format(VALUE, pPin.getPinName(RaspberryConfig.getRevision()));
		final OutputStream out = getOutputStream(formattedOut);
		if (pValue) {
			out.write("1".getBytes());
		} else {
			out.write("0".getBytes());
		}
		out.flush();
		out.close();

	}

	public static final OutputStream getOutputStream(final GpioPin pPin) throws IOException {
		final String formattedOut = String.format(VALUE, pPin.getPinName(RaspberryConfig.getRevision()));
		return getOutputStream(formattedOut);
	}

	/**
	 * Write.
	 *
	 * @param pFilePath the file path
	 * @param pValue the value
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private static final void write(final String pFilePath, final String pValue) throws IOException {
		LOG.entering(RaspberryServoMotorFeature.class.getName(), "write");
		final OutputStream out = getOutputStream(pFilePath);
		out.write(pValue.getBytes());
		out.flush();
		out.close();
	}


	/**
	 * For testing purposes. This is not nice, but I found no nicer way so far...
	 *
	 * @param pPath the path
	 * @return the output stream
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private static final OutputStream getOutputStream(final String pPath) throws IOException {
		LOG.entering(RaspberryServoMotorFeature.class.getName(), "getOutputStream");
		OutputStream out;
		if (System.getProperty(ARG_TEST_MODE) != null && System.getProperty(ARG_TEST_MODE).equalsIgnoreCase("true")) {
			out = new ByteArrayOutputStream();
		} else {
			out = new FileOutputStream(pPath);
		}
		return out;
	}


}
