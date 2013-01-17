package de.anjaro.gpio;


/**
 * See http://www.raspberrypi.org/archives/1929#comment-31646
 * @author pippo
 *
 */
public enum Revision {

	code2(1), code3(1), code4(2), code5(2), code6(2);

	private int revision;

	private Revision(final int revision) {
		this.revision = revision;
	}

	public int getRevision() {
		return this.revision;
	}

}
