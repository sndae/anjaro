package de.anjaro.exception;

import de.anjaro.util.IAnjaroError;

public class AnjaroException extends Exception {

	private static final long serialVersionUID = -2191700774781244017L;

	private final IAnjaroError error;

	public AnjaroException(final IAnjaroError pError) {
		super();
		this.error = pError;
	}

	public AnjaroException(final IAnjaroError pError, final Throwable pArg1) {
		super(pError != null ? pError.getFormattedMessage() : null, pArg1);
		this.error = pError;
	}


	public IAnjaroError getAnjaroError() {
		return this.error;
	}
}
