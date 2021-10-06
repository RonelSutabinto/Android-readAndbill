package com.generic.readandbill.database;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;
public class ReadingDataSource {
	  public static final int CONSUMER_ID = 20;
	    public static final String DEMAND = "demand";
	    public static final String FEEDBACKCODE = "feedbackcode";
	    public static final String FIELDFINDING = "fieldFinding";
	    public static final String ID = "_id";
	    public static final String IDCONSUMER = "idconsumer";
	    public static final String ISAM = "isam";
	    public static final String ISPRINTED = "isprinted";
	    public static final String KILOWATTHOUR = "kilowatthour";
	    public static final String READING = "reading";
	    public static final String READINGDATE = "readingdate";
	    public static final int READING_ID = 10;
	    public static final String REMARKS = "remarks";
	    public static final String SENIORCITIZENDISCOUNT = "seniorcitizendiscount";
	    public static final String TABLE_READINGS = "readings";
	    public static final String TOTALAMOUNT = "totalamount";
	    public static final String TRANSACTIONDATE = "transactiondate";
	    
	    public static final String SOA_PREFIX = "soaprefix";
	    public static final String CANCELLED = "cancelled";
	    public static final String ROUTE = "route";
	    public static final String SOA_NUMBER = "soanumber";
	    
	    protected String[] allColumns;
	    protected SQLiteDatabase db;
	    private ReadandBillDatabaseHelper dbHelper;

	    public static List<String> readingFields() {
	        List<String> readingFields = new ArrayList();
	        readingFields.add("_id integer primary key autoincrement, ");
	        readingFields.add("idconsumer integer not null, ");
	        readingFields.add("reading real not null, ");
	        readingFields.add("transactiondate integer not null, ");
	        readingFields.add("isprinted integer not null, ");
	        readingFields.add("feedbackcode text not null, ");
	        readingFields.add("demand real not null, ");
	        readingFields.add("fieldFinding integer not null, ");
	        readingFields.add("seniorcitizendiscount real not null, ");
	        readingFields.add("kilowatthour real not null, ");
	        readingFields.add("remarks text not null, ");
	        readingFields.add("totalamount real not null, ");
	        readingFields.add("isam integer not null, ");
	        readingFields.add("readingdate text not null, ");
	        
	        readingFields.add("soaprefix text not null, ");
	        readingFields.add("soanumber integer not null, ");
	        readingFields.add("cancelled integer not null, ");
	        readingFields.add("route text not null ");
	        
	       
	        return readingFields;
	    }

	    public ReadingDataSource(Context context) {
	        this.allColumns = new String[]{ID, IDCONSUMER, READING, TRANSACTIONDATE, ISPRINTED, FEEDBACKCODE, DEMAND, FIELDFINDING, SENIORCITIZENDISCOUNT, KILOWATTHOUR, REMARKS, TOTALAMOUNT, ISAM, READINGDATE, SOA_PREFIX, SOA_NUMBER, CANCELLED, ROUTE};
	        this.dbHelper = new ReadandBillDatabaseHelper(context);
	    }

	    public ReadingDataSource(ReadandBillDatabaseHelper dbHelper, Context context) {
	        this.allColumns = new String[]{ID, IDCONSUMER, READING, TRANSACTIONDATE, ISPRINTED, FEEDBACKCODE, DEMAND, FIELDFINDING, SENIORCITIZENDISCOUNT, KILOWATTHOUR, REMARKS, TOTALAMOUNT, ISAM, READINGDATE, SOA_PREFIX, SOA_NUMBER, CANCELLED, ROUTE};
	        this.dbHelper = dbHelper;
	        if (this.dbHelper == null) {
	            ReadingDataSource readingDataSource = new ReadingDataSource(context);
	        }
	    }

