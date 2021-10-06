package com.generic.readandbill.database;
import android.text.format.Time;
import java.util.concurrent.TimeUnit;
public class ZoneReport {
	protected Long maxTime;
    protected Long minTime;
    protected Long qtyToBeRead;
    protected Long readingDate;
    protected Long totalTime;

    public Long getQtyToBeRead() {
        return this.qtyToBeRead;
    }

    public void setQtyToBeRead(Long qtyToBeRead) {
        this.qtyToBeRead = qtyToBeRead;
    }

    public String getReadingDate() {
        Time myTime = new Time();
        myTime.set(this.readingDate.longValue());
        return myTime.format("%D");
    }

    public void setReadingDate(Long readingDate) {
        this.readingDate = readingDate;
    }

    public String getDay() {
        Time myTime = new Time();
        myTime.set(this.readingDate.longValue());
        return myTime.format("%A");
    }

    public String getMinTime() {
        Time myTime = new Time();
        myTime.set(this.minTime.longValue());
        return myTime.format("%H:%M:%S");
    }

    public void setMinTime(Long minTime) {
        this.minTime = minTime;
    }

    public String getMaxTime() {
        Time myTime = new Time();
        myTime.set(this.maxTime.longValue());
        return myTime.format("%H:%M:%S");
    }

    public void setMaxTime(Long maxTime) {
        this.maxTime = maxTime;
    }

    public String getTotalTime() {
        return String.format("%d min %d sec", new Object[]{Long.valueOf(TimeUnit.MILLISECONDS.toMinutes(this.totalTime.longValue())), Long.valueOf(TimeUnit.MILLISECONDS.toSeconds(this.totalTime.longValue()) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(this.totalTime.longValue())))});
    }

    public void setTotalTime(Long totalTime) {
        this.totalTime = totalTime;
    }

    public String getAvgTime() {
        Long myTime = Long.valueOf(this.totalTime.longValue() / getQtyToBeRead().longValue());
        return String.format("%d min %d sec", new Object[]{Long.valueOf(TimeUnit.MILLISECONDS.toMinutes(myTime.longValue())), Long.valueOf(TimeUnit.MILLISECONDS.toSeconds(myTime.longValue()) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(myTime.longValue())))});
    }
}
