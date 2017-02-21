package unitTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import editor.Line;
import utilities.LineList;

public class LineListTester {

	LineList list;
	
	@Before
	public void setUp() throws Exception {
		list = new LineList();
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void testGetOutOfBounds() {
		assertEquals("", list.get(0));
		assertEquals("", list.get(5));
		assertEquals("", list.get(-1));
		testInsertAt();
		assertEquals("", list.get(-1));
		assertEquals("", list.get(100));
	}

	@Test
	public void testGet() {
		testInsertAt();
		assertEquals("Line: ", list.get(0).toString());
		assertEquals("Line: 1", list.get(1).toString());
		assertEquals("Line: 4", list.get(4).toString());
	}

	@Test
	public void testSet() {
		testInsertAt();
		list.set(0, new Line("testSet"));
		assertEquals("Line: testSet, Line: 1, Line: 2, Line: 3, Line: 4", list.toString());
		list.set(4, new Line("testSet4"));
		assertEquals("Line: testSet, Line: 1, Line: 2, Line: 3, Line: testSet4", list.toString());
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void testSetOutOfBounds() {
		list.set(0, new Line("out of bounds test"));
		list.set(-1, new Line("out of bounds test"));
		list.set(100, new Line("out of bounds test"));
		testInsertAt();
		list.set(0, new Line("out of bounds test"));
		list.set(-1, new Line("out of bounds test"));
		list.set(100, new Line("out of bounds test"));
	}

	@Test
	public void testInsertAt() {
		list.insertAt(0, new Line());
		assertEquals("Line: ", list.toString());
		
		for(int i = 1; i < 5; i++) {
			list.insertAt(i, new Line("" + i));
		}
		assertEquals("Line: , Line: 1, Line: 2, Line: 3, Line: 4", list.toString());
	}

	@Test
	public void testRemoveAt() {
		testInsertAt();
		list.removeAt(1);
		assertEquals("Line: , Line: 2, Line: 3, Line: 4", list.toString());
		list.removeAt(0);
		assertEquals("Line: 2, Line: 3, Line: 4", list.toString());
		list.removeAt(2);
		assertEquals("Line: 2, Line: 3", list.toString());
		list.removeAt(1);
		list.removeAt(0);
		assertEquals("", list.toString());
	}

	@Test
	public void testToString() {
		assertEquals("", list.toString());
		testInsertAt();
		assertEquals("Line: , Line: 1, Line: 2, Line: 3, Line: 4", list.toString());
	}

	@Test
	public void testFillAndEmptyList() {
		testRemoveAt();
		testRemoveAt();
		testRemoveAt();
	}
}
