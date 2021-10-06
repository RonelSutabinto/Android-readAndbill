package com.generic.readandbill.database;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;
public class RateDataSource {
	   public static final String DCDEMAND = "dcdemand";
	    public static final String DCSYSTEM = "dcsystem";
	    public static final String GENSYS = "gensys";
	    public static final String HOSTCOMM = "hostcomm";
	    public static final String ID = "_id";
	    public static final String LIFELINESUBSIDY = "lifelinesubsidy";
	    public static final String MCRETAILCUST = "mcretailcust";
	    public static final String MCSYS = "mcsys";
	    public static final String PARR = "parr";
	    public static final String PREVYEARADJPOWERCOST = "prevyearadjpowercost";
	    public static final String RATECODE = "ratecode";
	    public static final String REINVESTMENTFUNDSUSTCAPEX = "reinvestmentfundsustcapex";
	    public static final String SCKILOWATTHOURLIMIT = "sckilowatthourlimit";
	    public static final String SCRETAILCUST = "scretailcust";
	    public static final String SCSUPPLYSYS = "scsupplysys";
	    public static final String SCSWITCH = "scswitch";
	    public static final String SENIORCITIZENDISCOUNT = "seniorcitizendiscount";
	    public static final String SENIORCITIZENSUBSIDY = "seniorcitizensubsidy";
	    public static final String SYSTEMLOSS = "systemloss";
	    public static final String TABLE_RATES = "rates";
	    public static final String TCDEMAND = "tcdemand";
	    public static final String TCSYSTEM = "tcsystem";
	    public static final String UCEC = "ucec";
	    public static final String UCME = "ucme";
	    public static final String UCSD = "ucsd";
	    protected String[] allColumns;
	    protected SQLiteDatabase db;
	    protected ReadandBillDatabaseHelper dbHelper;

	    public static List<String> ratesFields() {
	        List<String> rateFields = new ArrayList();
	        rateFields.add("_id integer primary key autoincrement, ");
	        rateFields.add("ratecode text not null, ");
	        rateFields.add("gensys real not null, ");
	        rateFields.add("hostcomm real not null, ");
	        rateFields.add("systemloss real not null, ");
	        rateFields.add("parr real not null, ");
	        rateFields.add("tcdemand real not null, ");
	        rateFields.add("tcsystem real not null, ");
	        rateFields.add("dcdemand real not null, ");
	        rateFields.add("dcsystem real not null, ");
	        rateFields.add("scretailcust real not null, ");
	        rateFields.add("scsupplysys real not null, ");
	        rateFields.add("mcretailcust real not null, ");
	        rateFields.add("mcsys real not null, ");
	        rateFields.add("lifelinesubsidy real not null, ");
	        rateFields.add("scswitch real not null, ");
	        rateFields.add("seniorcitizensubsidy real not null, ");
	        rateFields.add("sckilowatthourlimit real not null, ");
	        rateFields.add("seniorcitizendiscount real not null, ");
	        rateFields.add("reinvestmentfundsustcapex real not null, ");
	        rateFields.add("prevyearadjpowercost real not null, ");
	        rateFields.add("ucme real not null, ");
	        rateFields.add("ucec real not null, ");
	        rateFields.add("ucsd real not null");
	        return rateFields;
	    }

	    public RateDataSource(Context context) {
	        this.allColumns = new String[]{ID, RATECODE, GENSYS, HOSTCOMM, TCDEMAND, TCSYSTEM, SYSTEMLOSS, DCDEMAND, DCSYSTEM, SCRETAILCUST, SCSUPPLYSYS, MCRETAILCUST, MCSYS, UCME, UCEC, PARR, LIFELINESUBSIDY, PREVYEARADJPOWERCOST, REINVESTMENTFUNDSUSTCAPEX, SCSWITCH, SCKILOWATTHOURLIMIT, SENIORCITIZENSUBSIDY, SENIORCITIZENDISCOUNT,UCSD};
	        this.dbHelper = new ReadandBillDatabaseHelper(context);
	    }

	    public RateDataSource(ReadandBillDatabaseHelper dbHelper, Context context) {
	        this.allColumns = new String[]{ID, RATECODE, GENSYS, HOSTCOMM, TCDEMAND, TCSYSTEM, SYSTEMLOSS, DCDEMAND, DCSYSTEM, SCRETAILCUST, SCSUPPLYSYS, MCRETAILCUST, MCSYS, UCME, UCEC, PARR, LIFELINESUBSIDY, PREVYEARADJPOWERCOST, REINVESTMENTFUNDSUSTCAPEX, SCSWITCH, SCKILOWATTHOURLIMIT, SENIORCITIZENSUBSIDY, SENIORCITIZENDISCOUNT,UCSD};
	        this.dbHelper = dbHelper;
	        if (this.dbHelper == null) {
	            RateDataSource rateDataSource = new RateDataSource(context);
	        }
	    }

