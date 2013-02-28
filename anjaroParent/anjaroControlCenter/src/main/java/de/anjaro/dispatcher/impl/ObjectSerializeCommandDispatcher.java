package de.anjaro.dispatcher.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.logging.Logger;

import de.anjaro.dispatcher.ICommandDispatcher;
import de.anjaro.exception.DispatcherException;
import de.anjaro.model.Command;
import de.anjaro.model.CommandResult;
import de.anjaro.util.DefaultAnjaroError;

public class ObjectSerializeCommandDispatcher implements ICommandDispatcher<byte[]> {

	private static final Logger LOG = Logger.getLogger(ObjectSerializeCommandDispatcher.class.getName());


	@Override
	public Command getCommand(final byte[] pCommand) throws DispatcherException {
		LOG.entering(this.getClass().getName(), "getCommand");
		return (Command) this.deserialize(pCommand);
	}

	@Override
	public byte[] getCommand(final Command pCommand) throws DispatcherException {
		return this.serialize(pCommand);
	}

	private byte[] serialize(final Serializable pObject) throws DispatcherException {
		LOG.entering(this.getClass().getName(), "serialize");
		try {
			final ByteArrayOutputStream bout = new ByteArrayOutputStream();
			final ObjectOutputStream oout = new ObjectOutputStream(bout);
			oout.writeObject(pObject);
			LOG.exiting(ObjectSerializeCommandDispatcher.class.getName(), "getCommandResult");
			return bout.toByteArray();
		} catch (final Exception e) {
			LOG.throwing(this.getClass().getName(), "getCommandResult", e);
			throw new DispatcherException(DefaultAnjaroError.unableToConvertCommandResult.setMessageParams(e.getMessage(), e));
		}
	}

	private Serializable deserialize(final byte[] pObject) throws DispatcherException {
		try {
			final ObjectInputStream oin = new ObjectInputStream(new ByteArrayInputStream(pObject));
			final Command result = (Command) oin.readObject();
			LOG.exiting(ObjectSerializeCommandDispatcher.class.getName(), "getCommand");
			return result;
		} catch (final Exception e) {
			LOG.throwing(this.getClass().getName(), "getCommand", e);
			throw new DispatcherException(DefaultAnjaroError.unableToConvertToCommand.setMessageParams(e.getMessage(), e));
		}

	}



	@Override
	public CommandResult getCommandResult(final byte[] pCommandResult) throws DispatcherException {
		return (CommandResult) this.deserialize(pCommandResult);
	}



	@Override
	public byte[] getCommandResult(final CommandResult pCommandResult) throws DispatcherException {
		LOG.entering(this.getClass().getName(), "getCommandResult");
		return this.serialize(pCommandResult);
	}



}
