package de.anjaro.remote.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;

import de.anjaro.config.IConfigService;
import de.anjaro.controller.IAnjaroController;
import de.anjaro.dispatcher.ICommandDispatcher;
import de.anjaro.model.Command;
import de.anjaro.model.CommandResult;
import de.anjaro.remote.IInboundAdapter;

public class BluetoothInboundAdapter implements IInboundAdapter<byte[]> {

	private IAnjaroController controller;

	private ICommandDispatcher<byte[]> commandDispatcher;

	private static final Logger LOG = Logger.getLogger(BluetoothInboundAdapter.class.getName());

	@Override
	public String getName() {
		return "BluetoothInboundAdapter";
	}

	@Override
	public void init(final IConfigService pConfig) throws Exception {
		LOG.entering(this.getClass().getName(), "init");
		this.controller = pConfig.getController();

	}

	@Override
	public void setCommandDispatcher(final ICommandDispatcher<byte[]> pCommandDispatcher) {
		this.commandDispatcher = pCommandDispatcher;
	}

	@Override
	public void run() {
		LOG.entering(this.getClass().getName(), "run");
		LocalDevice local = null;
		StreamConnectionNotifier notifier;
		StreamConnection connection = null;
		try {
			local = LocalDevice.getLocalDevice();
			local.setDiscoverable(DiscoveryAgent.GIAC);
			final UUID uuid = new UUID("04c6093b00001000800000805f9b34fb", false);
			LOG.fine(uuid.toString());
			final String url = "btspp://localhost:" + uuid.toString() + ";name=RemoteBluetooth";
			notifier = (StreamConnectionNotifier)Connector.open(url);
		} catch (final BluetoothStateException e) {
			LOG.severe("Bluetooth is switched off");
			LOG.throwing(this.getClass().getName(), "run", e);
			return;
		} catch (final IOException e) {
			LOG.throwing(this.getClass().getName(), "run", e);
			return;
		}

		while(true) {
			try {
				LOG.info("Bluetooth inbound adapter waits for connection");
				connection = notifier.acceptAndOpen();
				final InputStream inputStream = connection.openInputStream();
				final OutputStream responseStream = connection.openOutputStream();
				while (true) {
					LOG.info("Bluetooth inbound adapter waits for input");
					boolean readNext = true;
					final ByteArrayOutputStream bout = new ByteArrayOutputStream();
					while(readNext) {
						final int part = inputStream.read();
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
						LOG.info("Received command over bluetooth: " + command);
					}
					final CommandResult result = this.controller.execute(command);
					if (LOG.isLoggable(Level.INFO)) {
						LOG.info("Send back result over bluetooth: " + result);
					}

					final byte[] resultByte = this.commandDispatcher.getCommandResult(result);
					responseStream.write(resultByte);
					responseStream.flush();
				}
			} catch (final Exception e) {
				LOG.throwing(this.getClass().getName(), "run", e);
				LOG.exiting(this.getClass().getName(), "run");
				return;
			}
		}
	}


	@Override
	public void shutDown() {
		LOG.entering(this.getClass().getName(), "shutDown");
		// TODO
	}
}
