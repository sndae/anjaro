package de.anjaro.exception;

import de.anjaro.util.IAnjaroError;


/**
 * The Class DispatcherException.
 * 
 * @author Joachim Pasquali
 */
public class DispatcherException extends AnjaroException {


	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4694678497605936567L;

	/**
	 * Instantiates a new dispatcher exception.
	 *
	 * @param pError the error
	 * @param pArg1 the arg1
	 */
	public DispatcherException(final IAnjaroError pError, final Throwable pArg1) {
		super(pError, pArg1);
	}

	/**
	 * Instantiates a new dispatcher exception.
	 *
	 * @param pError the error
	 */
	public DispatcherException(final IAnjaroError pError) {
		super(pError);
	}









}
