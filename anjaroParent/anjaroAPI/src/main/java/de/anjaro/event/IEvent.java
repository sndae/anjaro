package de.anjaro.event;

public interface IEvent<T> {

	String getSensorId();

	String getName();

	T getValue();


}
