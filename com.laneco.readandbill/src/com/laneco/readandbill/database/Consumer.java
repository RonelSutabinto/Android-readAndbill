package com.laneco.readandbill.database;
import com.androidapp.mytools.objectmanager.DoubleManager;
public class Consumer extends com.generic.readandbill.database.Consumer{
	 private boolean SCSwitch;
	    protected double arMats;
	    protected double arrears;
	    protected double aveKwh;
	    protected double changeMeterKilowatthour;
	    public final double commercialServiceFee;
	    private String connCode;
	    private boolean contracted;
	    protected double coreLoss;
	    protected String dateEnergized;
	    private double demandCharge;
	    protected double demandMax;
	    protected double demandMin;
	    protected double demandMultiplier;
	    protected double differentialBillRecovery;
	    protected double disco;
	    protected double equiptment;
	    protected double help;
	    public final double highVoltageServiceFee;
	    protected double incentives;
	    public final double industrialServiceFee;
	    private double kw;
	    protected double material;
	    protected String meterBrand;
	    protected int numberOfArrears;
	    protected double othersSurcharge;
	    protected double pilfer;
	    protected String poleNumber;
	    public final double publicBuilding1ServiceFee;
	    protected double refund;
	    public final double residentialServiceFee;
	    private boolean scSwitch;
	    protected double scap;
	    private double seniorCitizenDiscount;
	    public final double streetLightServiceFee;
	    protected double transformerLostTestResult;
	    protected String transformerNumber;

	    public Consumer() {
	        this.residentialServiceFee = 50.0d;
	        this.commercialServiceFee = 100.0d;
	        this.publicBuilding1ServiceFee = 50.0d;
	        this.streetLightServiceFee = 50.0d;
	        this.industrialServiceFee = 19.8d;
	        this.highVoltageServiceFee = 0.4d;
	        this.demandMultiplier = 0.0d;
	        this.demandMax = 0.0d;
	        this.demandMin = 0.0d;
	        this.meterBrand = "";
	        this.aveKwh = 0.0d;
	        this.dateEnergized = "";
	        this.transformerNumber = "";
	        this.poleNumber = "";
	        this.coreLoss = 0.0d;
	        this.transformerLostTestResult = 0.0d;
	        this.arMats = 0.0d;
	        this.arrears = 0.0d;
	        this.changeMeterKilowatthour = 0.0d;
	        this.differentialBillRecovery = 0.0d;
	        this.scap = 0.0d;
	        this.refund = 0.0d;
	        this.help = 0.0d;
	        this.pilfer = 0.0d;
	        this.numberOfArrears = 0;
	    }

	    public String getAccountNumber() {
	        return super.getAccountNumber().replace("-", "");
	    }

	    public String getOrigAccountNumber() {
	        return accountNumber;
	    }

	    public double getDemandMultiplier(boolean isDisplay) {
	        if (getDemandMin() != getDemandMax() || getDemandMax() <= 0.0d) {
	            return demandMultiplier;
	        }
	        if (isDisplay) {
	            return demandMultiplier;
	        }
	        return 1.0d;
	    }

	    public void setDemandMultiplier(double demandMultiplier) {
	        this.demandMultiplier = demandMultiplier;
	    }

	    public String getMeterBrand() {
	        return meterBrand;
	    }

	    public void setMeterBrand(String meterBrand) {
	        this.meterBrand = meterBrand;
	    }

	    public double getAveKwh() {
	        return aveKwh;
	    }

	    public void setAveKwh(double aveKwh) {
	        this.aveKwh = aveKwh;
	    }

	    public String getDateEnergized() {
	        return dateEnergized;
	    }

	    public void setDateEnergized(String dateEnergized) {
	        this.dateEnergized = dateEnergized;
	    }

	    public String getTransformerNumber() {
	        return transformerNumber;
	    }

	    public void setTransformerNumber(String transformerNumber) {
	        this.transformerNumber = transformerNumber;
	    }

	    public String getPoleNumber() {
	        return poleNumber;
	    }

	    public void setPoleNumber(String poleNumber) {
	        this.poleNumber = poleNumber;
	    }

	    public double getTransLoss() {
	        return getTransformerLostTestResult() + getCoreLoss();
	    }

	    public double getArMats() {
	        return arMats;
	    }

	    public void setArMats(double arMats) {
	        this.arMats = arMats;
	    }

	    public double getArrears() {
	        if (arrears == 0.0d) {
	            return 0.0d;
	        }
	        double serviceFee;
	        double result = arrears + (arrears * 0.03d);
	        if (rateCode.equals("R")) {
	            serviceFee = 50.0d;
	        } else if (rateCode.equals("C")) {
	            serviceFee = 100.0d;
	        } else if (rateCode.equals("P")) {
	            serviceFee = 50.0d;
	        } else if (rateCode.equals("S")) {
	            serviceFee = 50.0d;
	        } else if (rateCode.equals("I")) {
	            serviceFee = DoubleManager.rRound(Double.valueOf(kw * 19.8d)).doubleValue();
	        } else if (rateCode.equals("H")) {
	            serviceFee = DoubleManager.rRound(Double.valueOf(demandCharge * 0.4d)).doubleValue();
	        } else {
	            serviceFee = 0.0d;
	        }
	        return result + (result + (serviceFee + (0.12d * serviceFee)));
	    }

