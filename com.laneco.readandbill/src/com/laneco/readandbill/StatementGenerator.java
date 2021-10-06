package com.laneco.readandbill;

import android.content.Context;
import android.text.format.Time;
import com.androidapp.mytools.bluetooth.PrinterControls;
import com.androidapp.mytools.objectmanager.StringManager;
import com.laneco.readandbill.database.ComputeCharges;
import com.laneco.readandbill.database.Consumer;
import com.laneco.readandbill.database.RateDataSource;
import com.laneco.readandbill.database.Rates;
import com.laneco.readandbill.database.Reading;
import com.laneco.readandbill.database.UserProfile;
import com.laneco.readandbill.database.UserProfileDataSource;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
public class StatementGenerator {
	protected DecimalFormat amountFormat;
    private ComputeCharges compute;
    private Consumer consumer;
    protected DecimalFormat kilowattUsed;
    protected DecimalFormat percentage;
    private Rates rate;
    protected DecimalFormat rateFormat;
    private Reading reading;
    protected DecimalFormat totalAmountFormat;
    private UserProfile userProfile;
    private DecimalFormat vatFormat;

    public StatementGenerator(Context context, Consumer myConsumer, Reading reading) {
        RateDataSource dsRate = new RateDataSource(context);
        UserProfileDataSource dsUserProfile = new UserProfileDataSource(context);
        this.consumer = myConsumer;
        this.compute = new ComputeCharges(context, myConsumer);
        if (this.consumer != null) {
            this.rate = dsRate.getConsumerRate(this.consumer.getRateCode());
        }
        this.userProfile = dsUserProfile.getUserProfile();
        this.kilowattUsed = new DecimalFormat("###0.0");
        this.percentage = new DecimalFormat("##0");
        this.amountFormat = new DecimalFormat("###,##0.00");
        this.totalAmountFormat = new DecimalFormat("###,###,##0.00");
        this.rateFormat = new DecimalFormat("0.0000");
        this.vatFormat = new DecimalFormat("##,##0.00");
        this.reading = reading;
        this.compute.setReading(reading);
    }

    public List<String> generateSOA() {
        List<String> result = new ArrayList();
        for (String string : soaHeader()) {
            result.add(string);
        }
        for (String string2 : soaBody()) {
            result.add(string2);
        }
        for (String string22 : soaFooter()) {
            result.add(string22);
        }
        return result;
    }

    protected List<String> soaHeader() {
        List<String> result = new ArrayList();
        result.add(PrinterControls.char48());
        if (PrinterControls.btPrinter.getDeviceName().equals("SPP-R300")) {
            result.add("\u001b31");
        }
        result.add("Ver 2.2.0.0\n");
        result.add(PrinterControls.emphasized(true));
        result.add(StringManager.centerJustify("LANAO DEL NORTE ELECTRIC COOPERATIVE, INC.", 48) + "\n");
        result.add(PrinterControls.emphasized(false));
        result.add(StringManager.centerJustify("Tubod, Lanao del Norte, Mindanao, Philippines", 48) + "\n");
        result.add(StringManager.centerJustify("VAT REG. TIN 000-954-728-000", 48) + "\n");
        result.add(StringManager.centerJustify("TEL. NO. (063)341-5231 FAX NO. (063)341-5210", 48) + "\n");
        result.add(StringManager.centerJustify("E-MAIL: laneco_neptune@yahoo.com", 48) + "\n");
        result.add(PrinterControls.emphasized(true));
        result.add(StringManager.centerJustify("STATEMENT OF ACCOUNT", 48) + "\n");
        result.add(PrinterControls.emphasized(false));
        result.add(StringManager.centerJustify(this.userProfile.getBillingPeriod(), 48) + "\n");
        result.add(StringManager.centerJustify("Billing Period " + this.userProfile.getInitialReadingDate() + " to " + this.userProfile.getReadingDate(), 48) + "\n");
        result.add(StringManager.leftJustify(StringManager.leftJustify(this.consumer.getAccountNumber(), 10) + StringManager.centerJustify("TYPE - " + this.consumer.getRateCode(), 14) + StringManager.leftJustify("DUE DATE: ", 10) + StringManager.rightJustify(this.userProfile.getDueDate(), 14), 48) + "\n");
        result.add(StringManager.leftJustify(StringManager.leftJustify(this.consumer.getName(), 24) + StringManager.leftJustify("Energized:", 10) + StringManager.rightJustify(this.consumer.getDateEnergized(), 14), 48) + "\n");
        result.add(StringManager.leftJustify(StringManager.leftJustify(this.consumer.getMeterSerial() + " Brand " + this.consumer.getMeterBrand(), 24) + StringManager.leftJustify("POLE No. ", 10) + StringManager.rightJustify(this.consumer.getPoleNumber(), 14), 48) + "\n");
        result.add(StringManager.leftJustify(StringManager.leftJustify("Ave KWHr ", 10) + StringManager.rightJustify(String.valueOf(this.consumer.getAveKwh()) + " ", 14) + StringManager.leftJustify("TransLoss ", 10) + StringManager.rightJustify(String.valueOf(this.consumer.getTransLoss()), 14), 48) + "\n");
        result.add(lineBreak(48));
        for (String string : readingDetail()) {
            result.add(string);
        }
        return result;
    }

