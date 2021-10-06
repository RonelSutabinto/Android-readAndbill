package com.generic.readandbill.database;

public class FieldFindings {
	String fCode;
    String fDescription;
    long id;

    public FieldFindings() {
        this.id = -1;
        this.fCode = "0";
        this.fDescription = "";
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getfCode() {
        return this.fCode;
    }

    public void setfCode(String fCode) {
        this.fCode = fCode;
    }

    public String getfDescription() {
        return this.fDescription;
    }

    public void setfDescription(String fDescription) {
        this.fDescription = fDescription;
    }

    public String toString() {
        return this.fCode.toString() + " : " + this.fDescription;
    }
}
