package com.sh.util;

import java.io.File;
import java.io.FileFilter;

public class DirFilter implements FileFilter {

	@Override
	public boolean accept(File f) {
	
		return f.isDirectory();
	}

}
