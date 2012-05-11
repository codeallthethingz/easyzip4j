package com.easyzip4j;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;


public class FilesThenDirsIteratorTest {

	@Test
	public void testAllDirs() throws Exception {
		FilesThenDirsIterator test = new FilesThenDirsIterator(new File("./src/test/zipthis"));
		int count = 0; 
		while (test.hasNext()) {
			test.next();
			count++;
			
		}
		assertEquals(3, count);
		test = new FilesThenDirsIterator(new File("./src/test/zipthis"));
		assertEquals("textfile.txt", test.next().getName());
		assertEquals("subfolder", test.next().getName());
		assertEquals("subfolderempty", test.next().getName());
	}
}
