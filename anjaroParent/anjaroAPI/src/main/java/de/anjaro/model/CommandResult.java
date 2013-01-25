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

	/** The error code. */
	private int errorCode = 0;

	/** The error time. */
	private Date errorTime;

	/** The success result. */
	private Serializable successResult;

	/**
	 * Instantiates a new command result.
	 */
	public CommandResult() {
		super();
	}
	
	/**
	 * Gets the error code.
	 *
	 * @return error code. 0 if no error
	 */
	public int getErrorCode() {
		return this.errorCode;
	}
	
	/**
	 * Sets the error code.
	 *
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


	/**
	 * Gets the error message.
	 *
	 * @return the error message
	 */
	public String getErrorMessage() {
		return this.errorMessage;
	}




	/**
	 * Sets the error message.
	 *
	 * @param pErrorMessage the new error message
	 */
	public void setErrorMessage(final String pErrorMessage) {
		this.errorMessage = pErrorMessage;
	}




	/**
	 * Sets the success result.
	 *
	 * @param pSuccessResult the new success result
	 */
	public void setSuccessResult(final Serializable pSuccessResult) {
		this.successResult = pSuccessResult;
	}




	/**
	 * Gets the error time.
	 *
	 * @return the error time
	 */
	public Date getErrorTime() {
		return this.errorTime;
	}




	/**
	 * Sets the error time.
	 *
	 * @param pErrorTime the new error time
	 */
	public void setErrorTime(final Date pErrorTime) {
		this.errorTime = pErrorTime;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CommandResult [" + (this.errorMessage != null ? "errorMessage=" + this.errorMessage + ", " : "") + "errorCode=" + this.errorCode + ", " + (this.errorTime != null ? "errorTime=" + this.errorTime + ", " : "")
				+ (this.successResult != null ? "successResult=" + this.successResult : "") + "]";
	}





}
