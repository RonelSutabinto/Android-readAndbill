package com.generic.readandbill.database;

public class Consumer {
	  protected String accountNumber;
	    protected String address;
	    protected long id;
	    protected double initialReading;
	    protected String initialReadingDate;
	    protected String meterSerial;
	    protected double multiplier;
	    protected String name;
	    protected String rateCode;
	    protected double transformerRental;

	    public Consumer() {
	        this.id = 0;
	        this.accountNumber = "";
	        this.name = "";
	        this.address = "";
	        this.rateCode = "";
	        this.meterSerial = "";
	        this.initialReadingDate = "";
	        this.initialReading = 0.0d;
	        this.multiplier = 0.0d;
	        this.transformerRental = 0.0d;
	    }

	    public long getId() {
	        return id;
	    }

	    public void setId(long id) {
	        this.id = id;
	    }

	    public String getAccountNumber() {
	        return accountNumber;
	    }

	    public void setAccountNumber(String accountNumber) {
	        this.accountNumber = accountNumber;
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public String getAddress() {
	        return address;
	    }

	    public void setAddress(String address) {
	        this.address = address;
	    }

	    public String getRateCode() {
	        return rateCode;
	    }

	    public void setRateCode(String rateRef) {
	        this.rateCode = rateRef;
	    }

	    public String getMeterSerial() {
	        return meterSerial;
	    }

	    public void setMeterSerial(String meterSerial) {
	        this.meterSerial = meterSerial;
	    }

	    public double getInitialReading() {
	        return initialReading;
	    }

	    public void setInitialReading(double initialReading) {
	        this.initialReading = initialReading;
	    }

	    public String getInitialReadingDate() {
	        return initialReadingDate;
	    }

	    public void setInitialReadingDate(String initialReadingDate) {
	        this.initialReadingDate = initialReadingDate;
	    }

	    public double getMultiplier() {
	        return multiplier;
	    }

	    public void setMultiplier(double multiplier) {
	        if (multiplier == 0.0d) {
	            this.multiplier = 1.0d;
	        } else {
	            this.multiplier = multiplier;
	        }
	    }

	    public double getTransformerRental() {
	        return transformerRental;
	    }

	    public void setTransformerRental(double transformerRental) {
	        this.transformerRental = transformerRental;
	    }

	    public String toString() {
	        return String.valueOf(id);
	    }
}
