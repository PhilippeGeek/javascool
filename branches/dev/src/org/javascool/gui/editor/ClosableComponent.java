package org.javascool.gui.editor;

/** Interface for a component in a closable tab.
 * A component wich can be closed in a ClosableTabbedPane must implements this interface
 */
public interface ClosableComponent {
	/** Say if tab can be closed */
	public boolean isClosable();
	/** Return the name of the component in the tab */
	public String getFullName();
}
