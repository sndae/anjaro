package de.anjaro.exception;

import de.anjaro.util.IAnjaroError;

/**
 * The Class AnjaroException.
 * 
 * @author Joachim Pasquali
 */
public class AnjaroException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2191700774781244017L;

	/** The error. */
	private final IAnjaroError error;

	/**
	 * Instantiates a new anjaro exception.
	 *
	 * @param pError the error
	 */
	public AnjaroException(final IAnjaroError pError) {
		super();
		this.error = pError;
	}

	/**
	 * Instantiates a new anjaro exception.
	 *
	 * @param pError the error
	 * @param pArg1 the arg1
	 */
	public AnjaroException(final IAnjaroError pError, final Throwable pArg1) {
		super(pError != null ? pError.getFormattedMessage() : null, pArg1);
		this.error = pError;
	}


	/**
	 * Gets the anjaro error.
	 *
	 * @return the anjaro error
	 */
	public IAnjaroError getAnjaroError() {
		return this.error;
	}
}
