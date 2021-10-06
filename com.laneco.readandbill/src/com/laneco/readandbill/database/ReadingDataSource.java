package com.laneco.readandbill.database;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import com.androidapp.mytools.objectmanager.ArrayManager;
import com.generic.readandbill.ConsumerArrayAdapter;
//import com.laneco.readandbill.ConsumerArrayAdapter; //.ConsumerArrayAdapter;

import com.laneco.readandbill.*;
import java.util.ArrayList;
import java.util.List;
public class ReadingDataSource  extends com.generic.readandbill.database.ReadingDataSource{
	    //public static final String CANCELLED = "cancelled";
	    //public static final String ROUTE = "route";
	    //public static final String SOA_NUMBER = "soanumber";
	    //public static final String SOA_PREFIX = "soaprefix";
	    private ReadandBillDatabaseHelper dbHelper;
	    private UserProfileDataSource dsUP;
	    private String[] lAllColumn;

	    public static List<String> readingFields() {
	        List<String> readingFields = com.generic.readandbill.database.ReadingDataSource.readingFields();
	        //readingFields.set(readingFields.size() - 1, ((String) readingFields.get(readingFields.size() - 1)) );// + ", ");
	        //readingFields.add("soaprefix text not null, ");
	        //readingFields.add("soanumber integer not null, ");
	        //readingFields.add("cancelled integer not null, ");
	        //readingFields.add("route text not null ");
	        return readingFields;
	    }

	    public ReadingDataSource(Context context) {
	        super(new ReadandBillDatabaseHelper(context), context);
	       // this.lAllColumn = new String[]{ SOA_NUMBER, CANCELLED, ROUTE};
	        this.dbHelper = new ReadandBillDatabaseHelper(context);
	        this.dsUP = new UserProfileDataSource(context);
	       // this.allColumns =ArrayManager.concat(this.allColumns, this.lAllColumn);
	        
	    }

	    protected ContentValues readingContentValues(Reading reading) {
	        ContentValues values = super.readingContentValues(reading);
	        //values.put(SOA_PREFIX, reading.getSoaPrefix());
	        //values.put(SOA_NUMBER, Long.valueOf(reading.getSoaNumber()));
	        //values.put(CANCELLED, Integer.valueOf(reading.getCancelledValue()));
	        //values.put(ROUTE, reading.getRoute());
	        return values;
	    }

	    public Reading createReading(Reading reading) {
	        this.db = this.dbHelper.getWritableDatabase();
	        long insertId = this.db.insert(com.generic.readandbill.database.ReadingDataSource.TABLE_READINGS, null, readingContentValues(reading));
	        this.db.close();
	        return getReading(insertId, 10);
	    }

	    public void deleteReading(String route) {
	        this.db = this.dbHelper.getWritableDatabase();
	        this.db.execSQL("Delete from readings Where route = '" + route + "'");
	        this.db.close();
	    }

	    public long getMaxSOANumber(String soaPrefix) {
	        this.db = this.dbHelper.getReadableDatabase();
	        Cursor cursor = this.db.rawQuery("SELECT MAX(IFNULL(soanumber,0)) + 1 FROM readings WHERE soaprefix='" + soaPrefix + "'", null);
	        cursor.moveToFirst();
	        long result = cursor.getLong(0);
	        cursor.close();
	        this.db.close();
	        return result;
	    }

	    protected Reading cursorToReading(Cursor cursor) {
	        Reading reading = (Reading) super.cursorToReading(cursor, new Reading());
	       // if (cursor.getCount() > 0) {
	            //reading.setSoaNumber(cursor.getLong(cursor.getColumnIndex(SOA_NUMBER)));
	            //reading.setSoaPrefix(cursor.getString(cursor.getColumnIndex(SOA_PREFIX)));
	            //reading.setCancelled(cursor.getInt(cursor.getColumnIndex(CANCELLED)));
	            //reading.setRoute(cursor.getString(cursor.getColumnIndex(ROUTE)));
	       // }
	        return reading;
	    }

