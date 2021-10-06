package com.generic.readandbill.database;

public class Rates {
	 protected double dcDemand;
	    protected double dcDistribution;
	    protected double genSys;
	    protected double hostComm;
	    protected long id;
	    protected double lifeLineSubsidy;
	    protected double mcRetailCust;
	    protected double mcSys;
	    protected double parr;
	    protected double prevYearAdjPowerCost;
	    protected String rateCode;
	    protected double reinvestmentFundSustCapex;
	    protected double scKiloWattHourLimit;
	    protected double scRetailCust;
	    protected double scSupplySys;
	    protected boolean scSwitch;
	    protected double seniorCitizenDiscount;
	    protected double seniorCitizenSubsidy;
	    protected double systemLoss;
	    protected double tcDemand;
	    protected double tcSystem;
	    protected double ucec;
	    protected double ucme;
	    protected double ucsd;

	    public Rates() {
	        this.id = -1;
	        this.rateCode = "";
	        this.genSys = 0.0d;
	        this.hostComm = 0.0d;
	        this.systemLoss = 0.0d;
	        this.parr = 0.0d;
	        this.tcDemand = 0.0d;
	        this.tcSystem = 0.0d;
	        this.dcDemand = 0.0d;
	        this.dcDistribution = 0.0d;
	        this.scRetailCust = 0.0d;
	        this.scSupplySys = 0.0d;
	        this.mcRetailCust = 0.0d;
	        this.mcSys = 0.0d;
	        this.lifeLineSubsidy = 0.0d;
	        this.scSwitch = false;
	        this.seniorCitizenSubsidy = 0.0d;
	        this.scKiloWattHourLimit = 0.0d;
	        this.seniorCitizenDiscount = 0.0d;
	        this.reinvestmentFundSustCapex = 0.0d;
	        this.prevYearAdjPowerCost = 0.0d;
	        this.ucec = 0.0d;
	        this.ucme = 0.0d;
	        this.ucsd = 0.0d;
	    }

	    public double getUcsd(){
	    	return ucsd;
	    }
	    public void setUcsd(double sd){
	    	this.ucsd = sd;
	    }
	    public String getRateCode() {
	        return rateCode;
	    }

	    public long getId() {
	        return id;
	    }

	    public void setId(long id) {
	        this.id = id;
	    }

	    public void setRateCode(String rateCode) {
	        this.rateCode = rateCode;
	    }

	    public double getGenSys() {
	        return genSys;
	    }

	    public void setGenSys(double genSys) {
	        this.genSys = genSys;
	    }

	    public double getHostComm() {
	        return hostComm;
	    }

	    public void setHostComm(double hostComm) {
	        this.hostComm = hostComm;
	    }

	    public double getTcDemand() {
	        return tcDemand;
	    }

	    public void setTcDemand(double tcDemand) {
	        this.tcDemand = tcDemand;
	    }

	    public double getTcSystem() {
	        return tcSystem;
	    }

	    public void setTcSystem(double tcSystem) {
	        this.tcSystem = tcSystem;
	    }

	    public double getSystemLoss() {
	        return systemLoss;
	    }

	    public void setSystemLoss(double systemLoss) {
	        this.systemLoss = systemLoss;
	    }

	    public double getDcDemand() {
	        return dcDemand;
	    }

	    public void setDcDemand(double dcDemand) {
	        this.dcDemand = dcDemand;
	    }

	    public double getDcDistribution() {
	        return dcDistribution;
	    }

	    public double getScRetailCust() {
	        return scRetailCust;
	    }

	    public void setScRetailCust(double scRetailCust) {
	        this.scRetailCust = scRetailCust;
	    }

	    public double getScSupplySys() {
	        return scSupplySys;
	    }

	    public void setScSupplySys(double scSupplySys) {
	        this.scSupplySys = scSupplySys;
	    }

	    public double getMcRetailCust() {
	        return mcRetailCust;
	    }

	    public void setMcRetailCust(double mcRetailCust) {
	        this.mcRetailCust = mcRetailCust;
	    }

	    public double getMcSys() {
	        return mcSys;
	    }

	    public void setMcSys(double mcSys) {
	        this.mcSys = mcSys;
	    }

	    public double getUcme() {
	        return ucme;
	    }

	    public void setUcme(double ucme) {
	        this.ucme = ucme;
	    }

	    public double getUcec() {
	        return ucec;
	    }

	    public void setUcec(double ucec) {
	        this.ucec = ucec;
	    }

	    public double getParr() {
	        return parr;
	    }

	    public void setParr(double parr) {
	        this.parr = parr;
	    }

	    public double getLifeLineSubsidy() {
	        return lifeLineSubsidy;
	    }

	    public void setLifeLineSubsidy(double lifeLineSubsidy) {
	        this.lifeLineSubsidy = lifeLineSubsidy;
	    }

	    public Boolean getScSwitch() {
	        return Boolean.valueOf(scSwitch);
	    }

	    public void setScSwitch(Boolean scSwitch) {
	        this.scSwitch = scSwitch.booleanValue();
	    }

	    public double getScKiloWattHourLimit() {
	        return scKiloWattHourLimit;
	    }

	    public void setScKiloWattHourLimit(double scKiloWattHourLimit) {
	        this.scKiloWattHourLimit = scKiloWattHourLimit;
	    }

	    public double getSeniorCitizenSubsidy() {
	        return seniorCitizenSubsidy;
	    }

	    public void setSeniorCitizenSubsidy(double seniorCitizenSubsidy) {
	        this.seniorCitizenSubsidy = seniorCitizenSubsidy;
	    }

	    public double getSeniorCitizenDiscount() {
	        return seniorCitizenDiscount;
	    }

	    public void setSeniorCitizenDiscount(double seniorCitizenDiscount) {
	        this.seniorCitizenDiscount = seniorCitizenDiscount;
	    }

	    public double getPrevYearAdjPowerCost() {
	        return prevYearAdjPowerCost;
	    }

	    public void setDcDistribution(double dcDistribution) {
	        this.dcDistribution = dcDistribution;
	    }

	    public void setPrevYearAdjPowerCost(double prevYearAdjPowerCost) {
	        this.prevYearAdjPowerCost = prevYearAdjPowerCost;
	    }

	    public double getReinvestmentFundSustCapex() {
	        return reinvestmentFundSustCapex;
	    }

	    public void setReinvestmentFundSustCapex(double reinvestmentFundSustCapex) {
	        this.reinvestmentFundSustCapex = reinvestmentFundSustCapex;
	    }
}
