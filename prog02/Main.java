package prog02;

import java.util.*;

/**
 * A program to query and modify the phone directory stored in csc220.txt.
 * 
 * @author vjm
 */

public class Main {

	/**
	 * Processes user's commands on a phone directory.
	 * 
	 * @param fn
	 *            The file containing the phone directory.
	 * @param ui
	 *            The UserInterface object to use to talk to the user.
	 * @param pd
	 *            The PhoneDirectory object to use to process the phone directory.
	 */
	public static void processCommands(String fn, UserInterface ui, PhoneDirectory pd) {
		pd.loadData(fn);

		String[] commands = { "Add/Change Entry", "Look Up Entry", "Remove Entry", "Save Directory", "Exit" };

		String name, number, oldNumber;

		while (true) {
			int c = ui.getCommand(commands);
		//	System.out.println(c);
			switch (c) {
			case -1:
				ui.sendMessage("You shut down the program, restarting.  Use Exit to exit.");
				break;
			case 0:
				name= ui.getInfo(" ENter Name");
				if (name==null) {
					break;
				}else if (name=="") {
					ui.sendMessage("NO blank");
					break;
					
				}else {
					number = ui.getInfo("number");
					if(number==null) {
						break;
					}
					else {
					 oldNumber = pd.addOrChangeEntry(name, number);
					 if (oldNumber!=null) {
						 ui.sendMessage(name+"be changed from"+oldNumber+"to "+ number);
						 break;
					 }else {
						 ui.sendMessage(name+" be added with number"+ number);
						 break;
					 }
						
					}
				}
//				name = ui.getInfo("Enter name");
//				if (name == null) {
//					break;
//				}
//				if (name.length() == 0) {
//					ui.sendMessage("no blank name please ");
//					break;
//				} else {
//					number = ui.getInfo("Enter number");
//					if (number == null) {
//						break;
//					} else {
//						oldNumber = pd.addOrChangeEntry(name, number);
//						if (oldNumber != null) {
//							ui.sendMessage(
//									"the number for " + name + "has been changed from " + oldNumber + " to " + number);
//							break;
//						} else
//							ui.sendMessage(name + " has been added with the number" + number);
//						break;
//					}
//				}

			case 1:
				name = ui.getInfo("Enter name");
				if (name != null) {
					if (name.length() != 0) {
						number = pd.lookupEntry(name);
						if (number != null) {
							ui.sendMessage(name + " " + number);
						} else
							ui.sendMessage(name + " is not listed");

					} else
						ui.sendMessage("no blank name please ");
				}
				break;
			case 2:
				name = ui.getInfo("Enter name");
				if (name != null) {
					if (name.length() != 0) {
						number = pd.removeEntry(name);
						if (number != null) {
							ui.sendMessage("Removed entry with " + name + " and number " + number);
						} else
							ui.sendMessage(name + " is not listed");

					} else
						ui.sendMessage("no blank name please ");
				}
				break;

			case 3:
				pd.save();
				ui.sendMessage("has been saved");
				break;
			case 4:
				ui.sendMessage("goodby");
				return;

			}
		}
	}

	/**
	 * 
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		/*
		 * String fn = "csc220.txt"; PhoneDirectory pd = new ArrayBasedPD();
		 * UserInterface ui = new GUI("Phone Directory"); processCommands(fn, ui, pd); }
		 */
		/*
		 * String fn = "csc220.txt"; ArrayBasedPD pd = new ArrayBasedPD(); UserInterface
		 * ui = new TestUI(); processCommands(fn, ui, pd);
		 */

		String fn = "csc220.txt";
		PhoneDirectory pd = new SortedPD();
		UserInterface ui = new GUI(fn);
		processCommands(fn, ui, pd);
	}
}