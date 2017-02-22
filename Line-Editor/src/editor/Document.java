package editor;

import utilities.LineList;

public class Document {
	
	LineList lines;

	public Document() {
		lines = new LineList();
	}
	
	/**
	 * Inserts a Line at the specified line number. Anything already at the specified line number will be pushed down
	 * @param lineNumber
	 * @param lineToInsert
	 */
	public void insertLine(int lineNumber, Line lineToInsert) {
		lines.insertAt(lineNumber, lineToInsert);
	}
	
	
	/**
	 * Inserts a list of lines, starting at lineNumber
	 * @param startLineNumber
	 * @param linesToInsert
	 */
	public void insertMultipleLines(int startLineNumber, LineList linesToInsert) {
		//Iterate through all the lines and call our insertLine function to add them
		//Use a regular for loop so we can keep track of which line we are inserting at
		for(int i = 0; i < linesToInsert.size(); i++) {
			//line position to insert at is i places after startLineNumber
			//This way we start at the startLineNumber, increasing by one line each time
			insertLine(startLineNumber + i, linesToInsert.get(i));
		}
	}
	
	/**
	 * Removes the line at the specified line number
	 * @param lineNumber
	 */
	public void deleteLine(int lineNumber) {
		lines.removeAt(lineNumber);
	}
	
	/**
	 * Removes all lines in the document, leaving a blank document
	 */
	public void clear() {
		lines = new LineList();
	}
	
	/**
	 * Returns the Line at the specified line number
	 * @param lineNumber
	 * @return
	 */
	public Line getLine(int lineNumber) {
		return lines.get(lineNumber);
	}
	
	/**
	 * Returns a list of the lines between the specified positions (inclusive)
	 * @param startLineNumber
	 * @param endLineNumber
	 * @return
	 */
	public LineList getMultipleLines(int startLineNumber, int endLineNumber) {
		//Create the list we will return
		LineList result = new LineList();
		
		//Iterate through the lines from the start position to the end position, adding each to the list
		for(int currentLine = startLineNumber; currentLine <= endLineNumber; currentLine++) {
			//Insert at the end of the list (using the size function) so that the lines are in order
			result.insertAt(result.size(), lines.get(currentLine));
		}
		return result;
	}
	
	public int length() {
		return lines.size();
	}
	
	public String toString() {
		String str = "Document:\n";
		for(int i = 0; i < length(); i++) {
			str += lines.get(i).toString();
			//If this isn't the last string, add a new line
			if(i < (length() - 1)) {
				str += '\n';
			}
		}
		return str;
	}
}
