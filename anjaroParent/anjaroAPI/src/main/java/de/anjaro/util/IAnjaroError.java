package de.anjaro.util;

/**
 * The Interface IAnjaroError.
 */
public interface IAnjaroError {


	/**
	 * Gets the error code.
	 *
	 * @return the error code
	 */
	int getErrorCode();


	/**
	 * Gets the error message.
	 *
	 * @return the error message
	 */
	String getErrorMessage();

	/**
	 * Gets the formatted message.
	 *
	 * @return the formatted message
	 */
	String getFormattedMessage();

	/**
	 * Sets the message params.
	 *
	 * @param pParams the params
	 * @return the i anjaro error
	 */
	IAnjaroError setMessageParams(Object... pParams);

}
