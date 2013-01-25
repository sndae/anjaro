package de.anjaro.remote;

import de.anjaro.model.Command;
import de.anjaro.model.CommandResult;

public interface IOutboundAdapter<C> extends IAdapter<C> {

	CommandResult sendCommand(Command pCommand);

}
