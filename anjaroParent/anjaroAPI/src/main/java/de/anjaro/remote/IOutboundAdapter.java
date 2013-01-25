package de.anjaro.remote;

import de.anjaro.model.Command;
import de.anjaro.model.CommandResult;

/**
 * The Interface IOutboundAdapter.
 *
 * @param <C> the generic type
 */
public interface IOutboundAdapter<C> extends IAdapter<C> {

	/**
	 * Send command.
	 *
	 * @param pCommand the command
	 * @return the command result
	 */
	CommandResult sendCommand(Command pCommand);

}
