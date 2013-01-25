package de.anjaro.controller;
import de.anjaro.config.IConfigService;
import de.anjaro.model.Command;
import de.anjaro.model.CommandResult;
import de.anjaro.util.IShutdownListener;

/**
 * The controller handles all the lifecycle.
 * Once initialized with {@link IConfigService}, all features will be loaded
 * and initialized.
 * 
 * 
 * @author Joachim Pasquali
 */
public interface IAnjaroController {


	void init(IConfigService pConfigService) throws Exception;

	/**
	 * Has to be called before stopping the system.
	 * 
	 */
	void shutdown();


	/**
	 * Executes an command. Will return error, if feature is not available, method is unknown / does not
	 * match with method structure.
	 * 
	 * @param pCommand Command to be executed
	 * @return CommandResult, which can be success and a serializable object or an error code and message
	 */
	CommandResult execute(Command pCommand);



	void addShutdownListener(IShutdownListener pShutdownListener);


}
