package de.anjaro.util;

import java.text.MessageFormat;
import java.util.Date;

import de.anjaro.model.CommandResult;

/**
 * The Class CommandResultHelper.
 */
public class CommandResultHelper {

	/**
	 * Creates the result.
	 *
	 * @param pError the error
	 * @param pErrorParams the error params
	 * @return the command result
	 */
	public static final CommandResult createResult(final DefaultAnjaroError pError, final Object... pErrorParams) {
		final String message = MessageFormat.format(pError.getErrorMessage(), pErrorParams);
		final CommandResult result = new CommandResult();
		result.setErrorCode(pError.getErrorCode());
		result.setErrorMessage(message);
		result.setErrorTime(new Date());
		return result;
	}



}
