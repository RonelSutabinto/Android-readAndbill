package com.laneco.readandbill.database;
import android.text.format.Time;
public class UserProfile extends com.generic.readandbill.database.UserProfile{
	    //protected String initialReadingDate;
	    // protected String readingDate;
	    // protected String route;
        /*
	    public UserProfile() {
	        this.route = "";
	        this.readingDate = "";
	        this.initialReadingDate = ""; 
	    }

	    public String getReadingDate() {
	        return this.readingDate;
	    }

	    public void setReadingDate(String readingDate) {
	        this.readingDate = readingDate;
	    }

	    public String getInitialReadingDate() {
	        return this.initialReadingDate;
	    }

	    public void setInitialReadingDate(String initialReadingDate) {
	        this.initialReadingDate = initialReadingDate;
	    }

	    public String getRoute() {
	        return this.route;
	    }

	    public void setRoute(String route) {
	        this.route = route;
	    }

	    public String getDueDate() {
	        if (this.readingDate.trim().equals("")) {
	            return "";
	        }
	        Time myReadingDate = getReadDate();
	        myReadingDate.monthDay += 9;
	        myReadingDate.normalize(true);
	        return myReadingDate.format("%D");
	    }

	    public String getDiscoDate() {
	        if (this.readingDate.trim().equals("")) {
	            return "";
	        }
	        Time myReadingDate = getReadDate();
	        myReadingDate.monthDay += 12;
	        myReadingDate.normalize(true);
	        return myReadingDate.format("%D");
	    }

	    private Time getReadDate() {
	        Time myReadingDate = new Time();
	        Integer month = Integer.valueOf(Integer.parseInt(this.readingDate.split("/")[0]) - 1);
	        myReadingDate.set(Integer.valueOf(Integer.parseInt(this.readingDate.split("/")[1])).intValue(), month.intValue(), Integer.valueOf(Integer.parseInt(this.readingDate.split("/")[2])).intValue());
	        return myReadingDate;
	    }

	    public String getBillingPeriod() {
	        return getReadDate().format("%B, %Y Billing");
	    }*/
}
