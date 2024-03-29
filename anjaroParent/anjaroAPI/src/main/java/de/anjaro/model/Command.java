package de.anjaro.model;

import java.io.Serializable;
import java.util.Arrays;

/**
 * The Class Command.
 * 
 * @author Joachim Pasquali
 */
public class Command implements Serializable {


	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 567595956434734947L;

	/** The feature name. */
	private String featureName;

	/** The method. */
	private String method;

	/** The params. */
	private Serializable[] params;


	public Command() {
		super();
	}

	public Command(final String pFeatureName, final String pMethod) {
		super();
		this.featureName = pFeatureName;
		this.method = pMethod;
	}

	/**
	 * Gets the method.
	 *
	 * @return the method
	 */
	public String getMethod() {
		return this.method;
	}

	/**
	 * Sets the method.
	 *
	 * @param pMethod the new method
	 */
	public void setMethod(final String pMethod) {
		this.method = pMethod;
	}

	/**
	 * Gets the params.
	 *
	 * @return the params
	 */
	public Serializable[] getParams() {
		return this.params;
	}

	/**
	 * Sets the params.
	 *
	 * @param pParams the new params
	 */
	public void setParams(final Serializable... pParams) {
		this.params = pParams;
	}

	/**
	 * Gets the feature name.
	 *
	 * @return the feature name
	 */
	public String getFeatureName() {
		return this.featureName;
	}

	/**
	 * Sets the feature name.
	 *
	 * @param pFeatureName the new feature name
	 */
	public void setFeatureName(final String pFeatureName) {
		this.featureName = pFeatureName;
	}

	@Override
	public String toString() {
		return "Command [" + (this.featureName != null ? "featureName=" + this.featureName + ", " : "") + (this.method != null ? "method=" + this.method + ", " : "")
				+ (this.params != null ? "params=" + Arrays.toString(this.params) : "") + "]";
	}






}
