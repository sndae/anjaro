package de.anjaro.remote;

import de.anjaro.model.Command;
import de.anjaro.model.CommandResult;

public interface IOutboundAdapter extends IAdapter {

	CommandResult sendCommand(Command pCommand);

}