	    protected ContentValues readingContentValues(Reading reading) {
	        ContentValues values = new ContentValues();
	        Integer isPrinted = Integer.valueOf(0);
	        Integer isAM = Integer.valueOf(0);
	        if (reading.isPrinted.booleanValue()) {
	            isPrinted = Integer.valueOf(1);
	        }
	        if (reading.isAM.booleanValue()) {
	            isAM = Integer.valueOf(1);
	        }
	        values.put(IDCONSUMER, Long.valueOf(reading.getIdConsumer()));
	        values.put(READING, Double.valueOf(reading.getReading()));
	        values.put(TRANSACTIONDATE, Long.valueOf(reading.getTransactionDate()));
	        values.put(ISPRINTED, isPrinted);
	        values.put(FEEDBACKCODE, reading.getFeedBackCode());
	        values.put(DEMAND, Double.valueOf(reading.getDemand()));
	        values.put(FIELDFINDING, Long.valueOf(reading.getFieldFinding()));
	        values.put(SENIORCITIZENDISCOUNT, Double.valueOf(reading.getSeniorCitizenDiscount()));
	        values.put(KILOWATTHOUR, Double.valueOf(reading.getKilowatthour()));
	        values.put(REMARKS, reading.getRemarks());
	        values.put(TOTALAMOUNT, Double.valueOf(reading.getTotalbill()));
	        values.put(ISAM, isAM);
	        values.put(READINGDATE, reading.getReadingDate());  
	        
	        values.put(SOA_PREFIX, reading.getSoaPrefix());
	        values.put(SOA_NUMBER, Long.valueOf(reading.getSoaNumber()));
	        values.put(CANCELLED, Integer.valueOf(reading.getCancelledValue()));
	        values.put(ROUTE, reading.getRoute());
	       	        
	        return values;
	    }

	    public Reading createReading(Reading reading) {
	        this.db = this.dbHelper.getWritableDatabase();
	        long insertId = this.db.insert(TABLE_READINGS, null, readingContentValues(reading));
	        this.db.close();
	        return getReading(insertId, READING_ID);
	    }

	    public void deleteReading(Reading reading) {
	        this.db = this.dbHelper.getWritableDatabase();
	        this.db.delete(TABLE_READINGS, "_id=" + reading.getId(), null);
	        this.db.close();
	    }

	    public void updateReading(Reading reading) {
	        this.db = this.dbHelper.getWritableDatabase();
	        this.db.update(TABLE_READINGS, readingContentValues(reading), "_id=" + reading.getId(), null);
	        this.db.close();
	    }

	    public List<Reading> getAllReadings() {
	        this.db = this.dbHelper.getReadableDatabase();
	        List<Reading> readings = new ArrayList();
	        Cursor cursor = this.db.query(TABLE_READINGS, this.allColumns, null, null, null, null, null);
	        cursor.moveToFirst();
	        while (!cursor.isAfterLast()) {
	            readings.add(cursorToReading(cursor, new Reading()));
	            cursor.moveToNext();
	        }
	        cursor.close();
	        this.db.close();
	        return readings;
	    }

	    public Reading getReading(long id, int mode) {
	        Cursor cursor;
	        this.db = this.dbHelper.getReadableDatabase();
	        Reading myReading = new Reading();
	        switch (mode) {
	            case READING_ID /*10*/:
	                cursor = this.db.query(TABLE_READINGS, this.allColumns, "_id=" + id, null, null, null, null);
	                break;
	            case CONSUMER_ID /*20*/:
	                cursor = this.db.query(TABLE_READINGS, this.allColumns, "idconsumer=" + id, null, null, null, null);
	                break;
	            default:
	                cursor = null;
	                break;
	        }
	        if (cursor != null) {
	            cursor.moveToFirst();
	            myReading = cursorToReading(cursor, myReading);
	            cursor.close();
	        }
	        this.db.close();
	        return myReading;
	    }

	    public Integer getTotalRead() {
	        this.db = this.dbHelper.getReadableDatabase();
	        Integer result = Integer.valueOf(0);
	        Cursor cursor = this.db.rawQuery("SELECT COUNT(*) FROM readings", null);
	        cursor.moveToFirst();
	        if (cursor.getCount() > 0) {
	            result = Integer.valueOf(cursor.getInt(0));
	        } else {
	            result = Integer.valueOf(0);
	        }
	        this.db.close();
	        return result;
	    }

