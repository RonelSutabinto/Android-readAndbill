package com.androidapp.mytools.bluetooth;

public class PrinterControls {
	 public static MyPrinter btPrinter;

	    public static String emphasized(boolean toggleEmphasized) {
	        if (toggleEmphasized) {
	            if (btPrinter.getDeviceName().equals("EXTECH PRINTER") || btPrinter.getDeviceName().equals("APEX3")) {
	                return ExtechPrinterDriver.ENABLE_EMPHASIZED;
	            }
	            return Bixolon_SRP_R300_Driver.ENABLE_EMPHASIZED;
	        } else if (btPrinter.getDeviceName().equals("EXTECH PRINTER") || btPrinter.getDeviceName().equals("APEX3")) {
	            return ExtechPrinterDriver.DISABLE_EMPHASIZED;
	        } else {
	            return Bixolon_SRP_R300_Driver.DISABLE_EMPHASIZED;
	        }
	    }

	    public static String char48() {
	        if (btPrinter.getDeviceName().equals("EXTECH PRINTER") || btPrinter.getDeviceName().equals("APEX3")) {
	            return ExtechPrinterDriver.CHAR48;
	        }
	        return Bixolon_SRP_R300_Driver.CHAR48;
	    }

	    public static String doubleHeight(boolean enable) {
	        if (enable) {
	            if (btPrinter.getDeviceName().equals("EXTECH PRINTER") || btPrinter.getDeviceName().equals("APEX3")) {
	                return String.valueOf('\u001c');
	            }
	            return String.valueOf("\u001b!9");
	        } else if (btPrinter.getDeviceName().equals("EXTECH PRINTER") || btPrinter.getDeviceName().equals("APEX3")) {
	            return String.valueOf('\u001d');
	        } else {
	            return String.valueOf("\u001b@");
	        }
	    }
}
