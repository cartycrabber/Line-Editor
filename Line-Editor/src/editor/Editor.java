package editor;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import utilities.LineList;

public class Editor {
	
	//Create static class-wide document
	static Document doc;

	public static void main(String[] args) {
		//Create our copy buffers
		LineList lineCopyBuffer = new LineList();
		String charCopyBuffer = "";
		
		//Create a blank document
		doc = new Document();
		
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
				System.out.println("\tEnter file name to load");
				System.out.print("->");
				//Wait for them to type in a file
				String path = in.nextLine();
				//Try to read the file at path. if there is an exception, the file could not be found
				try {
					loadFileIntoDocument(path);
				} catch (FileNotFoundException e) {
					System.out.println("\tPath not found");
				}
				break;
			case "sa"://Show All
				showAll();
				break;
			case "sl"://Show Line
				//Prompt for line number
				System.out.print("Which line?");
				String line = in.nextLine();
				//Try to turn what they typed into an int. if parseInt throws an exception then they messed up
				try {
					showLine(Integer.parseInt(line));
				} catch (Exception e) {
					//What they typed in either wasn't a number or it was out of bounds of the document
					//Either way we don't care, just go back to the main menu
					System.out.println("Invalid line number");
				}
				break;
			case "sr"://Show Range
				//Prompt for start and end line numbers
				System.out.print("Starting line?\t");
				String start = in.nextLine();
				//Check and make sure this is valid input
				int startLine;
				try {
					startLine = Integer.parseInt(start);
					//If it's out of bounds of the doc, throw an exception so we go into the catch
					if((startLine <= 0) || (startLine > doc.length()))
						throw new Exception();
				} catch (Exception e) {
					System.out.println("Invalid line number");
					//Just skip over everything else in this loop and go back to the menu
					continue;
				}
				
				System.out.print("Ending line?\t");
				String end = in.nextLine();
				//Check and make sure this is valid input
				int endLine;
				try {
					endLine = Integer.parseInt(end);
					//If it's out of bounds of the doc, throw an exception so we go into the catch
					if((endLine <= 0) || (endLine > doc.length()))
						throw new Exception();
				} catch (Exception e) {
					System.out.println("Invalid line number");
					//Just skip over everything else in this loop and go back to the menu
					continue;
				}
				
				//Make sure starting line is not after ending line
				if(startLine > endLine) {
					System.out.println("Start line cannot be greater than end line");
					//skip over everything else in this cycle
					continue;
				}
				
				//Now that we know the line numbers are good, print them out
				showRange(startLine, endLine);
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
		//Just go through printing all the options out like they are in the example, using tabs for spacing
		System.out.println(""
				+ "\tMenu:  m\t\tDelete line:  dl\n"
				+ "\tLoad file:  l\t\tDelete range:  dr\n"
				+ "\tShow all:  sa\t\tCopy range:  cr\n"
				+ "\tShow line:  sl\t\tPaste lines:  pl\n"
				+ "\tShow range:  sr\t\tWrite to file:  w\n"
				+ "\tNew line:  nl\t\tQuit:  q\n"
				+ "\tEdit line:  el\t\tWrite and quit:  wq");
	}
	
	/**
	 * Reads the file at specified path into the specified document
	 * @param path
	 * @param doc
	 * @throws FileNotFoundException if the path does not point to an existing file
	 */
	static void loadFileIntoDocument(String path) throws FileNotFoundException {
		//try to open a file buffered reader to read from the file
		BufferedReader inputStream = new BufferedReader(new FileReader(path));
		String line;
		try {
			line = inputStream.readLine();
			
			//Clear the document out so we are writing to a fresh document
			doc.clear();
			
			//Iterate through every line in the file
			//Using a for loop because I want to keep track of which line I'm on
			//I realize i could've done this with a while loop but what ever
			for(int i = 0; line != null; i++) {
				doc.insertLine(i, new Line(line));
				line = inputStream.readLine();
			}
			
			//Close the stream to avoid memory leaks
			inputStream.close();
		} catch (IOException e) {
			System.out.println("\tError reading file");
		}
	}
	
	/**
	 * Shows all lines in the doc
	 */
	static void showAll() {
		for(int i = 0; i < doc.length(); i++) {
			System.out.println((i + 1) + ":\t" + doc.getLine(i).getAll());
		}
	}
	
	static void showLine(int line) {
		System.out.println(line + ":\t" + doc.getLine(line - 1).getAll());
	}
	
	static void showRange(int startLine, int endLine) {
		for(int i = startLine; i <= endLine; i++) {
			System.out.println(i + ":\t" + doc.getLine(i - 1).getAll());
		}
	}
}
