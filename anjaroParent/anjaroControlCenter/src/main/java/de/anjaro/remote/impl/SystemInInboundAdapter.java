package de.anjaro.remote.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;

import de.anjaro.controller.IAnjaroController;
import de.anjaro.dispatcher.ICommandDispatcher;
import de.anjaro.exception.DispatcherException;
import de.anjaro.model.CommandResult;
import de.anjaro.remote.IInboundAdapter;

public class SystemInInboundAdapter implements IInboundAdapter<String> {

	private IAnjaroController controller;

	private ICommandDispatcher<String> commandDispatcher;

	private boolean shutdown = false;

	private static final Logger LOG = Logger.getLogger(SystemInInboundAdapter.class.getName());

	@Override
	public String getName() {
		return "System in inbound Adapter";
	}

	@Override
	public void init(final IAnjaroController pController) throws Exception {
		this.controller = pController;

	}

	@Override
	public void setCommandDispatcher(final ICommandDispatcher<String> pCommandDispatcher) {
		this.commandDispatcher = pCommandDispatcher;

	}

	@Override
	public void shutDown() {
		// TODO: check, if this is working ...
		try {
			System.in.close();
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.shutdown = true;

	}

	@Override
	public void run() {
		LOG.entering(SystemInInboundAdapter.class.getName(), "run");
		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		while (!this.shutdown) {
			try {
				final String command = reader.readLine();
				final CommandResult result = this.controller.execute(this.commandDispatcher.getCommand(command));
				System.out.println(this.commandDispatcher.getCommandResult(result));
			} catch (final IOException e) {
				LOG.throwing(this.getClass().getName(), "run", e);
				System.out.println(e.getMessage());
			} catch (final DispatcherException e) {
				LOG.throwing(this.getClass().getName(), "run", e);
				System.out.println(e.getMessage());
			}
		}
		LOG.exiting(SystemInInboundAdapter.class.getName(), "run");

	}

}