	    public void setArrears(double arrears) {
	        this.arrears = arrears;
	    }

	    public double getChangeMeterKilowatthour() {
	        return changeMeterKilowatthour;
	    }

	    public void setChangeMeterKilowatthour(double changeMeterKilowatthour) {
	        this.changeMeterKilowatthour = changeMeterKilowatthour;
	    }

	    public int getNumberOfArrears() {
	        return numberOfArrears;
	    }

	    public void setNumberOfArrears(int numberOfArrears) {
	        this.numberOfArrears = numberOfArrears;
	    }

	    public double getDifferentialBillRecovery() {
	        return differentialBillRecovery;
	    }

	    public double getScap() {
	        return scap;
	    }

	    public void setScap(double scap) {
	        this.scap = scap;
	    }

	    public double getRefund() {
	        return refund;
	    }

	    public void setRefund(double refund) {
	        this.refund = refund;
	    }

	    public double getHelp() {
	        return help;
	    }

	    public void setHelp(double help) {
	        this.help = help;
	    }

	    public double getPilfer() {
	        return pilfer;
	    }

	    public void setPilfer(double pilfer) {
	        this.pilfer = pilfer;
	    }

	    public void setDifferentialBillRecovery(double differentialBillRecovery) {
	        this.differentialBillRecovery = differentialBillRecovery;
	    }

	    public double getOtherCharges() {
	        return (((((((getScap() + getRefund()) + getHelp()) + getPilfer()) + getDisco()) + getIncentives()) + getMaterial()) + getEquiptment()) + getOthersSurcharge();
	    }

	    public void setSCSwitch(boolean SCSwitch) {
	        this.scSwitch = SCSwitch;
	    }

	    public boolean isScSwitch() {
	        return scSwitch;
	    }

	    public void setScSwitch(boolean scSwitch) {
	        this.scSwitch = scSwitch;
	    }

	    public double getCoreLoss() {
	        return coreLoss;
	    }

	    public double getTransformerLostTestResult() {
	        return transformerLostTestResult;
	    }

	    public void setCoreLoss(double coreLoss) {
	        this.coreLoss = coreLoss;
	    }

	    public void setTransformerLostTestResult(double transformerLostTestResult) {
	        this.transformerLostTestResult = transformerLostTestResult;
	    }

	    public void setConnCode(String connCode) {
	        this.connCode = connCode;
	    }

	    public String getConnCode() {
	        return connCode;
	    }

	    public void setContracted(boolean contracted) {
	        this.contracted = contracted;
	    }

	    public boolean getContracted() {
	        return contracted;
	    }

	    public double getDemandMax() {
	        return demandMax;
	    }

	    public void setDemandMax(double demandMax) {
	        this.demandMax = demandMax;
	    }

	    public double getDemandMin() {
	        return demandMin;
	    }

	    public void setDemandMin(double demandMin) {
	        this.demandMin = demandMin;
	    }

	    public double getSeniorCitizenDiscount() {
	        return seniorCitizenDiscount;
	    }

	    public void setSeniorCitizenDiscount(double seniorCitizenDiscount) {
	        this.seniorCitizenDiscount = seniorCitizenDiscount;
	    }

	    public boolean getSCSwitch() {
	        return SCSwitch;
	    }

	    public void setKw(double kw) {
	        this.kw = kw;
	    }

	    public void setDemandCharge(double demandCharge) {
	        this.demandCharge = demandCharge;
	    }

	    public double getKw() {
	        return kw;
	    }

	    public double getDemandCharge() {
	        return demandCharge;
	    }

	    public double getDemand() {
	        if (getDemandMin() != 0.0d) {
	            return getDemandMin() / getDemandMultiplier(true);
	        }
	        return getDemandMax() / getDemandMultiplier(true);
	    }

	    public double getDisco() {
	        return disco;
	    }

	    public void setDisco(double disco) {
	        this.disco = disco;
	    }

	    public double getIncentives() {
	        return incentives;
	    }

	    public void setIncentives(double incentives) {
	        this.incentives = incentives;
	    }

	    public double getMaterial() {
	        return material;
	    }

	    public void setMaterial(double material) {
	        this.material = material;
	    }

	    public double getEquiptment() {
	        return equiptment;
	    }

	    public void setEquiptment(double equiptment) {
	        this.equiptment = equiptment;
	    }

	    public double getOthersSurcharge() {
	        return othersSurcharge;
	    }

	    public void setOthersSurcharge(double othersSurcharge) {
	        this.othersSurcharge = othersSurcharge;
	    }
}
