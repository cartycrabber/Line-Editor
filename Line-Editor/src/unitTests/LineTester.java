package unitTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import editor.Line;

public class LineTester {
	
	Line line;

	@Before
	public void setUp() throws Exception {
		line = new Line("This is a test line");
	}

	@Test
	public void testInsert() {
		fail("Not yet implemented");
	}

	@Test
	public void testDelete() {
		line.delete(0, 3);
		assertEquals("Line:  is a test line", line.toString());
		line.delete(0, 0);
		assertEquals("Line: is a test line", line.toString());
	}

	@Test
	public void testGet() {
		assertEquals("test", line.get(10, 13));
		assertEquals("This", line.get(0, 3));
		assertEquals("line", line.get(15, 18));
		assertEquals("test line", line.get(10, 18));
		assertEquals("This is a test line", line.get(0, 18));
		assertEquals("T", line.get(0, 0));
		assertEquals("e", line.get(18, 18));
	}
	
	@Test
	public void testSize() {
		assertEquals(19, line.size());
		assertEquals(0, new Line().size());
	}

	@Test
	public void testToString() {
		assertEquals("Line: This is a test line", line.toString());
		assertEquals("Line: ", new Line().toString());
	}

}
