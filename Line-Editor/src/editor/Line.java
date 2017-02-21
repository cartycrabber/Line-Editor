package editor;

public class Line {
	
	String data;
	
	public Line() {
		this("");
	}
	
	public Line(String text) {
		data = text;
	}
	
	public String toString() {
		return "Line: "  + data;
	}
}