	    public List<ZoneReport> getZoneReport() {
	        this.db = this.dbHelper.getReadableDatabase();
	        List<ZoneReport> zones = new ArrayList();
	        Cursor cursor = this.db.rawQuery("SELECT COUNT(*) as QtyToBeRead, transactiondate, MIN(transactiondate) as minTime, MAX(transactiondate) as maxTime, MIN(transactiondate) - MAX(transactiondate) as totalTime FROM readings GROUP BY isam, readingdate", null);
	        cursor.moveToFirst();
	        while (!cursor.isAfterLast()) {
	            zones.add(cursorToZone(cursor));
	            cursor.moveToNext();
	        }
	        cursor.close();
	        this.db.close();
	        return zones;
	    }

	    protected Reading cursorToReading(Cursor cursor, Reading reading) {
	        boolean z = true;
	        if (cursor.getCount() != 0) {
	            reading.setId(cursor.getLong(cursor.getColumnIndex(ID)));
	            reading.setidConsumer(cursor.getLong(cursor.getColumnIndex(IDCONSUMER)));
	            reading.setReading(cursor.getDouble(cursor.getColumnIndex(READING)));
	            reading.setTransactionDate(cursor.getLong(cursor.getColumnIndex(TRANSACTIONDATE)));
	            reading.setIsPrinted(Boolean.valueOf(cursor.getLong(cursor.getColumnIndex(ISPRINTED)) > 0));
	            reading.setFeedBackCode(cursor.getString(cursor.getColumnIndex(FEEDBACKCODE)));
	            reading.setDemand(cursor.getDouble(cursor.getColumnIndex(DEMAND)));
	            reading.setFieldFinding(cursor.getLong(cursor.getColumnIndex(FIELDFINDING)));
	            reading.setSeniorCitizenDiscount(cursor.getDouble(cursor.getColumnIndex(SENIORCITIZENDISCOUNT)));
	            reading.setKilowatthour(cursor.getDouble(cursor.getColumnIndex(KILOWATTHOUR)));
	            reading.setRemarks(cursor.getString(cursor.getColumnIndex(REMARKS)));
	            reading.setTotalbill(cursor.getDouble(cursor.getColumnIndex(TOTALAMOUNT)));
	            if (cursor.getInt(cursor.getColumnIndex(ISAM)) <= 0) {
	                z = false;
	            }
	            reading.setIsAM(Boolean.valueOf(z));
	            reading.setReadingDate(cursor.getString(cursor.getColumnIndex(READINGDATE)));
	            
	            if (cursor.getCount() > 0) {
	               reading.setSoaPrefix(cursor.getString(cursor.getColumnIndex(SOA_PREFIX)));
	               reading.setSoaNumber(cursor.getLong(cursor.getColumnIndex(SOA_NUMBER)));		            
		           reading.setCancelled(cursor.getInt(cursor.getColumnIndex(CANCELLED)));
		           reading.setRoute(cursor.getString(cursor.getColumnIndex(ROUTE)));
	            }
	        } else {
	            reading.setId(-1);
	        }
	        return reading;
	    }

	    private ZoneReport cursorToZone(Cursor cursor) {
	        ZoneReport zone = new ZoneReport();
	        if (cursor.getCount() != 0) {
	            zone.setQtyToBeRead(Long.valueOf(cursor.getLong(0)));
	            zone.setReadingDate(Long.valueOf(cursor.getLong(1)));
	            zone.setMinTime(Long.valueOf(cursor.getLong(2)));
	            zone.setMaxTime(Long.valueOf(cursor.getLong(3)));
	            zone.setTotalTime(Long.valueOf(cursor.getLong(4)));
	        }
	        return zone;
	    }

	   public void truncate() {
	        this.db = this.dbHelper.getWritableDatabase();
	        this.dbHelper.refreshTable(this.db, TABLE_READINGS, readingFields());
	        this.db.close();
	    }

	  public static final String DATABASE_CREATE() {
	        String result = "create table readings(";
	        for (String s : readingFields()) {
	            result = result + s;
	        }
	        return result + ");";
	    }
}
