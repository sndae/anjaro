package de.anjaro.test.adapter;

import de.anjaro.config.IConfigService;
import de.anjaro.controller.IAnjaroController;
import de.anjaro.model.Command;
import de.anjaro.model.CommandResult;
import de.anjaro.util.IShutdownListener;

public class TestController implements IAnjaroController {

	private String called;
	public static final String ERROR_MSG = "I was called";

	@Override
	public void init(final IConfigService pConfigService) throws Exception {

	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub

	}

	public String getCalled() {
		return this.called;
	}

	@Override
	public CommandResult execute(final Command pCommand) {
		this.called = pCommand.getMethod();
		final CommandResult result =  new CommandResult();
		result.setErrorMessage(ERROR_MSG);
		return result;
	}

	@Override
	public void addShutdownListener(final IShutdownListener pShutdownListener) {
		// TODO Auto-generated method stub

	}

}
