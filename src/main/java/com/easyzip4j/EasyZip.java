package com.easyzip4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

public class EasyZip {
	private static final Logger log = Logger.getLogger(EasyZip.class);

	public static void zip(String pSrcFolder, String pZipFile) throws Exception {
		EasyZip.zip(pSrcFolder, pZipFile, new EasyZipParameters());
	}

	public static void zip(String pSrcFolder, String pZipFile,
			EasyZipParameters pEasyZipParameters) throws IOException {
		String basePath = new File(pSrcFolder).getCanonicalPath();
		if (pEasyZipParameters == null) {
			pEasyZipParameters = new EasyZipParameters();
		}
		ZipOutputStream zipOut = null;
		try {
			zipOut = new ZipOutputStream(new FileOutputStream(
					new File(pZipFile)));
			if (pEasyZipParameters.isZipFolderContentsNotFolder()) {
				FilesThenDirsIterator ftd = new FilesThenDirsIterator(new File(
						pSrcFolder));
				while (ftd.hasNext()) {
					File file = (File) ftd.next();
					if (!file.isDirectory()) {
						addFileToZip("", file.getPath(), zipOut, false);
					}
					if (file.isDirectory()) {
						addFolderToZip("", file.getPath(), zipOut);
					}
				}
			} else {
				addFolderToZip("", basePath, zipOut);
			}
		} finally {
			IOUtils.closeQuietly(zipOut);
		}
	}

	private static void addFileToZip(String path, String srcFile,
			ZipOutputStream zip, boolean pIsEmptyFolder) throws IOException {
		String prefix = path.equals("") ? "" : path + "/";
		File folder = new File(srcFile);

		String name = prefix + folder.getName();
		if (pIsEmptyFolder == true) {
			if (log.isDebugEnabled()) {
				log.debug("adding empty dir: " + name);
			}
			zip.putNextEntry(new ZipEntry(name + "/"));
		} else {
			if (!folder.isDirectory()) {
				if (log.isDebugEnabled()) {
					log.debug("adding file: " + name);
				}
				FileInputStream in = null;
				try {
					zip.putNextEntry(new ZipEntry(name));
					in = new FileInputStream(srcFile);
					IOUtils.copy(in, zip);
				} finally {
					IOUtils.closeQuietly(in);
				}
			} else {
				addFolderToZip(path, srcFile, zip);
			}
		}
	}

	private static void addFolderToZip(String path, String srcFolder,
			ZipOutputStream zip) throws IOException {
		File folder = new File(srcFolder);

		if (folder.list().length == 0) {
			addFileToZip(path, srcFolder, zip, true);
		} else {
			FilesThenDirsIterator ftd = new FilesThenDirsIterator(folder);
			while (ftd.hasNext()) {
				String fileName = ftd.next().getName();
				if (path.equals("")) {
					addFileToZip(folder.getName(), srcFolder + "/" + fileName,
							zip, false);
				} else {
					addFileToZip(path + "/" + folder.getName(), srcFolder + "/"
							+ fileName, zip, false);
				}
			}
		}
	}
}
