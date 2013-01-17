package de.anjaro.model;

import java.io.Serializable;
import java.util.Date;

/**
 * The Class CommandResult.
 */
public class CommandResult implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5772483425559829364L;


	/** The error message. */
	private String errorMessage;

	private int errorCode = 0;

	private Date errorTime;

	/** The success result. */
	private Serializable successResult;

	public CommandResult() {
		super();
	}




	/**
	 * @return error code. 0 if no error
	 */
	public int getErrorCode() {
		return this.errorCode;
	}




	/**
	 * @param pErrorCode sets the erro code.
	 * @See
	 */
	public void setErrorCode(final int pErrorCode) {
		this.errorCode = pErrorCode;
	}





	/**
	 * Gets the success result.
	 *
	 * @return the success result
	 */
	public Object getSuccessResult() {
		return this.successResult;
	}







	public String getErrorMessage() {
		return this.errorMessage;
	}




	public void setErrorMessage(final String pErrorMessage) {
		this.errorMessage = pErrorMessage;
	}




	public void setSuccessResult(final Serializable pSuccessResult) {
		this.successResult = pSuccessResult;
	}




	public Date getErrorTime() {
		return this.errorTime;
	}




	public void setErrorTime(final Date pErrorTime) {
		this.errorTime = pErrorTime;
	}



}
