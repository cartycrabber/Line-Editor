package editor;

/*
 * Created by Will Carty
 * 2/22/17
 * for CSC 310, D. Nordstrom
 * 
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import utilities.LineList;

public class Editor {
	
	//Define static class-wide document, scanner, and copy buffers
	static Document doc;
	static Scanner in;
	static LineList lineCopyBuffer;
	static String stringCopyBuffer;
	
	//hold the path to the last opened file
	static String filePath;

	public static void main(String[] args) {
		//Create our copy buffers
		lineCopyBuffer = new LineList();
		stringCopyBuffer = "";
		
		//Create a blank document
		doc = new Document();
		
		//Create the input scanner
		in = new Scanner(System.in);
		
		//Initialize this to some default value
		filePath = "";
		
		//Show the menu for the first time
		showMenu();
		
		//Start the program loop
		while(true) {
			
			//Space things out a little so their easier to read
			System.out.println();
			
			//Wait for input...
			System.out.print("->\t");
			String command = in.nextLine();
			
			//Space things out a little so their easier to read
			System.out.println();
			
			//Go through the list of none commands looking for a match
			switch(command) {
			case "m"://Menu
				showMenu();
				break;
			case "l"://Load File
				load();
				break;
			case "sa"://Show All
				showAll();
				break;
			case "sl"://Show Line
				showLine();
				break;
			case "sr"://Show Range
				showRange();
				break;
			case "nl"://New Line
				newLine();
				break;
			case "el"://Edit Line
				editLine();
				break;
			case "dl"://Delete Line
				deleteLine();
				break;
			case "dr"://Delete Range
				deleteRange();
				break;
			case "cr"://Copy Range
				copyRange();
				break;
			case "pl"://Paste Lines
				pasteLines();
				break;
			case "w"://Write to File
				write();
				break;
			case "q"://Quit
				return;
			case "wq"://Write and Quit
				write();
				return;
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
	 * Reads the file at specified path into the document
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
			
			//We just loaded a file, so update filePath 
			filePath = path;
		} catch (IOException e) {
			System.out.println("\tError reading file");
		}
	}
	
	
	
	
	/**
	 * Performs the load command, asking for input and validating input then passing that to the load file function
	 */
	static void load() {
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
	}
	
	
	
	
	/**
	 * Shows all lines in the doc
	 */
	static void showAll() {
		for(int i = 0; i < doc.length(); i++) {
			//Add one because doc is 0 indexed but user input is 1 indexed
			System.out.println((i + 1) + ":\t" + doc.getLine(i).getAll());
		}
	}
	
	
	
	
	static void showLine() {
		//Prompt for line number
		System.out.print("Which line?");
		String line = in.nextLine();
		//Try to turn what they typed into an int. if parseInt throws an exception then they messed up
		try {
			//Subtract one because doc is 0 indexed but user input is 1 indexed
			System.out.println(line + ":\t" + doc.getLine(Integer.parseInt(line) - 1).getAll());
		} catch (Exception e) {
			//What they typed in either wasn't a number or it was out of bounds of the document
			//Either way we don't care, just go back to the main menu
			System.out.println("Invalid line number");
		}
	}
	
	
	
	
	static void showRange() {
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
			return;
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
			return;
		}
		
		//Make sure starting line is not after ending line
		if(startLine > endLine) {
			System.out.println("Start line cannot be greater than end line");
			//skip over everything else in this cycle
			return;
		}
		//Actually print out the lines now
		for(int i = startLine; i <= endLine; i++) {
			//Subtract one because doc is 0 indexed but user input is 1 indexed
			System.out.println(i + ":\t" + doc.getLine(i - 1).getAll());
		}
	}
	
	
	
	
	static void newLine() {
		int lineNumber;
		//If the doc is empty then this is the first line, else prompt for where to put this line
		if(doc.length() == 0) {
			lineNumber = 0;
		}
		else {
			System.out.println("Insert after line number:\t");
			try {
				//Read the input and parse it into an int
				lineNumber = Integer.parseInt(in.nextLine());
				//If specified number is out of bounds throw an exception
				if((lineNumber < 0) || (lineNumber > doc.length()))
					throw new Exception();
			} catch (Exception e) {
				//Either input wasn't a number or it was out of bounds
				System.out.println("Invalid line number");
				return;
			}
		}
		//At this point we have a good line number
		//Check if we're inserting at the beginning or not
		if(lineNumber == 0) {
			System.out.println("Inserting at first line");
		}
		else {
			System.out.println("Inserting after:");
			//Subtract one because doc is 0 indexed but user input is 1 indexed
			System.out.println(doc.getLine(lineNumber - 1).getAll());
		}
		
		//A loop for typing new lines. Only break out of this when user responds "n" to typing a new line
		while(true) {
			System.out.print("Type line? (y/n):\t");
			String response = in.nextLine();
			
			if(response.equals("y")) {
				//Add one because Document is 0 indexed but the editor is 1 indexed
				System.out.print((lineNumber + 1) + ":\t");
				//Insert whatever they type into the previously specified position in the doc
				doc.insertLine(lineNumber, new Line(in.nextLine()));
				//increment lineNumber so if we come through the loop again we are working on the next line
				lineNumber++;
			}
			else if(response.equals("n")) {
				//Just break out of this loop and go back to the main menu
				break;
			}
			else {
				System.out.println("Invalid response");
				//Skip to the next cycle of this loop
				continue;
			}
		}
	}
	
	
	
	
	static void editLine() {
		System.out.print("Which line?\t");
		//Read the lineNumber and verify the input just like i've done a few times already
		int lineNumber = 0;
		try {
			lineNumber = Integer.parseInt(in.nextLine());
			if((lineNumber <= 0) || (lineNumber > doc.length())) {
				throw new Exception();
			}
		} catch (Exception e) {
			System.out.println("Invalid line number");
			return;
		}
		
		//Store the line for later use (subtract 1 because of the 0 indexed vs 1 indexed problem)
		Line line = doc.getLine(lineNumber - 1);
		
		showLine(line);
		
		showLineMenu();
		
		while(true) {
			System.out.print("->\t");
			switch(in.nextLine()) {
			case "s"://Show line
				showLine(line);
				break;
			case "c"://Copy to string buffer
				copyToStringBuffer(line);
				break;
			case "t"://Cut
				cutToStringBuffer(line);
				showLine(line);
				break;
			case "p"://Paste from string buffer
				pasteFromStringBuffer(line);
				showLine(line);
				break;
			case "e"://Enter new substring
				enterNewSubstring(line);
				showLine(line);
				break;
			case "d"://Delete substring
				deleteSubstring(line);
				showLine(line);
				break;
			case "q"://quit line
				return;
			default:
				System.out.println("Unrecognized command");
			}
		}
	}
	
	
	static void showLineMenu() {
		System.out.println(""
				+ "\tShow line:  s\n"
				+ "\tCopy to string buffer:  c\n"
				+ "\tCut:  t\n"
				+ "\tPaste from string buffer:  p\n"
				+ "\tEnter new substring:  e\n"
				+ "\tDelete substring:  d\n"
				+ "\tQuit Line:  q");
	}
	
	
	static void showLine(Line line) {
		//for spacing
		System.out.println();
		
		//For loops to print out a scale as long as the line
		//This one prints out the number every five digits
		for(int i = 0; i < line.length(); i++) {
			//Print the numbers divisible by 5 (0, 5, 10, 15, etc)
			if((i % 5) == 0) {
				System.out.print(i);
				//Because numbers >= 10 take two character spaces, we need to skip one character ahead when we print them to accomodate the extra character
				if(i >= 10) {
					i++;
				}
			}
			else {
				System.out.print(" ");
			}
		}
		//Make sure to get each row on a new line
		System.out.println();
		//Print out the tick marks
		for(int i = 0; i < line.length(); i++) {
			//print a bar on the 10s and a + on the 5s. everything else gets a -
			if((i % 10) == 0) {
				System.out.print("|");
			}
			else if((i % 5) == 0) {
				System.out.print("+");
			}
			else {
				System.out.print("-");
			}
		}
		System.out.println();
		System.out.println(line.getAll());
		System.out.println();
	}
	
	
	static void copyToStringBuffer(Line line) {
		int start = 0;
		int end = 0;
		
		//Prompt for start position and validate input
		System.out.print("from position?\t");
		try {
			start = Integer.parseInt(in.nextLine());
			if((start < 0) || (start >= line.length())) {
				throw new Exception();
			}
		} catch (Exception e) {
			System.out.println("Invalid start position");
			return;
		}
		
		//Prompt for end position and validate input
		System.out.print("to position?\t");
		try {
			end = Integer.parseInt(in.nextLine());
			if((end < 0) || (end >= line.length())) {
				throw new Exception();
			}
		} catch (Exception e) {
			System.out.println("Invalid end position");
			return;
		}
		
		//Make sure start isnt after end
		if(start > end) {
			System.out.println("Start can't be after end");
			return;
		}
		
		//Now we can actually copy the substring to the buffer
		//Make a new string reference out of this so we don't have wonky things where changing one line changes a bunch of others
		stringCopyBuffer = new String(line.get(start, end));
	}
	
	
	static void cutToStringBuffer(Line line) {
		int start = 0;
		int end = 0;
		
		//Prompt for start position and validate input
		System.out.print("from position?\t");
		try {
			start = Integer.parseInt(in.nextLine());
			if((start < 0) || (start >= line.length())) {
				throw new Exception();
			}
		} catch (Exception e) {
			System.out.println("Invalid start position");
			return;
		}
		
		//Prompt for end position and validate input
		System.out.print("to position?\t");
		try {
			end = Integer.parseInt(in.nextLine());
			if((end < 0) || (end >= line.length())) {
				throw new Exception();
			}
		} catch (Exception e) {
			System.out.println("Invalid end position");
			return;
		}
		
		//Make sure start isnt after end
		if(start > end) {
			System.out.println("Start can't be after end");
			return;
		}
		
		//Copy the substring to the buffer, then delete the substring from the line
		//Make a new string reference out of this so we don't have wonky things where changing one line changes a bunch of others
		stringCopyBuffer = new String(line.get(start, end));
		line.delete(start, end);
	}
	
	
	static void pasteFromStringBuffer(Line line) {
		System.out.print("Insert At?\t");
		int insertPosition = 0;
		//Once again wait for input and validate it
		try {
			insertPosition = Integer.parseInt(in.nextLine());
			
			if((insertPosition < 0) || (insertPosition > line.length())) {
				throw new Exception();
			}
		} catch (Exception e) {
			System.out.println("Invalid position");
			return;
		}
		
		//Now that we have a good insert position, go ahead and insert the copy buffer into the line
		//Make a new string reference out of this so we don't have wonky things where changing one line changes a bunch of others
		line.insert(insertPosition, new String(stringCopyBuffer));
	}
	
	
	static void enterNewSubstring(Line line) {
		System.out.print("Insert At?\t");
		int insertPosition = 0;
		//again, wait for input and validate it
		try {
			insertPosition = Integer.parseInt(in.nextLine());
			
			if((insertPosition < 0) || (insertPosition > line.length())) {
				throw new Exception();
			}
		} catch (Exception e) {
			System.out.println("Invalid position");
			return;
		}
		
		System.out.print("text?\t");
		String text = in.nextLine();
		line.insert(insertPosition, text);
	}
	
	static void deleteSubstring(Line line) {
		int start = 0;
		int end = 0;
		
		//Prompt for start position and validate input
		System.out.print("from position?\t");
		try {
			start = Integer.parseInt(in.nextLine());
			if((start < 0) || (start >= line.length())) {
				throw new Exception();
			}
		} catch (Exception e) {
			System.out.println("Invalid start position");
			return;
		}
		
		//Prompt for end position and validate input
		System.out.print("to position?\t");
		try {
			end = Integer.parseInt(in.nextLine());
			if((end < 0) || (end >= line.length())) {
				throw new Exception();
			}
		} catch (Exception e) {
			System.out.println("Invalid end position");
			return;
		}
		
		//Make sure start isnt after end
		if(start > end) {
			System.out.println("Start can't be after end");
			return;
		}
		
		line.delete(start, end);
	}
	
	
	
	static void deleteLine() {
		System.out.print("Delete line number?\t");
		int line = 0;
		//wait for input and validate it
		try {
			line = Integer.parseInt(in.nextLine());
			
			if((line <= 0) || (line > doc.length())) {
				throw new Exception();
			}
		} catch (Exception e) {
			System.out.println("Invalid line number");
			return;
		}
		
		//Delete the line, converting from 1 indexed to 0 indexed
		doc.deleteLine(line - 1);
	}
	
	
	
	
	static void deleteRange() {
		int start = 0;
		int end = 0;
		
		//Prompt for start position and validate input
		System.out.print("from position?\t");
		try {
			start = Integer.parseInt(in.nextLine());
			if((start <= 0) || (start > doc.length())) {
				throw new Exception();
			}
		} catch (Exception e) {
			System.out.println("Invalid start position");
			return;
		}
		
		//Prompt for end position and validate input
		System.out.print("to position?\t");
		try {
			end = Integer.parseInt(in.nextLine());
			if((end <= 0) || (end > doc.length())) {
				throw new Exception();
			}
		} catch (Exception e) {
			System.out.println("Invalid end position");
			return;
		}
		
		//Make sure start isnt after end
		if(start > end) {
			System.out.println("Start can't be after end");
			return;
		}
		
		//Iterate through all the lines, removing each one
		for(int i = start; i <= end; i++) {
			//Convert from 1 indexed to 0 indexed
			doc.deleteLine(start - 1);
		}
	}
	
	
	
	
	static void copyRange() {
		int start = 0;
		int end = 0;
		
		//Prompt for start position and validate input
		System.out.print("from position?\t");
		try {
			start = Integer.parseInt(in.nextLine());
			if((start <= 0) || (start > doc.length())) {
				throw new Exception();
			}
		} catch (Exception e) {
			System.out.println("Invalid start position");
			return;
		}
		
		//Prompt for end position and validate input
		System.out.print("to position?\t");
		try {
			end = Integer.parseInt(in.nextLine());
			if((end <= 0) || (end > doc.length())) {
				throw new Exception();
			}
		} catch (Exception e) {
			System.out.println("Invalid end position");
			return;
		}
		
		//Make sure start isnt after end
		if(start > end) {
			System.out.println("Start can't be after end");
			return;
		}
		
		//Clear out the copy buffer
		lineCopyBuffer = new LineList();
		
		//Iterate through all the lines, adding each one to the copy buffer
		//Go backwards so we can keep inserting at the front of the copy buffer
		for(int i = end; i >= start; i--) {
			//Insert at front because we are inserting in reverse order
			//Subtract 1 to go from 1 indexed to 0 indexed
			//Make a new reference out of this so we don't have wonky things where changing one line changes a bunch of others
			lineCopyBuffer.insertAt(0, new Line(doc.getLine(i - 1).getAll()));
		}
	}
	
	
	
	
	static void pasteLines() {
		System.out.print("Paste after line number?\t");
		int line = 0;
		//wait for input and validate it
		try {
			line = Integer.parseInt(in.nextLine());
			
			if((line <= 0) || (line > doc.length())) {
				throw new Exception();
			}
		} catch (Exception e) {
			System.out.println("Invalid line number");
			return;
		}
		
		//Finally use our handy dandy insertMultipleLines function to easily do this
		//Because we are inserting AFTER the specified line, we don't have to add anything to convert from 1 to 0 indexed
		doc.insertMultipleLines(line, lineCopyBuffer);
	}
	
	
	
	
	static void write() {
		//If filepath is  still the default value, then we haven't opened another file and need to prompt for the file to store in
		String path = filePath;
		if(filePath == "") {
			System.out.println("File to write to?\t");
			path = in.nextLine();
		}
		
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
			
			//Iterate through every line in doc and write it to the file
			for(int i = 0; i < doc.length(); i++) {
				writer.write(doc.getLine(i).getAll());
				//Make sure to add a line break so each line is actually on a new line
				writer.write("\n");
			}
			
			//close the writer because we're done with it
			writer.close();
			
			//We just loaded a file so filePath needs to be updated with the path of the file we just loaded
			filePath = path;
		} catch (IOException e) {
			System.out.println("Could not write to file");
			//reset filePath because this path isnt working
			filePath = "";
		}
	}
}
