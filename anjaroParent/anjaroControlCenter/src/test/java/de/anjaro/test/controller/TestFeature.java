package de.anjaro.test.controller;

import java.util.logging.Logger;

import de.anjaro.controller.IAnjaroController;
import de.anjaro.feature.IFeature;

public class TestFeature implements IFeature {

	private static final Logger LOG = Logger.getLogger(TestFeature.class.getName());

	@Override
	public String getName() {
		return "testFeature";
	}

	@Override
	public String getDescription() {
		return "only for test purposes";
	}

	@Override
	public void shutDown() {
		LOG.entering(TestFeature.class.getName(), "shutDown");
	}

	@Override
	public void init(final IAnjaroController pController) throws Exception {
		LOG.entering(TestFeature.class.getName(), "init");
	}

	public String anAction(final String pAParam) {
		LOG.entering(TestFeature.class.getName(), "anAction");
		return "jupsiee";
	}

}
