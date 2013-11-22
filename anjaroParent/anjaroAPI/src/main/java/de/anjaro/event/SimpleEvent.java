package de.anjaro.event;

public class SimpleEvent implements IEvent<String> {

	private final String value;
	private final String sensorId;

	public final static String EVENT_NAME = "SIMPLE_EVENT";

	public SimpleEvent(final String pSensorId, final String pValue) {
		super();
		this.value = pValue;
		this.sensorId = pSensorId;
	}

	@Override
	public String getValue() {
		return this.value;
	}

	@Override
	public String getSensorId() {
		return this.sensorId;
	}

	@Override
	public String getName() {
		return EVENT_NAME;
	}

}
