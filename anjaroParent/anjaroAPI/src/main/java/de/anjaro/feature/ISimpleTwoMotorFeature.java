package de.anjaro.feature;


public interface ISimpleTwoMotorFeature extends IFeature {

	static final String NAME = "twoMotorFeature";

	enum COMMANDS { stop, forward, backward, right, left, getSpeed, setSpeed};

	void stop();

	void forward();

	void backward();

	void right();

	void left();

	int getSpeed();

	void setSpeed(Integer pSpeed);

	void setRightMediumValue(Integer pValue);

	void setLeftMediumValue(Integer pValue);

	int getRightMediumValue();

	int getLeftMediumValue();


}
