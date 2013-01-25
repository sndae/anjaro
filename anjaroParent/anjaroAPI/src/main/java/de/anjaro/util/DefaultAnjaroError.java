package de.anjaro.util;

import java.text.MessageFormat;


/**
 * The Enum DefaultAnjaroError. Central enum for all error messages, which are send back to
 * sender (e.g. remote call from client)
 */
public enum DefaultAnjaroError implements IAnjaroError {

	/** The invalid json command. */
	success(0, "successful"),


	/** The invalid json command. */
	invalidJsonCommand(1000, "Invalid json command: %s"),

	/** The unable to call method. */
	unableToCallMethod(1001, "Unable to call method: %s. Error message is: %s"),

	/** The feature not available. */
	featureNotAvailable(1002, "Feature is not available: %s."),

	/** The unable to convert to command. */
	unableToConvertToCommand(1100, "Unable to convert incoming parameter to Command object. Exception: %s"),

	/** The unable to convert command result. */
	unableToConvertCommandResult(1101, "Unable to convert CommandResult. Exception: %s"),

	/** The unknown or technical error. */
	unknownOrTechnicalError(2000, "Unexpecte exception occured. Error message is: %s");

	/** The params. */
	private Object[] params;


	/** The error code. */
	private final int errorCode;

	/** The error message. */
	private final String errorMessage;


	/**
	 * Instantiates a new Anjaro error.
	 * Adds the actual timestamp
	 *
	 * @param pErrorCode the error code
	 * @param pErrorMessage the error message
	 */
	private DefaultAnjaroError(final int pErrorCode, final String pErrorMessage) {
		this.errorCode = pErrorCode;
		this.errorMessage = pErrorMessage;
	}


	/* (non-Javadoc)
	 * @see de.anjaro.util.IAnjaroError#getErrorCode()
	 */
	@Override
	public int getErrorCode() {
		return this.errorCode;
	}


	/* (non-Javadoc)
	 * @see de.anjaro.util.IAnjaroError#getErrorMessage()
	 */
	@Override
	public String getErrorMessage() {
		return this.errorMessage;
	}




	/* (non-Javadoc)
	 * @see de.anjaro.util.IAnjaroError#getFormattedMessage()
	 */
	@Override
	public String getFormattedMessage() {
		return MessageFormat.format(this.getErrorMessage(), this.params);
	}




	/* (non-Javadoc)
	 * @see de.anjaro.util.IAnjaroError#setMessageParams(java.lang.Object[])
	 */
	@Override
	public IAnjaroError setMessageParams(final Object... pParams) {
		this.params = pParams;
		return this;
	}


}
