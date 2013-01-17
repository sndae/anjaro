package de.anjaro.dispatcher.impl;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.anjaro.controller.IAnjaroController;
import de.anjaro.model.Command;
import de.anjaro.model.CommandResult;
import de.anjaro.util.CommandResultHelper;
import de.anjaro.util.AnjaroError;

public class CommandDispatcher {

	private static final Logger LOG = Logger.getLogger(CommandDispatcher.class.getName());

	public static synchronized CommandResult execute(final Command pCommand, final IAnjaroController pController) {
		final String methodString = pCommand.getMethod();
		CommandResult result = new CommandResult();
		try {
			if (LOG.isLoggable(Level.FINER)) {
				LOG.finer("Method:" + methodString);
			}
			Method method = null;
			if ((pCommand.getParams() != null) && (pCommand.getParams().length > 0)) {
				final Class<?>[] clazzArray = new Class[pCommand.getParams().length];
				for (int i = 0; i < pCommand.getParams().length; i++) {
					if (LOG.isLoggable(Level.FINER)) {
						LOG.finer("Param class" + pCommand.getParams()[i].getClass().getName());
					}
					clazzArray[i] = pCommand.getParams()[i].getClass();
				}

				method = IAnjaroController.class.getMethod(pCommand.getMethod(), clazzArray);
			} else {
				method = IAnjaroController.class.getMethod(pCommand.getMethod(), (Class<?>) null);
			}

			final Serializable objResult = (Serializable) method.invoke(pController, pCommand.getParams());
			result.setSuccessResult(objResult);
		} catch (final Exception e) {
			result = CommandResultHelper.createResult(AnjaroError.unableToCallMethod, new Object[] { methodString, e.getMessage() });
			LOG.severe(result.getErrorMessage());
			LOG.throwing(CommandDispatcher.class.getName(), "execute", e);
		}
		return result;

	}

}