    private List<String> readingDetail() {
        List<String> result = new ArrayList();
        result.add(StringManager.rightJustify("PRES READING", 14) + StringManager.leftJustify(StringManager.rightJustify("PREV READING", 14) + StringManager.rightJustify("MULT.", 6) + StringManager.rightJustify("KILOWATTHOUR", 14), 48) + "\n");
        if (this.reading.getFeedBackCode().equals("A")) {
            result.add(StringManager.rightJustify(String.valueOf(this.consumer.getInitialReading()), 13) + " " + StringManager.leftJustify(StringManager.rightJustify(String.valueOf(this.consumer.getInitialReading()), 13) + " " + StringManager.rightJustify(String.valueOf(this.consumer.getMultiplier()), 5) + " " + StringManager.rightJustify(String.valueOf(this.compute.getKilowatthour()), 14), 48) + "\n");
        } else {
            result.add(StringManager.rightJustify(String.valueOf(this.reading.getReading()), 13) + " " + StringManager.leftJustify(StringManager.rightJustify(String.valueOf(this.consumer.getInitialReading()), 13) + " " + StringManager.rightJustify(String.valueOf(this.consumer.getMultiplier()), 5) + " " + StringManager.rightJustify(String.valueOf(this.compute.getKilowatthour()), 14), 48) + "\n");
        }
        if (this.consumer.getChangeMeterKilowatthour() > 0.0d) {
            result.add("with Change Meter");
            result.add(String.valueOf(this.consumer.getChangeMeterKilowatthour()) + "\n");
        }
        result.add(StringManager.leftJustify(StringManager.rightJustify("LFL KWHr", 14) + StringManager.rightJustify("DEM. READING", 14) + StringManager.rightJustify("MULT.", 6) + StringManager.rightJustify("KILOWATT USED", 14), 48) + "\n");
        result.add(StringManager.leftJustify(StringManager.rightJustify(String.valueOf(this.compute.getLifelineKilowatthourDisplay()), 13) + " " + StringManager.rightJustify(String.valueOf(this.reading.getDemand()), 13) + " " + StringManager.rightJustify(String.valueOf(this.consumer.getDemandMultiplier(true)), 5) + " " + StringManager.rightJustify(String.valueOf(this.compute.getKilowattUsed()), 14), 48) + "\n");
        return result;
    }

