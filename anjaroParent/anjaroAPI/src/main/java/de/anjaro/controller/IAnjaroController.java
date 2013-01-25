package de.anjaro.controller;
import de.anjaro.config.IConfigService;
import de.anjaro.model.Command;
import de.anjaro.model.CommandResult;
import de.anjaro.util.IShutdownListener;

/**
 * The controller handles all the lifecycle.
 * Once initialized with {@link IConfigService}, all features and adapters should be loaded
 * and initialized.
 * During the shutdown phase, all features and adapters should be shutdown.
 * 
 * 
 * @author Joachim Pasquali
 */
public interface IAnjaroController {


	/**
	 * Inits the controller.
	 *
	 * @param pConfigService the config service
	 * @throws Exception the exception
	 */
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



	/**
	 * Adds the shutdown listener. If there is some class to be informed, when the system shuts down,
	 * it can register a shutdown listener over this method.
	 *
	 * @param pShutdownListener the shutdown listener
	 */
	void addShutdownListener(IShutdownListener pShutdownListener);


}
