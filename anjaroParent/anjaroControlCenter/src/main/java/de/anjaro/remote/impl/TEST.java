package de.anjaro.remote.impl;

import java.io.ByteArrayOutputStream;

public class TEST {


	public static void main(final String[] args) {
		final ByteArrayOutputStream s = new ByteArrayOutputStream();
		s.write(126);

		System.out.println(new String(s.toByteArray()));
	}

}
