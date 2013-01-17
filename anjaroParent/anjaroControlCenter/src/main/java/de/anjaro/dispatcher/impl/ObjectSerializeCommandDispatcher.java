package de.anjaro.dispatcher.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Logger;

import de.anjaro.controller.IAnjaroController;
import de.anjaro.dispatcher.ICommandDispatcher;
import de.anjaro.model.Command;
import de.anjaro.model.CommandResult;
import de.anjaro.util.CommandResultHelper;
import de.anjaro.util.AnjaroError;

public class ObjectSerializeCommandDispatcher implements ICommandDispatcher {

	private IAnjaroController controller;
	private static final Logger LOG = Logger.getLogger(ObjectSerializeCommandDispatcher.class.getName());


	@Override
	public byte[] execute(final byte[] pCommand) {
		CommandResult result;
		try {
			final ObjectInputStream oin = new ObjectInputStream(new ByteArrayInputStream(pCommand));
			final Command command = (Command) oin.readObject();
			result = CommandDispatcher.execute(command, this.controller);
		} catch (final Exception e) {
			result = CommandResultHelper.createResult(AnjaroError.unknownOrTechnicalError, e.getMessage());
			LOG.severe(result.getErrorMessage());
			LOG.throwing(this.getClass().getName(), "runCommand", e);
		}
		final ByteArrayOutputStream bout = new ByteArrayOutputStream();
		try {
			final ObjectOutputStream oout = new ObjectOutputStream(bout);
			oout.writeObject(result);
		} catch (final IOException e) {
			// FIXME not nice solution.
			throw new RuntimeException(e);
		}
		return bout.toByteArray();

	}

	@Override
	public void setController(final IAnjaroController pController) {
		this.controller = pController;
	}


}
