package de.anjaro.util;


/**
 * The Enum AnjaroError. Central enum for all error messages, which are send back to
 * sender (e.g. remote call from client)
 */
public enum AnjaroError {

	/** The invalid json command. */
	success(0, "successful"),


	/** The invalid json command. */
	invalidJsonCommand(1000, "Invalid json command: %s"),

	/** The unable to call method. */
	unableToCallMethod(1001, "Unable to call method: %s. Error message is: %s"),


	unknownOrTechnicalError(2000, "Unexpecte exception occured. Error message is: %s");




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
	private AnjaroError(final int pErrorCode, final String pErrorMessage) {
		this.errorCode = pErrorCode;
		this.errorMessage = pErrorMessage;
	}


	public int getErrorCode() {
		return this.errorCode;
	}


	public String getErrorMessage() {
		return this.errorMessage;
	}





}
