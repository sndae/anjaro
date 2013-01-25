package de.anjaro.model;

import java.io.Serializable;

public class Command implements Serializable {


	private static final long serialVersionUID = 567595956434734947L;
	private String featureName;
	private String method;
	private Serializable[] params;

	public String getMethod() {
		return this.method;
	}
	public void setMethod(final String pMethod) {
		this.method = pMethod;
	}

	public Serializable[] getParams() {
		return this.params;
	}

	public void setParams(final Serializable... pParams) {
		this.params = pParams;
	}

	public String getFeatureName() {
		return this.featureName;
	}
	public void setFeatureName(final String pFeatureName) {
		this.featureName = pFeatureName;
	}






}
