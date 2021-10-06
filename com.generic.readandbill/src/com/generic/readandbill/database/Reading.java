package com.generic.readandbill.database;
import android.text.format.Time;
public class Reading {
	protected double demand;
    protected String feedBackCode;
    protected long fieldFinding;
    protected long id;
    protected long idConsumer;
    protected Boolean isAM;
    protected Boolean isPrinted;
    protected double kilowatthour;
    protected double reading;
    protected String readingDate;
    protected String remarks;
    protected double seniorCitizenDiscount;
    protected double totalbill;
    protected long transactionDate;
    
    private String soaPrefix;
    private boolean cancelled;
    private String route;
    private long soaNumber;

    public Reading() {
        new Time().setToNow();
        this.id = -1;
        this.idConsumer = -1;
        this.reading = 0.0d;
        this.transactionDate = 0;
        this.isPrinted = Boolean.valueOf(false);
        this.feedBackCode = "";
        this.demand = 0.0d;
        this.fieldFinding = -1;
        this.seniorCitizenDiscount = 0.0d;
        this.kilowatthour = 0.0d;
        this.totalbill = 0.0d;
        this.remarks = "";
        this.isAM = Boolean.valueOf(false);
        this.readingDate = "";
        
        this.soaPrefix = "";
        this.soaNumber = -1;
        this.route = "";
    }

    
    public String getSoaPrefix() {
        return this.soaPrefix;
    }

    public void setSoaPrefix(String soaPrefix) {
        this.soaPrefix = soaPrefix;
    }
    public long getSoaNumber() {
        if (this.soaNumber == 0) {
            return 1;
        }
        return this.soaNumber;
    }

    public void setSoaNumber(long soaNumber) {
        this.soaNumber = soaNumber;
    }

    public int getCancelledValue() {
        if (this.cancelled) {
            return 1;
        }
        return 0;
    }

    public void setCancelled(int cancelled) {
        boolean z = true;
        if (cancelled != 1) {
            z = false;
        }
        this.cancelled = z;
    }

    public String getRoute() {
        return this.route;
    }

    public void setRoute(String route) {
        this.route = route;
    }
    //=============================
    //=============================
    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdConsumer() {
        return this.idConsumer;
    }

    public void setidConsumer(long idConsumer) {
        this.idConsumer = idConsumer;
    }

    public double getReading() {
        if (this != null) {
            return this.reading;
        }
        return 0.0d;
    }

    public void setReading(double reading) {
        this.reading = reading;
    }

    public double getDemand() {
        if (this != null) {
            return this.demand;
        }
        return 0.0d;
    }

    public void setDemand(double demand) {
        this.demand = demand;
    }

    public long getTransactionDate() {
        return this.transactionDate;
    }

    public void setTransactionDate(long transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Boolean getIsPrinted() {
        return this.isPrinted;
    }

    public void setIsPrinted(Boolean isPrinted) {
        this.isPrinted = isPrinted;
    }

    public String getFeedBackCode() {
        return this.feedBackCode;
    }

    public void setFeedBackCode(String feedBackCode) {
        this.feedBackCode = feedBackCode;
    }

    public long getFieldFinding() {
        return this.fieldFinding;
    }

    public void setFieldFinding(long fieldFinding) {
        this.fieldFinding = fieldFinding;
    }

    public double getSeniorCitizenDiscount() {
        return this.seniorCitizenDiscount;
    }

    public void setSeniorCitizenDiscount(double seniorCitizenDiscount) {
        this.seniorCitizenDiscount = seniorCitizenDiscount;
    }

    public double getKilowatthour() {
        return this.kilowatthour;
    }

    public void setKilowatthour(double kilowatthour) {
        this.kilowatthour = kilowatthour;
    }

    public String getRemarks() {
        return this.remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public double getTotalbill() {
        return this.totalbill;
    }

    public void setTotalbill(double totalbill) {
        this.totalbill = totalbill;
    }

    public Boolean getIsAM() {
        return this.isAM;
    }

    public void setIsAM(Boolean isAM) {
        this.isAM = isAM;
    }

    public String getReadingDate() {
        return this.readingDate;
    }

    public void setReadingDate(String readingDate) {
        this.readingDate = readingDate;
    }
}
