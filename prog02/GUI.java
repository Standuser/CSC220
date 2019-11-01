
package prog02;

import javax.swing.*;

/**
 * An implementation of UserInterface using popup windows and buttons.
 * @author vjm
 */
public class GUI implements UserInterface {
	private String windowTitle;

	/** Creates a new instance of GUI 
	 */
	//String windowTitle
	public GUI(String windowTitle) {
	}

	/** presents set of commands for user to choose one of
        @param commands the commands to choose from
        @return the index of the command in the array
	 */
	public int getCommand (String[] commands) {
		return JOptionPane.showOptionDialog
				(null, // No parent
						"Select a Command", // Prompt message
						windowTitle, // Window title
						JOptionPane.YES_NO_CANCEL_OPTION, // Option type
						JOptionPane.QUESTION_MESSAGE, // Message type
						null, // Icon
						commands, // List of commands
						commands[commands.length -1]);
	}

	/** tell the user something
	@param message string to print out to the user
	 */
	public void sendMessage (String message) {
		JOptionPane.showMessageDialog(null, message);
	}

	/** prompts the user for a string
	@param prompt the request
	@return what the user enters, null if nothing
	 */
	public String getInfo (String prompt) {
		return JOptionPane.showInputDialog(prompt);
	}

}
