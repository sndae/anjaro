package de.anjaro.util;

/**
 * The listener interface for receiving IShutdown events.
 * The class that is interested in processing a IShutdown
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addIShutdownListener<code> method. When
 * the IShutdown event occurs, that object's appropriate
 * method is invoked.
 *
 * @see IShutdownEvent
 */
public interface IShutdownListener {

	/**
	 * Shut down.
	 */
	void shutDown();

}