	    public Reading getSummaryReading(long id, int mode) {
	        Cursor cursor;
	        this.db = this.dbHelper.getReadableDatabase();
	        Reading myReading = new Reading();
	        switch (mode) {
	            case ConsumerArrayAdapter.ACCOUNT_NUMBER /*10*/:
	                cursor = this.db.query(com.generic.readandbill.database.ReadingDataSource.TABLE_READINGS, this.allColumns, "_id=" + id + " and " + CANCELLED + " = " + 0, null, null, null, null);
	                break;
	            case ConsumerArrayAdapter.SEQUENCE /*20*/:
	                cursor = this.db.query(com.generic.readandbill.database.ReadingDataSource.TABLE_READINGS, this.allColumns, "idconsumer=" + id + " and " + CANCELLED + " = " + 0, null, null, null, null);
	                break;
	            default:
	                cursor = null;
	                break;
	        }
	        if (cursor != null) {
	            cursor.moveToFirst();
	            myReading = cursorToReading(cursor);
	            cursor.close();
	        }
	        this.db.close();
	        return myReading;
	    }

	    public Reading getReading(long id, int mode) {
	        Cursor cursor;
	        this.db = this.dbHelper.getReadableDatabase();
	        switch (mode) {
	            case ConsumerArrayAdapter.ACCOUNT_NUMBER /*10*/:
	                cursor = this.db.query(com.generic.readandbill.database.ReadingDataSource.TABLE_READINGS, this.allColumns, "_id=" + id + " and " + CANCELLED + " = 0 and " + ROUTE + "='" + this.dsUP.getUserProfile().getRoute() + "'", null, null, null, null);
	                break;
	            case ConsumerArrayAdapter.SEQUENCE  /*20*/:
	                cursor = this.db.query(com.generic.readandbill.database.ReadingDataSource.TABLE_READINGS, this.allColumns, "idconsumer=" + id + " and " + CANCELLED + "= 0 and " + ROUTE + "='" + this.dsUP.getUserProfile().getRoute() + "'", null, null, null, null);
	                break;
	            default:
	                cursor = null;
	                break;
	        }
	        cursor.moveToFirst();
	        Reading myReading = cursorToReading(cursor);
	        cursor.close();
	        this.db.close();
	        return myReading;
	    }

	    public void updateReading(Reading reading) {
	        this.db = this.dbHelper.getWritableDatabase();
	        long id = reading.getId();
	        Integer isPrinted = Integer.valueOf(0);
	        Integer isAM = Integer.valueOf(0);
	        if (reading.getIsPrinted().booleanValue()) {
	            isPrinted = Integer.valueOf(1);
	        }
	        if (reading.getIsAM().booleanValue()) {
	            isAM = Integer.valueOf(1);
	        }
	        this.db.update(com.generic.readandbill.database.ReadingDataSource.TABLE_READINGS, readingContentValues(reading), "_id=" + id, null);
	        this.db.close();
	    }

	    public void updateReadingCancelled(long idConsumer) {
	        this.db = this.dbHelper.getWritableDatabase();
	        this.db.execSQL("UPDATE readings SET cancelled= 1 WHERE idconsumer=" + idConsumer + " and " + ROUTE + "='" + this.dsUP.getUserProfile().getRoute() + "'");
	        this.db.close();
	    }

	    public List<Reading> getResultReading() {
	        List<Reading> result = new ArrayList();
	        this.db = this.dbHelper.getReadableDatabase();
	        Cursor cursor = this.db.query(com.generic.readandbill.database.ReadingDataSource.TABLE_READINGS, this.allColumns, "cancelled=0 and route = '" + this.dsUP.getUserProfile().getRoute() + "'", null, null, null, null);
	        cursor.moveToFirst();
	        while (!cursor.isAfterLast()) {
	            result.add(cursorToReading(cursor));
	            cursor.moveToNext();
	        }
	        this.db.close();
	        return result;
	    }

	    public boolean tableExist() {
	        this.db = this.dbHelper.getReadableDatabase();
	        Cursor cursor = this.db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = 'readings'", null);
	        if (cursor != null) {
	            if (cursor.getCount() > 0) {
	                cursor.close();
	                return true;
	            }
	            cursor.close();
	        }
	        this.db.close();
	        return false;
	    }

	    public void truncate() {
	        this.db = this.dbHelper.getWritableDatabase();
	        this.dbHelper.refreshTable(this.db, com.generic.readandbill.database.ReadingDataSource.TABLE_READINGS, readingFields());
	        this.db.close();
	    }
	}

