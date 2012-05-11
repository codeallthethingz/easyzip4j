package com.easyzip4j;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

public class EasyZipTest {

	private static final String FOLDER = "./src/test/zipthis";
	private static final String ZIP = "./src/test/zipthis.zip";
	private static final File ZIP_FILE = new File(ZIP);

	@Before
	public void delete() throws IOException {
		try {
			FileUtils.forceDelete(ZIP_FILE);
		} catch (Exception e) {
		}
	}

	@Test
	public void testCreation() throws Exception {
		EasyZip.zip(FOLDER, ZIP);
		assertTrue(ZIP_FILE.exists());
		ZipInputStream in = new ZipInputStream(new FileInputStream(ZIP));
		assertEquals("zipthis/textfile.txt", next(in));
		assertEquals("zipthis/subfolder/subfolderempty/", next(in));
		assertEquals("zipthis/subfolder/subfolderfull/file2.txt", next(in));
		assertEquals("zipthis/subfolder/subfolderfull/file3.txt", next(in));
		assertEquals("zipthis/subfolder/subfolderfull/something.txt", next(in));
		assertEquals("zipthis/subfolderempty/", next(in));
	}

	private String next(ZipInputStream in) throws IOException {
		return in.getNextEntry().toString();
	}

	@Test
	public void testTestCreationNotTopLevel() throws IOException {
		EasyZip.zip(FOLDER, ZIP,
				new EasyZipParameters().setZipFolderContentsNotFolder(true));
		assertTrue(ZIP_FILE.exists());
		ZipInputStream in = new ZipInputStream(new FileInputStream(ZIP));
		assertEquals("textfile.txt", next(in));
		assertEquals("subfolder/subfolderempty/", next(in));
		assertEquals("subfolder/subfolderfull/file2.txt", next(in));
		assertEquals("subfolder/subfolderfull/file3.txt", next(in));
		assertEquals("subfolder/subfolderfull/something.txt", next(in));
		assertEquals("subfolderempty/", next(in));
	}
}
