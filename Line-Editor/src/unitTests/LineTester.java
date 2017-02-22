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
		line.insert(10, "great ");
		assertEquals("Line: This is a great test line", line.toString());
		line.insert(0, "Wow, ");
		assertEquals("Line: Wow, This is a great test line", line.toString());
		line.insert(30, "! Good Job");
		assertEquals("Line: Wow, This is a great test line! Good Job", line.toString());
		line.insert(40, "!");
		assertEquals("Line: Wow, This is a great test line! Good Job!", line.toString());
		line.insert(0, "");
		assertEquals("Line: Wow, This is a great test line! Good Job!", line.toString());
		line.insert(41, "");
		assertEquals("Line: Wow, This is a great test line! Good Job!", line.toString());
		line.insert(20, "");
		assertEquals("Line: Wow, This is a great test line! Good Job!", line.toString());
	}

	@Test
	public void testDelete() {
		line.delete(0, 3);
		assertEquals("Line:  is a test line", line.toString());
		line.delete(0, 0);
		assertEquals("Line: is a test line", line.toString());
		line.delete(13, 13);
		assertEquals("Line: is a test lin", line.toString());
		line.delete(8, 8);
		assertEquals("Line: is a tes lin", line.toString());
		line.delete(0, 11);
		assertEquals("Line: ", line.toString());
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
	public void testLength() {
		assertEquals(19, line.length());
		assertEquals(0, new Line().length());
	}

	@Test
	public void testToString() {
		assertEquals("Line: This is a test line", line.toString());
		assertEquals("Line: ", new Line().toString());
	}

}