    protected List<String> soaBody() {
        List<String> result = new ArrayList();
        result.add(lineBreak(48));
        result.add(StringManager.leftJustify("", 30) + StringManager.centerJustify("RATE", 7) + StringManager.centerJustify("AMOUNT", 11) + "\n");
        result.add(PrinterControls.emphasized(true));
        result.add("GENERATION AND TRANSMISSION\n");
        result.add(PrinterControls.emphasized(false));
        if (this.compute.genSys().doubleValue() != 0.0d) {
            result.add(bodyLineGenerator("Generation System Charge", this.rate.getGenSys(), this.compute.genSys().doubleValue()) + "\n");
        }
        if (this.compute.hostComm().doubleValue() != 0.0d) {
            result.add(bodyLineGenerator("Franchise & Ben. to Host Comm", this.rate.getHostComm(), this.compute.hostComm().doubleValue()) + "\n");
        }
        if (this.compute.powerActRateRed2().doubleValue() != 0.0d) {
            result.add(bodyLineGenerator("Power Act Reduction", this.rate.getParr(), this.compute.powerActRateRed2().doubleValue()) + "\n");
        }
        if (this.compute.tcSystem().doubleValue() != 0.0d) {
            result.add(bodyLineGenerator("Transmission System Charge", this.rate.getTcSystem(), this.compute.tcSystem().doubleValue()) + "\n");
        }
        if (this.compute.tcDemand().doubleValue() != 0.0d) {
            result.add(bodyLineGenerator("Transmission Dem. Charge", this.rate.getTcDemand(), this.compute.tcDemand().doubleValue()) + "\n");
        }
        if (this.compute.systemLoss().doubleValue() != 0.0d) {
            result.add(bodyLineGenerator("System Loss Charge", this.rate.getSystemLoss(), this.compute.systemLoss().doubleValue()) + "\n");
        }
        result.add(PrinterControls.emphasized(true));
        result.add("DISTRIBUTION REVENUES\n");
        result.add(PrinterControls.emphasized(false));
        if (this.compute.dcDistribution().doubleValue() != 0.0d) {
            result.add(bodyLineGenerator("Distribution System Charge", this.rate.getDcDistribution(), this.compute.dcDistribution().doubleValue()) + "\n");
        }
        if (this.compute.dcDemand().doubleValue() != 0.0d) {
            result.add(bodyLineGenerator("Distribution Dem. Charge", this.rate.getDcDemand(), this.compute.dcDemand().doubleValue()) + "\n");
        }
        if (this.compute.scSupplySys().doubleValue() != 0.0d) {
            result.add(bodyLineGenerator("Supply System Charge", this.rate.getScSupplySys(), this.compute.scSupplySys().doubleValue()) + "\n");
        }
        if (this.compute.scRetailCust().doubleValue() != 0.0d) {
            result.add(bodyLineGenerator("Supply Retail Cust. Charge", this.rate.getScRetailCust(), this.compute.scRetailCust().doubleValue()) + "\n");
        }
        if (this.compute.mcSystem().doubleValue() != 0.0d) {
            result.add(bodyLineGenerator("Metering System Charge", this.rate.getMcSys(), this.compute.mcSystem().doubleValue()) + "\n");
        }
        if (this.compute.mcRetailCust().doubleValue() != 0.0d) {
            result.add(bodyLineGenerator("Metering Retail Customer", this.rate.getMcRetailCust(), this.compute.mcRetailCust().doubleValue()) + "\n");
        }
        if (this.compute.reinvestmentFundSustCapex().doubleValue() != 0.0d) {
            result.add(bodyLineGenerator("Reinvest. Fund For Sust.CAPEX", this.rate.getReinvestmentFundSustCapex(), this.compute.reinvestmentFundSustCapex().doubleValue()) + "\n");
        }
        result.add(PrinterControls.emphasized(true));
        result.add(StringManager.leftJustify("OTHERS", 48) + "\n");
        result.add(PrinterControls.emphasized(false));
        if (this.compute.lifelineDiscSubs().doubleValue() != 0.0d) {
            result.add(bodyLineGenerator("LifeLine (Discount) Subsidy", this.rate.getLifeLineSubsidy(), this.compute.lifelineDiscSubs().doubleValue()) + "\n");
        }
        if (this.compute.getSeniorCitizenDiscountSubsidy() != 0.0d) {
            if (!this.consumer.getSCSwitch()) {
                result.add(bodyLineGenerator("Senior Citizen (Disc.) Subs.", this.rate.getSeniorCitizenSubsidy(), this.compute.getSeniorCitizenDiscountSubsidy()) + "\n");
            } else if (this.consumer.getSCSwitch()) {
                result.add(bodyLineGenerator("Senior Citizen (Disc.) Subs.", this.consumer.getSeniorCitizenDiscount(), this.compute.getSeniorCitizenDiscountSubsidy()) + "\n");
            }
        }
        if (this.compute.prevYearAdjPowerCost().doubleValue() != 0.0d) {
            result.add(bodyLineGenerator("Previous Year Adjustment", this.rate.getPrevYearAdjPowerCost(), this.compute.prevYearAdjPowerCost().doubleValue()) + "\n");
        }
        if (this.compute.overUnderRecovery() != 0.0d) {
            result.add(bodyLineGenerator("(Over) / Under Recoveries", this.rate.getOverUnderRecovery(), this.compute.overUnderRecovery()) + "\n");
        }
        result.add(PrinterControls.emphasized(true));
        result.add("GOVERMENT REVENUES\n");
        result.add(PrinterControls.emphasized(false));
        if (this.compute.realPropertyTax() != 0.0d) {
            result.add(bodyLineGenerator("Real Property Tax", this.rate.getRealPropertyTax(), this.compute.realPropertyTax())+ "\n");
        }
        if (this.compute.ucme().doubleValue() != 0.0d) {
            result.add(bodyLineGenerator("UC-ME (NPC-SPUG)", this.rate.getUcme(), this.compute.ucme().doubleValue()) + "\n");
        }
        if (this.compute.ucec().doubleValue() != 0.0d) {
            result.add(bodyLineGenerator("Environmental Charge", this.rate.getUcec(), this.compute.ucec().doubleValue()) + "\n");
        }
        if (this.compute.ucStrandedContractCost() != 0.0d) {
            result.add(bodyLineGenerator("Stranded Contract Cost", this.rate.getUcStrandedContractCost(), this.compute.ucStrandedContractCost()) + "\n");
        }
        if (this.compute.ucmeRed() != 0.0d) {
            result.add(bodyLineGenerator("UC-ME (RED)", this.rate.getUcmeRed(), this.compute.ucmeRed()) + "\n");
        }
        //UCSD Charge
        if (this.compute.ucsd() != 0.0d) {
            result.add(bodyLineGenerator("UCSD Charge", this.rate.getUcsd(), this.compute.ucsd()) + "\n");
        }
        if (this.compute.feedTariffAllowance() != 0.0d) {
            result.add(bodyLineGenerator("Fit-All (Renewable)", this.rate.getFeedTariffAllowance(), this.compute.feedTariffAllowance()) + "\n");
        }
        if (this.consumer.getDifferentialBillRecovery() != 0.0d) {
            result.add(bodyLineGenerator("Differential Billing/Recover", this.consumer.getDifferentialBillRecovery()) + "\n");
        }
        if (this.consumer.getOtherCharges() != 0.0d) {
            result.add(bodyLineGenerator("Other Charges (456/142/421)", this.consumer.getOtherCharges()) + "\n");
        }
        if (this.consumer.getArMats() != 0.0d) {
            result.add(bodyLineGenerator("A/R (Materials)", this.consumer.getArMats()) + "\n");
        }
        if (this.consumer.getTransformerRental() != 0.0d) {
            result.add(bodyLineGenerator("Transformer Rental", this.consumer.getTransformerRental()) + "\n");
        }
        if (this.compute.totalVat() != 0.0d) {
            result.add(bodyLineGenerator("Vat amount", this.compute.totalVat()) + "\n");
        }
        result.add(lineBreak(48));
        for (String string : amountDueDetail()) {
            result.add(string);
        }
        return result;
    }

