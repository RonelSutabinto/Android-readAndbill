package com.androidapp.mytools.bluetooth;

public class ExtechPrinterDriver {
	 public static final String CHAR48;
	    public static final String CHAR57;
	    public static final String CHAR72;
	    public static final String DISABLE_DOUBLE_SIZE;
	    public static final String DISABLE_EMPHASIZED;
	    public static final String ENABLE_DOUBLE_SIZE;
	    public static final String ENABLE_EMPHASIZED;

	    static {
	        CHAR72 = String.valueOf("\u001bk5");
	        CHAR57 = String.valueOf("\u001bk3");
	        CHAR48 = String.valueOf("\u001bk2");
	        ENABLE_EMPHASIZED = String.valueOf("\u001bU1");
	        DISABLE_EMPHASIZED = String.valueOf("\u001bU0");
	        ENABLE_DOUBLE_SIZE = String.valueOf('\u001c');
	        DISABLE_DOUBLE_SIZE = String.valueOf('\u001d');
	    }
}
