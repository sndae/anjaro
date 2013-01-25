package de.anjaro.dispatcher;

import de.anjaro.exception.DispatcherException;
import de.anjaro.model.Command;
import de.anjaro.model.CommandResult;
import de.anjaro.remote.IAdapter;

/**
 * The Interface ICommandDispatcher, dispatches incoming values to {@link Command} objects
 * and again back converts the {@link CommandResult} to the appropriate format.
 *
 * @param <C> the type, which is known by the adapter, which uses the dispatcher
 * 
 * @see IAdapter
 * 
 * @author Joachim Pasquali
 */
public interface ICommandDispatcher<C> {

	/**
	 * Gets the command.
	 *
	 * @param pCommand the command in adapter specific format (String, byte[], etc.)
	 * @return the command as an object
	 * @throws DispatcherException if given parameter value cannot be converted to a command object
	 */
	Command getCommand(C pCommand) throws DispatcherException;

	/**
	 * Gets the command result.
	 *
	 * @param pCommandResult the command result
	 * @return the command result in the adapter specific format (String, byte[], etc.)
	 * @throws DispatcherException if the CommandResult object cannot be converted to the appropriate format
	 */
	C getCommandResult(CommandResult pCommandResult) throws DispatcherException;

}
