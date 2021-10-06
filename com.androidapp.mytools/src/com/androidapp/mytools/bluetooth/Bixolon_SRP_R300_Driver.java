package com.androidapp.mytools.bluetooth;

public class Bixolon_SRP_R300_Driver {
	public static final String CHAR48;
    public static final String CHAR57;
    public static final String DISABLE_EMPHASIZED;
    public static final String ENABLE_EMPHASIZED;

    static {
        CHAR57 = String.valueOf("\u001bM1");
        CHAR48 = String.valueOf("\u001bM0");
        ENABLE_EMPHASIZED = String.valueOf("\u001bE1");
        DISABLE_EMPHASIZED = String.valueOf("\u001bE0");
    }
}
