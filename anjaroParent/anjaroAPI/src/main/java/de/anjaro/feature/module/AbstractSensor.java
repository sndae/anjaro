package de.anjaro.feature.module;

import java.util.Properties;

import de.anjaro.controller.IAnjaroController;


public abstract class AbstractSensor implements ISensor {

	private final String id;
	protected IAnjaroController controller;

	public AbstractSensor(final String pId) {
		super();
		this.id = pId;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public void init(final IAnjaroController pController) throws Exception {
		this.controller = pController;
	}

	@Override
	public void adjust(final Properties pValues) {
		// Nothing to do here. Please overwrite,if you need
	}



}

