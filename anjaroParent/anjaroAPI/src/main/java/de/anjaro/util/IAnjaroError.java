package de.anjaro.util;

public interface IAnjaroError {


	int getErrorCode();


	String getErrorMessage();

	String getFormattedMessage();

	IAnjaroError setMessageParams(Object... pParams);

}
