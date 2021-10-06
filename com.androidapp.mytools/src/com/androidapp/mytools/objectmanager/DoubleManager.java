package com.androidapp.mytools.objectmanager;

public class DoubleManager {
	 public static Double rRound(Double amount) {
		 String result = String.format("%.4f", amount);
			
			if (Integer.parseInt(result.substring(result.indexOf(".") + 3, result.indexOf(".") + 4)) >= 5) {
				if (amount < 0) {
					return Double.parseDouble(result.substring(0, result.indexOf(".") + 3)) + -0.01;
				} else {
					return Double.parseDouble(result.substring(0, result.indexOf(".") + 3)) + 0.01;
				}
			} else {
				return Double.parseDouble(result.substring(0, result.indexOf(".") + 3));
			}  
	    }
	 
	 
	 /*
	  public static Double rRound(Double amount) {
		String result = String.format("%.4f", amount);
		
		if (Integer.parseInt(result.substring(result.indexOf(".") + 3, result.indexOf(".") + 4)) >= 5) {
			if (amount < 0) {
				return Double.parseDouble(result.substring(0, result.indexOf(".") + 3)) + -0.01;
			} else {
				return Double.parseDouble(result.substring(0, result.indexOf(".") + 3)) + 0.01;
			}
		} else {
			return Double.parseDouble(result.substring(0, result.indexOf(".") + 3));
		}
	}
	
	LANECO ==========================================================
	
	String result = String.format("%.4f", new Object[]{amount});
	        if (Integer.parseInt(result.substring(result.indexOf(".") + 3, result.indexOf(".") + 4)) < 5) {
	            return Double.valueOf(Double.parseDouble(result.substring(0, result.indexOf(".") + 3)));
	        }
	        if (amount.doubleValue() < 0.0d) {
	            return Double.valueOf(Double.parseDouble(result.substring(0, result.indexOf(".") + 3)) -0.01d);
	        }
	        return Double.valueOf(Double.parseDouble(result.substring(0, result.indexOf(".") + 3)) + 0.01d);
	  */
}
