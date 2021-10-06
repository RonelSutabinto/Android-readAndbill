package com.generic.readandbill.database;

public class tmpVariable {
	protected String OrderType;
	
	public tmpVariable(){
		this.OrderType = "read";
	}
	
	public void setOrderType(String ordert){
		this.OrderType = ordert;
	}
	public String getOrderType(){
		return OrderType;
	}
}
