package de.anjaro.remote.impl;

import java.util.logging.Logger;

import de.anjaro.controller.IAnjaroController;
import de.anjaro.dispatcher.ICommandDispatcher;
import de.anjaro.remote.IInboundAdapter;

public class BluetoothInboundAdapter implements IInboundAdapter {

	private IAnjaroController controller;

	private ICommandDispatcher commandDispatcher;

	private static final Logger LOG = Logger.getLogger(BluetoothInboundAdapter.class.getName());

	@Override
	public String getName() {
		return "Bluetoothadapter";
	}

	@Override
	public void init(final IAnjaroController pController) throws Exception {
		LOG.entering(BluetoothInboundAdapter.class.getName(), "init");
		this.controller = pController;

	}

	@Override
	public void setCommandDispatcher(final ICommandDispatcher pCommandDispatcher) {
		this.commandDispatcher = pCommandDispatcher;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

	@Override
	public void shutDown() {
		// TODO Auto-generated method stub

	}
}
