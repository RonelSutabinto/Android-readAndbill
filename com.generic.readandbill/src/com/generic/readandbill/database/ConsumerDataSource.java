package com.generic.readandbill.database;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;


public class ConsumerDataSource {
	public static final String ACCOUNTNUMBER = "accountnumber";
    public static final String ADDRESS = "address";
    public static final String ID = "_id";
    public static final String INITIALREADING = "initialreading";
    public static final String METERSERIAL = "meterserial";
    public static final String MULTIPLIER = "multiplier";
    public static final String NAME = "name";
    public static final String RATECODE = "ratecode";
    public static final String READINGDATE = "readingdate";
    public static final String TABLE_CONSUMERS = "consumers";
    public static final String TRANSFORMER_RENTAL = "transformerrental";
    protected String[] allColumns;
    protected SQLiteDatabase db;
    protected ReadandBillDatabaseHelper dbHelper;
    private UserProfileDataSource dsUP;

    public static List<String> consumerFields() {
        List<String> consumerFields = new ArrayList();
        consumerFields.add("_id integer primary key autoincrement, ");
        consumerFields.add("accountnumber text not null, ");
        consumerFields.add("name text not null, ");
        consumerFields.add("address text not null, ");
        consumerFields.add("ratecode text not null, ");
        consumerFields.add("meterserial text not null, ");
        consumerFields.add("readingdate text not null, ");
        consumerFields.add("initialreading real not null, ");
        consumerFields.add("transformerrental real not null, ");
        consumerFields.add("multiplier real not null");
        return consumerFields;
    }

    public ConsumerDataSource(Context context) {
        this.allColumns = new String[]{ID, ACCOUNTNUMBER, NAME, ADDRESS, RATECODE, METERSERIAL, READINGDATE, INITIALREADING, MULTIPLIER, TRANSFORMER_RENTAL};
        this.dbHelper = new ReadandBillDatabaseHelper(context);
    }

    public ConsumerDataSource(ReadandBillDatabaseHelper dbHelper, Context context) {
        this.allColumns = new String[]{ID, ACCOUNTNUMBER, NAME, ADDRESS, RATECODE, METERSERIAL, READINGDATE, INITIALREADING, MULTIPLIER, TRANSFORMER_RENTAL};
        this.dbHelper = dbHelper;
        if (this.dbHelper == null) {
            ConsumerDataSource consumerDataSource = new ConsumerDataSource(context);
        }
    }

    public boolean isOpen() {
        if (this.db != null) {
            return this.db.isOpen();
        }
        return false;
    }

    protected ContentValues consumerContentValues(Consumer consumer) {
        ContentValues values = new ContentValues();
        values.put(ACCOUNTNUMBER, consumer.getAccountNumber());
        values.put(NAME, consumer.getName());
        values.put(ADDRESS, consumer.getAddress());
        values.put(RATECODE, consumer.getRateCode());
        values.put(MULTIPLIER, Double.valueOf(consumer.getMultiplier()));
        values.put(METERSERIAL, consumer.getMeterSerial());
        values.put(READINGDATE, consumer.getInitialReadingDate());
        values.put(INITIALREADING, Double.valueOf(consumer.getInitialReading()));
        values.put(TRANSFORMER_RENTAL, Double.valueOf(consumer.getTransformerRental()));
        return values;
    }

    public Consumer createConsumer(Consumer consumer) {
        this.db = this.dbHelper.getWritableDatabase();
        long insertId = this.db.insert(TABLE_CONSUMERS, null, consumerContentValues(consumer));
        this.db.close();
        return getConsumer(Long.valueOf(insertId));
    }

    public Consumer getConsumer(Long id) {
        this.db = this.dbHelper.getReadableDatabase();
        Cursor cursor = this.db.query(TABLE_CONSUMERS, this.allColumns, "_id=" + id, null, null, null, null);
        cursor.moveToFirst();
        this.db.close();
        return cursorToConsumer(cursor, new Consumer());
    }

