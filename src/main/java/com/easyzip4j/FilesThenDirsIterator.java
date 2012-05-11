package com.easyzip4j;

import java.io.File;
import java.io.FileFilter;
import java.util.Iterator;

public class FilesThenDirsIterator implements Iterator<File> {

		private File[] files;
		private File[] dirs;
		private int total;
		private int index = 0;

		public FilesThenDirsIterator(File srcDir) {
			files = srcDir.listFiles(new FileFilter() {
				@Override
				public boolean accept(File pPathname) {
					return pPathname.isFile();
				}
			});
			if (files == null){
				files = new File[0];
			}
			dirs = srcDir.listFiles(new FileFilter() {
				@Override
				public boolean accept(File pPathname) {
					return pPathname.isDirectory();
				}
			});
			if (dirs == null){
				dirs = new File[0];
			}
			total = dirs.length + files.length;
		}

		@Override
		public boolean hasNext() {
			return index < total ;
		}

		@Override
		public File next() {
			File result = index <  files.length ? files[index] : dirs[index-files.length];
			index++;
			return result;
		}

		@Override
		public void remove() {
throw new IllegalStateException("not implemented");
		}

	}