package de.anjaro.remote.impl;

import java.util.logging.Logger;

import de.anjaro.controller.IAnjaroController;
import de.anjaro.dispatcher.ICommandDispatcher;
import de.anjaro.model.Command;
import de.anjaro.model.CommandResult;
import de.anjaro.remote.IOutboundAdapter;

public class SocketOutboundAdapter implements IOutboundAdapter<byte[]> {

	private static final Logger LOG = Logger.getLogger(SocketOutboundAdapter.class.getName());
	private ICommandDispatcher<byte[]> commandDispatcher;

	@Override
	public String getName() {
		return "socketOutboundAdapter";
	}

	@Override
	public void init(final IAnjaroController pController) throws Exception {
		LOG.entering(this.getClass().getName(), "init");


	}

	@Override
	public void setCommandDispatcher(final ICommandDispatcher<byte[]> pCommandDispatcher) {
		this.commandDispatcher = pCommandDispatcher;

	}

	@Override
	public void shutDown() {
		// TODO Auto-generated method stub

	}


	@Override
	public CommandResult sendCommand(final Command pCommand) {
		return null;
	}

}
