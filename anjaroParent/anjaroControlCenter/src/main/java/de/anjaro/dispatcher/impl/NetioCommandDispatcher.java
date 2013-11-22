package de.anjaro.dispatcher.impl;

import de.anjaro.exception.DispatcherException;
import de.anjaro.model.CommandResult;
import de.anjaro.util.DefaultAnjaroError;

public class NetioCommandDispatcher extends SimpleStringCommandDispatcher {

	/**
	 * 
	 * Not supported
	 */
	@Override
	public CommandResult getCommandResult(final byte[] pCommandResult) throws DispatcherException {
		throw new DispatcherException(DefaultAnjaroError.unableToConvertCommandResult);
	}

	@Override
	public byte[] getCommandResult(final CommandResult pCommandResult) {
		byte[] result =  String.valueOf(pCommandResult.getErrorCode()).getBytes();
		if (pCommandResult.getSuccessResult() != null) {
			result = pCommandResult.getSuccessResult().toString().getBytes();
		} 
		return result; 
	}



}