	    protected ContentValues rateContentValues(Rates rate) {
	        ContentValues values = new ContentValues();
	        values.put(RATECODE, rate.getRateCode());
	        values.put(GENSYS, Double.valueOf(rate.getGenSys()));
	        values.put(HOSTCOMM, Double.valueOf(rate.getHostComm()));
	        values.put(TCDEMAND, Double.valueOf(rate.getTcDemand()));
	        values.put(TCSYSTEM, Double.valueOf(rate.getTcSystem()));
	        values.put(SYSTEMLOSS, Double.valueOf(rate.getSystemLoss()));
	        values.put(DCDEMAND, Double.valueOf(rate.getDcDemand()));
	        values.put(DCSYSTEM, Double.valueOf(rate.getDcDistribution()));
	        values.put(SCRETAILCUST, Double.valueOf(rate.getScRetailCust()));
	        values.put(SCSUPPLYSYS, Double.valueOf(rate.getScSupplySys()));
	        values.put(MCRETAILCUST, Double.valueOf(rate.getMcRetailCust()));
	        values.put(MCSYS, Double.valueOf(rate.getMcSys()));
	        values.put(UCME, Double.valueOf(rate.getUcme()));
	        values.put(UCEC, Double.valueOf(rate.getUcec()));
	        values.put(PARR, Double.valueOf(rate.getParr()));
	        values.put(LIFELINESUBSIDY, Double.valueOf(rate.getLifeLineSubsidy()));
	        values.put(PREVYEARADJPOWERCOST, Double.valueOf(rate.getPrevYearAdjPowerCost()));
	        values.put(REINVESTMENTFUNDSUSTCAPEX, Double.valueOf(rate.getReinvestmentFundSustCapex()));
	        values.put(SCSWITCH, rate.getScSwitch());
	        values.put(SCKILOWATTHOURLIMIT, Double.valueOf(rate.getScKiloWattHourLimit()));
	        values.put(SENIORCITIZENSUBSIDY, Double.valueOf(rate.getSeniorCitizenSubsidy()));
	        values.put(SENIORCITIZENDISCOUNT, Double.valueOf(rate.getSeniorCitizenDiscount()));
	        values.put(UCSD, Double.valueOf(rate.getUcsd()));
	        return values;
	    }

	    public Rates createRate(Rates rate) {
	        this.db = this.dbHelper.getWritableDatabase();
	        long insertId = this.db.insert(TABLE_RATES, null, rateContentValues(rate));
	        this.db.close();
	        return getRate(insertId);
	    }

	    public Rates getRate(String rateCode) {
	        this.db = this.dbHelper.getReadableDatabase();
	        Rates myRate = new Rates();
	        Cursor cursor = this.db.query(TABLE_RATES, this.allColumns, "ratecode='" + rateCode + "'", null, null, null, null);
	        cursor.moveToFirst();
	        myRate = cursorToRate(cursor, myRate);
	        cursor.close();
	        this.db.close();
	        return myRate;
	    }

	    public Rates getRate(long id) {
	        this.db = this.dbHelper.getReadableDatabase();
	        Rates myRate = new Rates();
	        Cursor cursor = this.db.query(TABLE_RATES, this.allColumns, "_id=_id", null, null, null, null);
	        cursor.moveToFirst();
	        myRate = cursorToRate(cursor, myRate);
	        cursor.close();
	        this.db.close();
	        return myRate;
	    }

	    protected Rates cursorToRate(Cursor cursor, Rates rate) {
	        if (cursor.getCount() == 0) {
	            return null;
	        }
	        rate.setId(cursor.getLong(cursor.getColumnIndex(ID)));
	        rate.setRateCode(cursor.getString(cursor.getColumnIndex(RATECODE)));
	        rate.setGenSys(cursor.getDouble(cursor.getColumnIndex(GENSYS)));
	        rate.setHostComm(cursor.getDouble(cursor.getColumnIndex(HOSTCOMM)));
	        rate.setTcDemand(cursor.getDouble(cursor.getColumnIndex(TCDEMAND)));
	        rate.setTcSystem(cursor.getDouble(cursor.getColumnIndex(TCSYSTEM)));
	        rate.setSystemLoss(cursor.getDouble(cursor.getColumnIndex(SYSTEMLOSS)));
	        rate.setDcDemand(cursor.getDouble(cursor.getColumnIndex(DCDEMAND)));
	        rate.setDcDistribution(cursor.getDouble(cursor.getColumnIndex(DCSYSTEM)));
	        rate.setScRetailCust(cursor.getDouble(cursor.getColumnIndex(SCRETAILCUST)));
	        rate.setScSupplySys(cursor.getDouble(cursor.getColumnIndex(SCSUPPLYSYS)));
	        rate.setMcRetailCust(cursor.getDouble(cursor.getColumnIndex(MCRETAILCUST)));
	        rate.setMcSys(cursor.getDouble(cursor.getColumnIndex(MCSYS)));
	        rate.setUcme(cursor.getDouble(cursor.getColumnIndex(UCME)));
	        rate.setUcec(cursor.getDouble(cursor.getColumnIndex(UCEC)));
	        rate.setParr(cursor.getDouble(cursor.getColumnIndex(PARR)));
	        rate.setLifeLineSubsidy(cursor.getDouble(cursor.getColumnIndex(LIFELINESUBSIDY)));
	        rate.setPrevYearAdjPowerCost(cursor.getDouble(cursor.getColumnIndex(PREVYEARADJPOWERCOST)));
	        rate.setReinvestmentFundSustCapex(cursor.getDouble(cursor.getColumnIndex(REINVESTMENTFUNDSUSTCAPEX)));
	        rate.setScSwitch(Boolean.valueOf(cursor.getLong(cursor.getColumnIndex(SCSWITCH)) > 0));
	        rate.setScKiloWattHourLimit(cursor.getDouble(cursor.getColumnIndex(SCKILOWATTHOURLIMIT)));
	        rate.setSeniorCitizenSubsidy(cursor.getDouble(cursor.getColumnIndex(SENIORCITIZENSUBSIDY)));
	        rate.setSeniorCitizenDiscount(cursor.getDouble(cursor.getColumnIndex(SENIORCITIZENDISCOUNT)));
	        rate.setUcsd(cursor.getDouble(cursor.getColumnIndex(UCSD)));
	        return rate;
	    }

	    public void truncate() {
	        this.db = this.dbHelper.getWritableDatabase();
	        this.dbHelper.refreshTable(this.db, TABLE_RATES, ratesFields());
	        this.db.close();
	    }
}
