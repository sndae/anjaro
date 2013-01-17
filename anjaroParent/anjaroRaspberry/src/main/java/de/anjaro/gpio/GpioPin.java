package de.anjaro.gpio;

/**
 * See http://elinux.org/RPi_Low-level_peripherals#GPIO_Driving_Example_.28Bash_shell_script.29
 * 
 * @author pippo
 *
 */
public enum GpioPin {

	p3("0","2"), p5("1","3"), p7("4","4"), p11("17","17"), p13("21","27"), p15("22","22"), p19("10","10"), p21("9","9"), p23("11","11"),
	p8("14","14"), p10("15","15"), p12("18","18"), p16("23","23"), p18("24","24"), p22("25","25"), p24("8","8"), p26("7","7");

	private String rev1;
	private String rev2;
	private GpioPin(final String pRev1, final String pRev2) {
		this.rev1 = pRev1;
		this.rev2 = pRev2;
	}

	public String getPinName(final Revision pRevision) {
		if (pRevision == null) {
			throw new IllegalArgumentException("Revision must not be null");
		}
		switch (pRevision.getRevision()) {

		case 1: return this.rev1;
		case 2: return this.rev2;
		default: throw new IllegalArgumentException("Revision not valid: " + pRevision.getRevision());
		}
	}




}