    public List<Consumer> getAllConsumer() {
        this.db = this.dbHelper.getReadableDatabase();
        Cursor cursor = this.db.query(TABLE_CONSUMERS, this.allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        List<Consumer> result = new ArrayList();
        while (!cursor.isAfterLast()) {
            result.add(cursorToConsumer(cursor, new Consumer()));
            cursor.moveToNext();
        }
        cursor.close();
        this.db.close();
        return result;
    }

    public List<Consumer> getAllUnReadConsumer() {
        this.db = this.dbHelper.getReadableDatabase();
        List<Consumer> result = new ArrayList();
        Cursor cursor = this.db.rawQuery("SELECT c.* FROM consumers c LEFT JOIN readings r on r.idconsumer = c._id WHERE r._id is null;", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            result.add(cursorToConsumer(cursor, new Consumer()));
            cursor.moveToNext();
        }
        cursor.close();
        this.db.close();
        return result;
    }

    protected Consumer cursorToConsumer(Cursor cursor, Consumer consumer) {
        consumer.setId(cursor.getLong(cursor.getColumnIndex(ID)));
        consumer.setAccountNumber(cursor.getString(cursor.getColumnIndex(ACCOUNTNUMBER)));
        consumer.setName(cursor.getString(cursor.getColumnIndex(NAME)));
        consumer.setAddress(cursor.getString(cursor.getColumnIndex(ADDRESS)));
        consumer.setRateCode(cursor.getString(cursor.getColumnIndex(RATECODE)));
        consumer.setMeterSerial(cursor.getString(cursor.getColumnIndex(METERSERIAL)));
        consumer.setInitialReadingDate(cursor.getString(cursor.getColumnIndex(READINGDATE)));
        consumer.setInitialReading(cursor.getDouble(cursor.getColumnIndex(INITIALREADING)));
        consumer.setMultiplier(cursor.getDouble(cursor.getColumnIndex(MULTIPLIER)));
        consumer.setTransformerRental(cursor.getDouble(cursor.getColumnIndex(TRANSFORMER_RENTAL)));
        return consumer;
    }
/*
    protected Consumer cursorToConsumerSet(Cursor cursor) {
    	Consumer consumer = new Consumer();
        if (cursor.getCount() == 0) {
            return consumer;
        }
        consumer.setId(cursor.getLong(cursor.getColumnIndex(ID)));
        consumer.setAccountNumber(cursor.getString(cursor.getColumnIndex(ACCOUNTNUMBER)));
        consumer.setName(cursor.getString(cursor.getColumnIndex(NAME)));
        consumer.setAddress(cursor.getString(cursor.getColumnIndex(ADDRESS)));
        consumer.setRateCode(cursor.getString(cursor.getColumnIndex(RATECODE)));
        consumer.setMeterSerial(cursor.getString(cursor.getColumnIndex(METERSERIAL)));
        consumer.setInitialReadingDate(cursor.getString(cursor.getColumnIndex(READINGDATE)));
        consumer.setInitialReading(cursor.getDouble(cursor.getColumnIndex(INITIALREADING)));
        consumer.setMultiplier(cursor.getDouble(cursor.getColumnIndex(MULTIPLIER)));
        consumer.setTransformerRental(cursor.getDouble(cursor.getColumnIndex(TRANSFORMER_RENTAL)));
        return consumer;
    }
    
    public List<Consumer> getReadSummarySet(String orderType) {
        this.db = this.dbHelper.getReadableDatabase();
        List<Consumer> result = new ArrayList();
        Cursor cursor = this.db.rawQuery("SELECT c.* FROM consumers c INNER JOIN readings r on r.idconsumer = c._id WHERE r.cancelled = 0 and r.route = '" + this.dsUP.getUserProfile().getRoute() + "' order by c."+orderType, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            result.add(cursorToConsumerSet(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        this.db.close();
        return result;
    }

    public List<Consumer> getUnreadSummarySet(String orderType) {
        this.db = this.dbHelper.getReadableDatabase();
        List<Consumer> result = new ArrayList();
        Cursor cursor = this.db.rawQuery("SELECT c.* FROM consumers c LEFT JOIN readings r on r.idconsumer = c._id WHERE r._id is null order by c."+orderType, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            result.add(cursorToConsumerSet(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        this.db.close();
        return result;
    }*/
    
    public Integer getNumberOfConsumer() {
        this.db = this.dbHelper.getReadableDatabase();
        Integer result = Integer.valueOf(0);
        Cursor cursor = this.db.rawQuery("select count(*) recCount from consumers", null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            result = Integer.valueOf(cursor.getInt(0));
        } else {
            result =0;// Integer.valueOf(0);
        }
        this.db.close();
        return result;
    }

    public void truncate() {
        this.db = this.dbHelper.getWritableDatabase();
        this.dbHelper.refreshTable(this.db, TABLE_CONSUMERS, consumerFields());
        this.db.close();
    }
    
    
}
