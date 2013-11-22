package de.anjaro.feature.impl;


public class Test {

	public static void main(final String[] args) {
		final ServoblasterTwoMotorFeature feature = new ServoblasterTwoMotorFeature();

		final String methodName = "setSpeed";
		final Class paramClazz = Integer.class;

		final Class[] paramArray = new Class[1];
		paramArray[0] = paramClazz;

		try {
			feature.getClass().getMethod(methodName, paramArray);
		} catch (final SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