    private String bodyLineGenerator(String description, double rate, double amount) {
        return "" + StringManager.leftJustify(description, 29) + StringManager.rightJustify(this.rateFormat.format(rate), 8) + StringManager.rightJustify(this.amountFormat.format(amount), 11);
    }

    private String bodyLineGenerator(String description, double amount) {
        return "" + StringManager.leftJustify(description, 34) + StringManager.rightJustify(this.totalAmountFormat.format(amount), 14);
    }

    private String footerTotalLineGenerator(String description, double amount) {
        return "" + StringManager.leftJustify(description, 34) + StringManager.rightJustify(this.amountFormat.format(amount), 14);
    }

    private List<String> amountDueDetail() {
        List<String> result = new ArrayList();
        result.add(PrinterControls.emphasized(true));
        result.add(footerTotalLineGenerator("TOTAL AMT DUE ON OR BEFOR DUE DATE", this.compute.totalCharge() + this.compute.totalVat()) + "\n");
        result.add(footerTotalLineGenerator("SERVICE FEE AND", this.compute.serviceFee()));
        result.add(footerTotalLineGenerator("SURCHARGE AFTER DUE(" + this.userProfile.getDueDate() + ")", this.compute.surcharge()) + "\n");
        result.add(footerTotalLineGenerator("ADD: VAT", this.compute.serviceFeeVat() + this.compute.surchargeVat()));
        result.add(lineBreak(48));
        result.add(footerTotalLineGenerator("TOTAL AMOUNT AFTER DUE DATE", this.compute.amountAfterDue().doubleValue()) + "\n");
        result.add(StringManager.rightJustify("==============", 48) + "\n");
        if (this.consumer.getArrears() != 0.0d) {
            result.add("ARREARS " + String.valueOf(this.consumer.getNumberOfArrears()) + "(surcharge & service fee " + "inclusive)\n");
            result.add(footerTotalLineGenerator("as of " + this.userProfile.getInitialReadingDate(), this.consumer.getArrears()) + "\n");
        }
        result.add("\n");
        result.add(StringManager.leftJustify(StringManager.leftJustify("VATABLE AMOUNT", 30) + StringManager.rightJustify(this.amountFormat.format(this.compute.taxableEnergy()), 14), 48) + "\n");
        result.add("\n");
        return result;
    }

