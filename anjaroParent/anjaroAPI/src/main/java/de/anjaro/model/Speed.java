package de.anjaro.model;

/**
 * The Enum Speed.
 */
public enum Speed {


	/** The speed0. */
	speed0(0), /** The speed1. */
	speed1(1), /** The speed2. */
	speed2(2), /** The speed3. */
	speed3(3), /** The speed4. */
	speed4(4), /** The speed5. */
	speed5(5), /** The speed6. */
	speed6(6), /** The speed7. */
	speed7(7), /** The speed8. */
	speed8(8), /** The speed9. */
	speed9(9), /** The speed10. */
	speed10(10);

	private int speedInt;

	private Speed(final int speedInt) {
		this.speedInt = speedInt;
	}

	public int getSpeedInt() {
		return this.speedInt;
	}

	public String getSpeedString() {
		return String.valueOf(this.speedInt);
	}


}
