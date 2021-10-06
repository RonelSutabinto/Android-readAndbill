package com.laneco.readandbill.database;

public class Reading extends com.generic.readandbill.database.Reading{
	//private boolean cancelled;
    //private String route;
    //private long soaNumber;
    //private String soaPrefix;

    public Reading() {
        //this.soaPrefix = "";
        //this.soaNumber = -1;
        //this.route = "";
    }
    /*
    public String getSoaPrefix() {
        return this.soaPrefix;
    }

    public void setSoaPrefix(String soaPrefix) {
        this.soaPrefix = soaPrefix;
    }*/
/*
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
    }*/
}
