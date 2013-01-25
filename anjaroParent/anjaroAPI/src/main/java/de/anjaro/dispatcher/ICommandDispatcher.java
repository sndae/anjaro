package de.anjaro.dispatcher;

import de.anjaro.exception.DispatcherException;
import de.anjaro.model.Command;
import de.anjaro.model.CommandResult;

/**
 * The Interface ICommandDispatcher.
 *
 * @param <C> the generic type
 */
public interface ICommandDispatcher<C> {

	/**
	 * Gets the command.
	 *
	 * @param pCommand the command
	 * @return the command
	 * @throws DispatcherException the dispatcher exception
	 */
	Command getCommand(C pCommand) throws DispatcherException;

	/**
	 * Gets the command result.
	 *
	 * @param pCommandResult the command result
	 * @return the command result
	 * @throws DispatcherException the dispatcher exception
	 */
	C getCommandResult(CommandResult pCommandResult) throws DispatcherException;

}
