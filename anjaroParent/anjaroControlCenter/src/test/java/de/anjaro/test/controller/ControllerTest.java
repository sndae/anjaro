package de.anjaro.test.controller;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import de.anjaro.config.IConfigService;
import de.anjaro.controller.IAnjaroController;
import de.anjaro.controller.impl.DefaultControllerImpl;
import de.anjaro.feature.IFeature;
import de.anjaro.model.Command;
import de.anjaro.model.CommandResult;
import de.anjaro.remote.IInboundAdapter;
import de.anjaro.util.DefaultAnjaroError;
import de.anjaro.util.IShutdownListener;


@RunWith(JUnit4.class)
public class ControllerTest implements IShutdownListener {

	private boolean shutdown = false;
	private TestFeature feature;
	private IInboundAdapter<String> adapter;

	@Test
	public void testControllerInitExecute() {
		final IConfigService config = this.getConfig();
		final IAnjaroController controller = config.getController();
		final Command command = new Command();
		command.setFeatureName("testFeature");
		command.setMethod("anAction");
		command.setParams("MyString");
		try {
			controller.init(config);
			Mockito.verify(this.feature).init(controller);
			Mockito.verify(this.adapter).init(config);
			final CommandResult result = controller.execute(command);
			Assert.assertEquals("okidoki", result.getSuccessResult());
			Assert.assertEquals(DefaultAnjaroError.success.getErrorCode(), result.getErrorCode());
			Assert.assertNull(result.getErrorMessage());
		} catch (final Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testControllerShutDown() {
		final IConfigService config = this.getConfig();
		final IAnjaroController controller = config.getController();
		try {
			controller.init(config);
			controller.addShutdownListener(this);
			controller.shutdown();
			Assert.assertEquals(true, this.shutdown);
			// check that shutdown of feature was called
			Mockito.verify(this.feature).shutDown();
			Mockito.verify(this.adapter).shutDown();

		} catch (final Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	private IConfigService getConfig() {
		this.feature = Mockito.mock(TestFeature.class);
		Mockito.when(this.feature.getName()).thenReturn("testFeature");
		Mockito.when(this.feature.anAction("MyString")).thenReturn("okidoki");
		final List<IFeature> featureList = new ArrayList<IFeature>();
		featureList.add(this.feature);

		this.adapter = Mockito.mock(IInboundAdapter.class);
		final List<IInboundAdapter> adapterList = new ArrayList<IInboundAdapter>();
		adapterList.add(this.adapter);



		final TestConfiguration config = Mockito.mock(TestConfiguration.class);
		Mockito.when(config.getFeatureList()).thenReturn(featureList);
		Mockito.when(config.getInboundAdapterList()).thenReturn(adapterList);
		Mockito.when(config.getController()).thenReturn((new DefaultControllerImpl()));
		return config;
	}


	@Override
	public void shutDown() {
		this.shutdown = true;
	}


}
