package de.anjaro.exception;

import de.anjaro.util.IAnjaroError;


public class DispatcherException extends AnjaroException {


	private static final long serialVersionUID = 4694678497605936567L;

	public DispatcherException(final IAnjaroError pError, final Throwable pArg1) {
		super(pError, pArg1);
	}

	public DispatcherException(final IAnjaroError pError) {
		super(pError);
	}









}
