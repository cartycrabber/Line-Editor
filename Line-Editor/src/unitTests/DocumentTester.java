package unitTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import editor.Document;
import editor.Line;
import utilities.LineList;

public class DocumentTester {

	Document doc;
	
	@Before
	public void setUp() throws Exception {
		doc = new Document();
	}

	@Test
	public void testInsertLine() {
		doc.insertLine(0, new Line("first line"));
		assertEquals("Document:\nLine: first line", doc.toString());
		doc.insertLine(1, new Line("second line"));
		assertEquals("Document:\nLine: first line\nLine: second line", doc.toString());
		doc.insertLine(2, new Line("woho three lines!"));
		assertEquals("Document:\nLine: first line\nLine: second line\nLine: woho three lines!", doc.toString());
		doc.insertLine(3, new Line("wow a fourth line"));
		assertEquals("Document:\nLine: first line\nLine: second line\nLine: woho three lines!\nLine: wow a fourth line", doc.toString());
		doc.insertLine(0, new Line("here we go"));
		assertEquals("Document:\nLine: here we go\nLine: first line\nLine: second line\nLine: woho three lines!\nLine: wow a fourth line", doc.toString());
		doc.insertLine(2, new Line("last insert test, finally"));
		assertEquals("Document:\nLine: here we go\nLine: first line\nLine: last insert test, finally\nLine: second line\nLine: woho three lines!\nLine: wow a fourth line", doc.toString());
	}

	@Test
	public void testInsertMultipleLines() {
		LineList list = new LineList();
		
		for(int i = 0; i < 4; i++) {
			list.insertAt(i, new Line("line " + i));
		}
		doc.insertMultipleLines(0, list);
		assertEquals("Document:\nLine: line 0\nLine: line 1\nLine: line 2\nLine: line 3", doc.toString());
		
		list = new LineList();
		for(int i = 4; i < 6; i++) {
			list.insertAt(i - 4, new Line("line " + i));
		}
		doc.insertMultipleLines(0, list);
		assertEquals("Document:\nLine: line 4\nLine: line 5\nLine: line 0\nLine: line 1\nLine: line 2\nLine: line 3", doc.toString());
		
		list = new LineList();
		for(int i = 6; i < 8; i++) {
			list.insertAt(i - 6, new Line("line " + i));
		}
		doc.insertMultipleLines(doc.length(), list);
		assertEquals("Document:\nLine: line 4\nLine: line 5\nLine: line 0\nLine: line 1\nLine: line 2\nLine: line 3\nLine: line 6\nLine: line 7", doc.toString());
		
		list = new LineList();
		list.insertAt(0, new Line("line 8"));
		doc.insertMultipleLines(doc.length(), list);
		assertEquals("Document:\nLine: line 4\nLine: line 5\nLine: line 0\nLine: line 1\nLine: line 2\nLine: line 3\nLine: line 6\nLine: line 7\nLine: line 8", doc.toString());
		
		list = new LineList();
		doc.insertMultipleLines(doc.length(), list);
		assertEquals("Document:\nLine: line 4\nLine: line 5\nLine: line 0\nLine: line 1\nLine: line 2\nLine: line 3\nLine: line 6\nLine: line 7\nLine: line 8", doc.toString());
		
		list = new LineList();
		list.insertAt(0, new Line("line 9"));
		doc.insertMultipleLines(0, list);
		assertEquals("Document:\nLine: line 9\nLine: line 4\nLine: line 5\nLine: line 0\nLine: line 1\nLine: line 2\nLine: line 3\nLine: line 6\nLine: line 7\nLine: line 8", doc.toString());
		
		list = new LineList();
		for(int i = 10; i < 12; i++) {
			list.insertAt(i - 10, new Line("line " + i));
		}
		doc.insertMultipleLines(4, list);
		assertEquals("Document:\nLine: line 9\nLine: line 4\nLine: line 5\nLine: line 0\nLine: line 10\nLine: line 11\nLine: line 1\nLine: line 2\nLine: line 3\nLine: line 6\nLine: line 7\nLine: line 8", doc.toString());
	}

	@Test
	public void testDeleteLine() {
		testInsertMultipleLines();
		doc.deleteLine(0);
		assertEquals("Document:\nLine: line 4\nLine: line 5\nLine: line 0\nLine: line 10\nLine: line 11\nLine: line 1\nLine: line 2\nLine: line 3\nLine: line 6\nLine: line 7\nLine: line 8", doc.toString());
		
		doc.deleteLine(doc.length() - 1);
		assertEquals("Document:\nLine: line 4\nLine: line 5\nLine: line 0\nLine: line 10\nLine: line 11\nLine: line 1\nLine: line 2\nLine: line 3\nLine: line 6\nLine: line 7", doc.toString());
		
		doc.deleteLine(3);
		assertEquals("Document:\nLine: line 4\nLine: line 5\nLine: line 0\nLine: line 11\nLine: line 1\nLine: line 2\nLine: line 3\nLine: line 6\nLine: line 7", doc.toString());
		
		while(doc.length() > 0) {
			doc.deleteLine(0);
		}
		assertEquals("Document:\n", doc.toString());
	}

	@Test
	public void testGetLine() {
		testInsertMultipleLines();
		assertEquals("Line: line 9", doc.getLine(0).toString());
		assertEquals("Line: line 5", doc.getLine(2).toString());
		assertEquals("Line: line 8", doc.getLine(doc.length() - 1).toString());
	}

	@Test
	public void testGetMultipleLines() {
		testInsertMultipleLines();
		LineList list = doc.getMultipleLines(0, 2);
		assertEquals("Line: line 9", list.get(0).toString());
		assertEquals("Line: line 4", list.get(1).toString());
		assertEquals("Line: line 5", list.get(2).toString());
		
		list = doc.getMultipleLines(10, 11);
		assertEquals("Line: line 7", list.get(0).toString());
		assertEquals("Line: line 8", list.get(1).toString());
		
		list = doc.getMultipleLines(4, 6);
		assertEquals("Line: line 10", list.get(0).toString());
		assertEquals("Line: line 11", list.get(1).toString());
		assertEquals("Line: line 1", list.get(2).toString());
	}

	@Test
	public void testLength() {
		assertEquals(0, doc.length());
		testInsertMultipleLines();
		assertEquals(12, doc.length());
	}

	@Test
	public void testFillingAndEmptying() {
		testDeleteLine();
		assertEquals("Document:\n", doc.toString());
		testDeleteLine();
		assertEquals("Document:\n", doc.toString());
		testDeleteLine();
		assertEquals("Document:\n", doc.toString());
		testInsertLine();
	}
}
