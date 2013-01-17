package de.anjaro.util;

import java.text.MessageFormat;
import java.util.Date;

import de.anjaro.model.CommandResult;

public class CommandResultHelper {

	public static final CommandResult createResult(final AnjaroError pError, final Object... pErrorParams) {
		final String message = MessageFormat.format(pError.getErrorMessage(), pErrorParams);
		final CommandResult result = new CommandResult();
		result.setErrorCode(pError.getErrorCode());
		result.setErrorMessage(message);
		result.setErrorTime(new Date());
		return result;
	}



}
