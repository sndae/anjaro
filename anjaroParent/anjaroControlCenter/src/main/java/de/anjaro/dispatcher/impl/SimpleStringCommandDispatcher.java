package de.anjaro.dispatcher.impl;

import java.io.Serializable;
import java.util.logging.Logger;

import de.anjaro.dispatcher.ICommandDispatcher;
import de.anjaro.exception.DispatcherException;
import de.anjaro.model.Command;
import de.anjaro.model.CommandResult;

public class SimpleStringCommandDispatcher implements ICommandDispatcher<byte[]> {

	private static final Logger LOG = Logger.getLogger(SimpleStringCommandDispatcher.class.getName());



	@Override
	public byte[] getCommand(final Command pCommand) throws DispatcherException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CommandResult getCommandResult(final byte[] pCommandResult) throws DispatcherException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Command getCommand(final byte[] pCommand) {
		LOG.entering(SimpleStringCommandDispatcher.class.getName(), "getCommand");
		final String commandString = new String(pCommand);
		final String[] commandSplit = commandString.split(";");
		final String featureName = commandSplit[0];
		final String method = commandSplit[1];
		final Command command = new Command();
		if (commandSplit.length > 2) {
			final Serializable[] paramArray = new Serializable[commandSplit.length -2];
			for (int i = 2; i < commandSplit.length; i++) {
				paramArray[i-2] = this.getParam(commandSplit[i]);
			}
			command.setParams(paramArray);
		}
		command.setFeatureName(featureName);
		command.setMethod(method);
		LOG.exiting(SimpleStringCommandDispatcher.class.getName(), "getCommand");
		return command;
	}

	@Override
	public byte[] getCommandResult(final CommandResult pCommandResult) {
		return pCommandResult.toString().getBytes();
	}

	private Serializable getParam(final String pParam) {
		Serializable result = pParam;
		if (pParam.startsWith("$ENUM$")) {
			result = this.handleEnum(pParam);
		} else {
			try {
				result = Integer.valueOf(pParam);
			} catch (final NumberFormatException e) {
				// probably no int value
				try {
					result = Double.valueOf(pParam);
				} catch (final NumberFormatException e1) {
					// probably no double value
				}
			}
		}
		return result;
	}

	private Serializable handleEnum(final String pStringParam) {
		LOG.entering(SimpleStringCommandDispatcher.class.getName(), "handleEnum");
		Serializable result = null;
		try {
			final String className = pStringParam.substring(6, pStringParam.lastIndexOf('.'));
			final String enumConstant = pStringParam.substring(pStringParam.lastIndexOf('.')+1);
			final Class<?> c = Class.forName(className);
			for (int i = 0; i < c.getEnumConstants().length; i++) {
				final Object o = c.getEnumConstants()[i];
				if (o.toString().equals(enumConstant)) {
					result = (Serializable) o;
					break;
				}
			}
		} catch (final ClassNotFoundException e) {
			LOG.throwing(this.getClass().getName(), "handleEnum", e);
			LOG.warning("Cannot convert enum: " + e.getMessage());
		}
		LOG.exiting(SimpleStringCommandDispatcher.class.getName(), "handleEnum");
		return result;
	}


}
