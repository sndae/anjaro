package de.anjaro.dispatcher;

import de.anjaro.exception.DispatcherException;
import de.anjaro.model.Command;
import de.anjaro.model.CommandResult;

public interface ICommandDispatcher<C> {

	Command getCommand(C pCommand) throws DispatcherException;

	C getCommandResult(CommandResult pCommandResult) throws DispatcherException;

}
