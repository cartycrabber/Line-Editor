package editor;

import java.util.Scanner;

import utilities.LineList;

public class Editor {

	public static void main(String[] args) {
		//Create our copy buffers
		LineList lineCopyBuffer = new LineList();
		String charCopyBuffer = "";
		
		//Create the input scanner
		Scanner in = new Scanner(System.in);
		
		//Show the menu for the first time
		showMenu();
		
		//Start the program loop
		while(true) {
			//Wait for input...
			System.out.print("->\t");
			String command = in.nextLine();
			
			//Go through the list of none commands looking for a match
			switch(command) {
			case "m"://Menu
				showMenu();
				break;
			case "l"://Load File
				break;
			case "sa"://Show All
				break;
			case "sl"://Show Line
				break;
			case "sr"://Show Range
				break;
			case "nl"://New Line
				break;
			case "el"://Edit Line
				break;
			case "dl"://Delete Line
				break;
			case "dr"://Delete Range
				break;
			case "cr"://Copy Range
				break;
			case "pl"://Paste Lines
				break;
			case "w"://Write to File
				break;
			case "q"://Quit
				break;
			case "wq"://Write and Quit
				break;
			default:
				System.out.println("Unrecognized command");
			}
		}
	}

	
	static void showMenu() {
		System.out.println(""
				+ "\tMenu:  m\t\tDelete line:  dl\n"
				+ "\tLoad file:  l\t\tDelete range:  dr\n"
				+ "\tShow all:  sa\t\tCopy range:  cr\n"
				+ "\tShow line:  sl\t\tPaste lines:  pl\n"
				+ "\tShow range:  sr\t\tWrite to file:  w\n"
				+ "\tNew line:  nl\t\tQuit:  q\n"
				+ "\tEdit line:  el\t\tWrite and quit:  wq");
	}
}