    protected List<String> soaFooter() {
        Time myTime = new Time();
        myTime.set(this.reading.getTransactionDate());
        List<String> result = new ArrayList();
        result.add(StringManager.leftJustify("Date Delivered", 38) + StringManager.rightJustify(myTime.format("%D"), 10) + "\n");
        result.add(StringManager.leftJustify("Disconnection Date", 38) + StringManager.rightJustify(this.userProfile.getDiscoDate(), 10) + "\n");
        result.add(StringManager.leftJustify("SOA Control Number: ", 20) + StringManager.rightJustify(this.reading.getSoaPrefix() + "-" + StringManager.rightJustify(String.valueOf(this.reading.getSoaNumber()), 4).replace(" ", "0"), 28) + "\n");
        result.add("Meter Reader: " + this.userProfile.getName() + "\n");
        result.add(StringManager.centerJustify("THANK YOU FOR PAYING YOUR ELECTRIC BILL ON TIME", 48) + "\n");
        result.add(StringManager.centerJustify("GOD BLESS YOU.", 48) + "\n");
        result.add(String.valueOf('\n'));
        result.add(lineBreak(48));
        result.add(StringManager.centerJustify("Persuant  to  Republic  Act  No.7832,  otherwise", 48) + "\n");
        result.add(StringManager.centerJustify("known  as  the Anti-Pilferage of Electricity and", 48) + "\n");
        result.add(StringManager.centerJustify("Theft  of  Electic  Transmission Lines/Materials", 48) + "\n");
        result.add(StringManager.centerJustify("Act of 1994, STEALING OF ELECTRICITY IS A CRIME.", 48) + "\n");
        result.add("\n");
        result.add(PrinterControls.emphasized(true));
        result.add(StringManager.centerJustify("PAGPAKABANA BANTAY KURYENTE NAAY GANTI,ipahibalo", 48) + "\n");
        result.add(StringManager.centerJustify("sa pinakaduol nga buhatan sa LANECO", 48) + "\n");
        result.add(PrinterControls.emphasized(false));
        result.add(StringManager.pageBreak());
        return result;
    }

    public static String lineBreak(int numChar) {
        return "" + StringManager.leftJustify("", numChar).replace(" ", "-") + "\n";
    }
}
