package editor;

public class Line {
	
	String data;
	
	public Line() {
		this("");
	}
	
	public Line(String text) {
		data = text;
	}
	
	/**
	 * Inserts a string at the specified position. Anything already at the specified position will be pushed to the right
	 * @param position where to insert the string
	 * @param text what to insert
	 */
	public void insert(int position, String text) {
		//Check and make sure position is in bounds
		if((position < 0) || (position > data.length())) {
			throw new IndexOutOfBoundsException();
		}
		
		//The part of the string before the position we are inserting
		String beforeInsert = "";
		//The part of the string after the position we are inserting
		String afterInsert = "";
		
		//If we are inserting at the beginning of the string, there is no before part, so skip this
		if(position > 0) {
			beforeInsert = data.substring(0, position);
		}
		
		//If we are inserting at the end of the string, there is no after part, so skip this
		if(position < data.length()) {
			afterInsert = data.substring(position);
		}
		
		//Combine the before and after parts with the text to insert in the middle
		data = beforeInsert + text + afterInsert;
	}
	
	/**
	 * Removes a subset of characters from the line. start and end positions are inclusive
	 * @param startPosition first character to remove
	 * @param endPosition last character to remove
	 */
	public void delete(int startPosition, int endPosition) {
		//Make sure we are in bounds
		if((startPosition < 0) || (startPosition >= data.length()) || (endPosition < 0) || (endPosition >= data.length())) {
			throw new IndexOutOfBoundsException();
		}
		//Make sure start is before end
		if(startPosition > endPosition) {
			throw new RuntimeException("Start position must be before end position");
		}
		
		//Subtring out the parts we want to keep and combine them
		data = data.substring(0, startPosition) + data.substring(endPosition + 1);
	}
	
	/**
	 * Returns a substring of the line, does not remove them
	 * @param startPosition first character to get
	 * @param endPosition last character to get
	 * @return
	 */
	public String get(int startPosition, int endPosition) {
		//Make sure we are in bounds
		if((startPosition < 0) || (startPosition >= data.length()) || (endPosition < 0) || (endPosition >= data.length())) {
			throw new IndexOutOfBoundsException();
		}
		//Make sure start is before end
		if(startPosition > endPosition) {
			throw new RuntimeException("Start position must be before end position");
		}
		
		//Substring the part that is being requested
		return data.substring(startPosition, endPosition + 1);
	}
	
	public int size() {
		return data.length();
	}
	
	public String toString() {
		return "Line: "  + data;
	}
}
