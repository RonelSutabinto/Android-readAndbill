package com.laneco.readandbill;
import android.content.Context;
import android.text.format.Time;
import com.androidapp.mytools.bluetooth.PrinterControls;
import com.androidapp.mytools.objectmanager.StringManager;
import com.generic.readandbill.database.Reading;
import com.laneco.readandbill.database.Consumer;
import com.laneco.readandbill.database.ReadingDataSource;
import com.laneco.readandbill.database.UserProfile;
import com.laneco.readandbill.database.UserProfileDataSource;
import java.util.ArrayList;
import java.util.List;
public class SummaryDataGenerator {
	private ReadingDataSource dsReading;
    private UserProfileDataSource dsUP;
    private String periodCover;
    private List<Consumer> summary;
    private Integer totalConsumer;
    private Double totalKilowatthour;
    private Integer totalRead;
    private Integer totalUnread;

    public SummaryDataGenerator(Context context, List<Consumer> summary, Integer totalConsumer) {
        this.dsReading = new ReadingDataSource(context);
        this.dsUP = new UserProfileDataSource(context);
        this.periodCover = "";
        this.summary = summary;
        this.totalRead = Integer.valueOf(summary.size());
        this.totalConsumer = totalConsumer;
        this.totalUnread = Integer.valueOf(totalConsumer.intValue() - this.totalRead.intValue());
    }

    public List<String> getSummary() {
        List<String> result = new ArrayList();
        for (String string : summaryHeader()) {
            result.add(string);
        }
        for (String string2 : summaryBody()) {
            result.add(string2);
        }
        for (String string22 : summaryFooter()) {
            result.add(string22);
        }
        return result;
    }

    private List<String> summaryHeader() {
        UserProfile userProfile = this.dsUP.getUserProfile();
        List<String> result = new ArrayList();
        new Time().set(this.dsReading.getReading(((Consumer) this.summary.get(0)).getId(), 20).getTransactionDate());
        this.totalKilowatthour = Double.valueOf(0.0d);
        if (PrinterControls.btPrinter.getDeviceName().equals("SPP-R300")) {
            result.add(PrinterControls.doubleHeight(false));
        }
        result.add("Ver 1.0.3.8\n");
        if (PrinterControls.btPrinter.getDeviceName().equals("EXTECH PRINTER") || PrinterControls.btPrinter.getDeviceName().equals("APEX 3")) {
            result.add(PrinterControls.doubleHeight(true));
            result.add(centerJustify("Reading Summary", 48) + "\n");
            result.add(PrinterControls.doubleHeight(false));
        } else if (PrinterControls.btPrinter.getDeviceName().equals("SPP-R300")) {
            result.add(PrinterControls.doubleHeight(true));
            result.add(centerJustify("Reading Summary", 32) + "\n");
            result.add(PrinterControls.doubleHeight(false));
        }
        result.add(leftJustify("", 48) + "\n");
        result.add(centerJustify(userProfile.getBillingPeriod(), 48) + "\n");
        result.add(leftJustify("", 48) + "\n");
        result.add(leftJustify("Acct#", 10) + " " + leftJustify("FB", 2) + " " + leftJustify("P.Read", 10) + " " + leftJustify("KWHr", 10) + " " + leftJustify("T", 1) + "\n");
        result.add(lineBreak());
        return result;
    }

    private List<String> summaryBody() {
        List<String> result = new ArrayList();
        this.totalKilowatthour = Double.valueOf(0.0d);
        for (int i = 0; i < this.summary.size(); i++) {
            Reading reading = this.dsReading.getSummaryReading(((Consumer) this.summary.get(i)).getId(), 20);
            result.add(leftJustify(((Consumer) this.summary.get(i)).getAccountNumber(), 10) + " " + leftJustify(reading.getFeedBackCode(), 2) + " " + rightJustify(String.valueOf(reading.getReading()), 10) + " " + rightJustify(String.valueOf(reading.getKilowatthour()), 10) + " " + leftJustify(((Consumer) this.summary.get(i)).getRateCode(), 1) + "\n");
            this.totalKilowatthour = Double.valueOf(this.totalKilowatthour.doubleValue() + reading.getKilowatthour());
        }
        return result;
    }

    private List<String> summaryFooter() {
        Integer size = Integer.valueOf(this.summary.size());
        UserProfile userProfile = this.dsUP.getUserProfile();
        List<String> result = new ArrayList();
        result.add(lineBreak());
        result.add(rightJustify(size.toString(), 24) + " " + rightJustify(this.totalKilowatthour.toString(), 10) + "\n");
        result.add(leftJustify("", 48) + "\n");
        result.add("Meter Reader  : " + userProfile.getName() + "\n");
        result.add(leftJustify("Total Record Read : ", 37) + rightJustify(this.totalRead.toString(), 10) + "\n");
        result.add(leftJustify("Total Record Unread : ", 37) + rightJustify(this.totalUnread.toString(), 10) + "\n");
        result.add(leftJustify("Total Record : ", 37) + rightJustify(this.totalConsumer.toString(), 10) + "\n");
        result.add("Billing Period: " + userProfile.getBillingPeriod());
        result.add(StringManager.pageBreak());
        return result;
    }

    private String leftJustify(String stringToJustify, int length) {
        String result = stringToJustify;
        for (int i = 1; i <= length - stringToJustify.length(); i++) {
            result = result + " ";
        }
        return result;
    }

    private String rightJustify(String stringToJustify, int length) {
        String result = "";
        for (int i = 1; i <= length - stringToJustify.length(); i++) {
            result = result + " ";
        }
        return result + stringToJustify;
    }

    private String centerJustify(String stringToJustify, int length) {
        String result = "";
        int left = (length - stringToJustify.length()) / 2;
        int right = left;
        if (length % 2 != 0) {
            left++;
        }
        return result + leftJustify("", left) + stringToJustify + rightJustify("", right);
    }

    private String lineBreak() {
        return "" + leftJustify("", 47).replace(" ", "-") + "\n";
    }
}
