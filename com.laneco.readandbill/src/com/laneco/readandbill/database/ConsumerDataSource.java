package com.laneco.readandbill.database;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import com.androidapp.mytools.objectmanager.ArrayManager;
import java.util.ArrayList;
import java.util.List;
public class ConsumerDataSource extends com.generic.readandbill.database.ConsumerDataSource {
	 public static final String ARMATS = "armats";
	    public static final String ARREARS = "arrears";
	    public static final String AVEKWH = "avekwh";
	    public static final String CHANGE_METER_KWHR = "changemeterkilowatthour";
	    public static final String CORELOSS = "coreloss";
	    public static final String DATEENERGIZED = "dateenergized";
	    public static final String DEMANDCHARGE = "demandcharge";
	    public static final String DEMAND_MAX = "demandmax";
	    public static final String DEMAND_MIN = "demandmin";
	    public static final String DEMAND_MULTIPLIER = "demandmultiplier";
	    public static final String DIFFERENTIAL_BILL_RECOVERY = "differentialbillrecovery";
	    public static final String DISCO = "disco";
	    public static final String EQUIPTMENT = "equiptment";
	    public static final String HELP = "help";
	    public static final String INCENTIVES = "incentives";
	    public static final String KW = "kw";
	    public static final String MATERIAL = "material";
	    public static final String METERBRAND = "meterbrand";
	    public static final String NUMBER_OF_ARREARS = "numarrears";
	    public static final String OTHERSSURCHARGE = "othersSurcharge";
	    public static final String PILFER = "pilfer";
	    public static final String POLENUMBER = "polenumber";
	    public static final String REFUND = "refund";
	    public static final String SCAP = "scap";
	    public static final String SENIORCITIZEN_DISCOUNT = "seniorcitizendiscount";
	    public static final String TRANSFORMERLOSTTESTRESULT = "transformerlosttestresult";
	    public static final String TRANSFORMERNUMBER = "transformernumber";
	    private ReadandBillDatabaseHelper dbHelper;
	    private UserProfileDataSource dsUP;
	    private String[] lAllColumn;

	    public static List<String> consumerFields() {
	        List<String> consumerFields = com.generic.readandbill.database.ConsumerDataSource.consumerFields();
	        consumerFields.set(consumerFields.size() - 1, ((String) consumerFields.get(consumerFields.size() - 1)) + ", ");
	        consumerFields.add("demandmultiplier real not null, ");
	        consumerFields.add("demandmin real not null, ");
	        consumerFields.add("demandmax real not null, ");
	        consumerFields.add("meterbrand text, ");
	        consumerFields.add("avekwh real not null, ");
	        consumerFields.add("dateenergized text not null, ");
	        consumerFields.add("transformernumber text not null, ");
	        consumerFields.add("polenumber text not null, ");
	        consumerFields.add("coreloss real not null, ");
	        consumerFields.add("transformerlosttestresult real not null,");
	        consumerFields.add("armats real not null, ");
	        consumerFields.add("arrears real not null, ");
	        consumerFields.add("changemeterkilowatthour text not null, ");
	        consumerFields.add("differentialbillrecovery real not null, ");
	        consumerFields.add("seniorcitizendiscount real not null, ");
	        consumerFields.add("scap real not null, ");
	        consumerFields.add("refund real not null, ");
	        consumerFields.add("help real not null, ");
	        consumerFields.add("pilfer real not null, ");
	        consumerFields.add("disco real not null, ");
	        consumerFields.add("incentives real not null, ");
	        consumerFields.add("material real not null, ");
	        consumerFields.add("equiptment real not null, ");
	        consumerFields.add("othersSurcharge real not null, ");
	        consumerFields.add("numarrears integer not null, ");
	        consumerFields.add("kw real not null, ");
	        consumerFields.add("demandcharge real not null");
	        return consumerFields;
	    }

	    public ConsumerDataSource(Context context) {
	        super(new ReadandBillDatabaseHelper(context), context);
	        this.lAllColumn = new String[]{DEMAND_MULTIPLIER, DEMAND_MIN, DEMAND_MAX, METERBRAND, AVEKWH, DATEENERGIZED, TRANSFORMERNUMBER, POLENUMBER, CORELOSS, TRANSFORMERLOSTTESTRESULT, ARMATS, ARREARS, CHANGE_METER_KWHR, DIFFERENTIAL_BILL_RECOVERY, SENIORCITIZEN_DISCOUNT, SCAP, REFUND, HELP, PILFER, NUMBER_OF_ARREARS, KW, DEMANDCHARGE, DISCO, INCENTIVES, MATERIAL, EQUIPTMENT, OTHERSSURCHARGE};
	        this.dbHelper = new ReadandBillDatabaseHelper(context);
	        this.dsUP = new UserProfileDataSource(context);
	        this.allColumns = ArrayManager.concat(this.allColumns, this.lAllColumn);
	    }

	    protected ContentValues consumerContentValues(Consumer consumer) {
	        ContentValues values = super.consumerContentValues(consumer);
	        values.put(DEMAND_MULTIPLIER, Double.valueOf(consumer.getDemandMultiplier(true)));
	        values.put(DEMAND_MIN, Double.valueOf(consumer.getDemandMin()));
	        values.put(DEMAND_MAX, Double.valueOf(consumer.getDemandMax()));
	        values.put(METERBRAND, consumer.getMeterBrand());
	        values.put(AVEKWH, Double.valueOf(consumer.getAveKwh()));
	        values.put(DATEENERGIZED, consumer.getDateEnergized());
	        values.put(TRANSFORMERNUMBER, consumer.getTransformerNumber());
	        values.put(POLENUMBER, consumer.getPoleNumber());
	        values.put(CORELOSS, Double.valueOf(consumer.getCoreLoss()));
	        values.put(TRANSFORMERLOSTTESTRESULT, Double.valueOf(consumer.getTransformerLostTestResult()));
	        values.put(ARMATS, Double.valueOf(consumer.getArMats()));
	        values.put(ARREARS, Double.valueOf(consumer.getArrears()));
	        values.put(CHANGE_METER_KWHR, Double.valueOf(consumer.getChangeMeterKilowatthour()));
	        values.put(DIFFERENTIAL_BILL_RECOVERY, Double.valueOf(consumer.getDifferentialBillRecovery()));
	        values.put(SENIORCITIZEN_DISCOUNT, Double.valueOf(consumer.getSeniorCitizenDiscount()));
	        values.put(SCAP, Double.valueOf(consumer.getScap()));
	        values.put(REFUND, Double.valueOf(consumer.getRefund()));
	        values.put(HELP, Double.valueOf(consumer.getHelp()));
	        values.put(PILFER, Double.valueOf(consumer.getPilfer()));
	        values.put(DISCO, Double.valueOf(consumer.getDisco()));
	        values.put(INCENTIVES, Double.valueOf(consumer.getIncentives()));
	        values.put(MATERIAL, Double.valueOf(consumer.getMaterial()));
	        values.put(EQUIPTMENT, Double.valueOf(consumer.getEquiptment()));
	        values.put(OTHERSSURCHARGE, Double.valueOf(consumer.getOthersSurcharge()));
	        values.put(NUMBER_OF_ARREARS, Integer.valueOf(consumer.getNumberOfArrears()));
	        values.put(KW, Double.valueOf(consumer.getKw()));
	        values.put(DEMANDCHARGE, Double.valueOf(consumer.getDemandCharge()));
	        return values;
	    }

	    public Consumer createConsumer(Consumer consumer) {
	        this.db = this.dbHelper.getWritableDatabase();
	        long insertId = this.db.insertWithOnConflict(com.generic.readandbill.database.ConsumerDataSource.TABLE_CONSUMERS, null, consumerContentValues(consumer), 4);
	        this.db.close();
	        return getConsumer(Long.valueOf(insertId));
	    }

	    public Consumer getConsumer(Long id) {
	        this.db = this.dbHelper.getReadableDatabase();
	        Cursor cursor = this.db.query(com.generic.readandbill.database.ConsumerDataSource.TABLE_CONSUMERS, this.allColumns, "_id=" + id, null, null, null, null);
	        cursor.moveToFirst();
	        Consumer con = cursorToConsumer(cursor);
	        cursor.close();
	        this.db.close();
	        return con;
	    }

	    public List<Consumer> getAllLanecoConsumer() {
	        List<Consumer> consumers = new ArrayList();
	        this.db = this.dbHelper.getWritableDatabase();
	        Cursor cursor = this.db.query(com.generic.readandbill.database.ConsumerDataSource.TABLE_CONSUMERS, this.allColumns, null, null, null, null, com.generic.readandbill.database.ConsumerDataSource.ACCOUNTNUMBER);
	        cursor.moveToFirst();
	        while (!cursor.isAfterLast()) {
	            consumers.add(cursorToConsumer(cursor));
	            cursor.moveToNext();
	        }
	        cursor.close();
	        this.db.close();
	        return consumers;
	    }

	    public List<Consumer> getReadSummary() {
	        this.db = this.dbHelper.getReadableDatabase();
	        List<Consumer> result = new ArrayList();
	        Cursor cursor = this.db.rawQuery("SELECT c.* FROM consumers c INNER JOIN readings r on r.idconsumer = c._id WHERE r.cancelled = 0 and r.route = '" + this.dsUP.getUserProfile().getRoute() + "';", null);
	        cursor.moveToFirst();
	        while (!cursor.isAfterLast()) {
	            result.add(cursorToConsumer(cursor));
	            cursor.moveToNext();
	        }
	        cursor.close();
	        this.db.close();
	        return result;
	    }

	    public List<Consumer> getUnreadSummary() {
	        this.db = this.dbHelper.getReadableDatabase();
	        List<Consumer> result = new ArrayList();
	        Cursor cursor = this.db.rawQuery("SELECT c.* FROM consumers c LEFT JOIN readings r on r.idconsumer = c._id WHERE r._id is null", null);
	        cursor.moveToFirst();
	        while (!cursor.isAfterLast()) {
	            result.add(cursorToConsumer(cursor));
	            cursor.moveToNext();
	        }
	        cursor.close();
	        this.db.close();
	        return result;
	    }
	    
//==========================
//==========================
	  /*  public List<Consumer> getReadSummarySet(String orderType) {
	        this.db = this.dbHelper.getReadableDatabase();
	        List<Consumer> result = new ArrayList();
	        Cursor cursor = this.db.rawQuery("SELECT c.* FROM consumers c INNER JOIN readings r on r.idconsumer = c._id WHERE r.cancelled = 0 and r.route = '" + this.dsUP.getUserProfile().getRoute() + "' order by c."+orderType, null);
	        cursor.moveToFirst();
	        while (!cursor.isAfterLast()) {
	            result.add(cursorToConsumer(cursor));
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
	            result.add(cursorToConsumer(cursor));
	            cursor.moveToNext();
	        }
	        cursor.close();
	        this.db.close();
	        return result;
	    }*/

//==========================
//==========================

	    public boolean existing() {
	        this.db = this.dbHelper.getReadableDatabase();
	        Cursor cursor = this.db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = 'consumers'", null);
	        boolean result = cursor.getCount() > 0;
	        cursor.close();
	        this.db.close();
	        return result;
	    }

	    protected Consumer cursorToConsumer(Cursor cursor) {
	        Consumer consumer = new Consumer();
	        if (cursor.getCount() == 0) {
	            return consumer;
	        }
	        consumer = (Consumer) super.cursorToConsumer(cursor, consumer);
	        consumer.setDemandMultiplier(cursor.getDouble(cursor.getColumnIndex(DEMAND_MULTIPLIER)));
	        consumer.setDemandMin(cursor.getDouble(cursor.getColumnIndex(DEMAND_MIN)));
	        consumer.setDemandMax(cursor.getDouble(cursor.getColumnIndex(DEMAND_MAX)));
	        consumer.setMeterBrand(cursor.getString(cursor.getColumnIndex(METERBRAND)));
	        consumer.setAveKwh((double) cursor.getLong(cursor.getColumnIndex(AVEKWH)));
	        consumer.setDateEnergized(cursor.getString(cursor.getColumnIndex(DATEENERGIZED)));
	        consumer.setTransformerNumber(cursor.getString(cursor.getColumnIndex(TRANSFORMERNUMBER)));
	        consumer.setPoleNumber(cursor.getString(cursor.getColumnIndex(POLENUMBER)));
	        consumer.setCoreLoss(cursor.getDouble(cursor.getColumnIndex(CORELOSS)));
	        consumer.setTransformerLostTestResult(cursor.getDouble(cursor.getColumnIndex(TRANSFORMERLOSTTESTRESULT)));
	        consumer.setArMats(cursor.getDouble(cursor.getColumnIndex(ARMATS)));
	        consumer.setArrears(cursor.getDouble(cursor.getColumnIndex(ARREARS)));
	        consumer.setChangeMeterKilowatthour(cursor.getDouble(cursor.getColumnIndex(CHANGE_METER_KWHR)));
	        consumer.setDifferentialBillRecovery(cursor.getDouble(cursor.getColumnIndex(DIFFERENTIAL_BILL_RECOVERY)));
	        consumer.setSeniorCitizenDiscount(cursor.getDouble(cursor.getColumnIndex(SENIORCITIZEN_DISCOUNT)));
	        consumer.setScap(cursor.getDouble(cursor.getColumnIndex(SCAP)));
	        consumer.setRefund(cursor.getDouble(cursor.getColumnIndex(REFUND)));
	        consumer.setHelp(cursor.getDouble(cursor.getColumnIndex(HELP)));
	        consumer.setPilfer(cursor.getDouble(cursor.getColumnIndex(PILFER)));
	        consumer.setDisco(cursor.getDouble(cursor.getColumnIndex(DISCO)));
	        consumer.setIncentives(cursor.getDouble(cursor.getColumnIndex(INCENTIVES)));
	        consumer.setMaterial(cursor.getDouble(cursor.getColumnIndex(MATERIAL)));
	        consumer.setEquiptment(cursor.getDouble(cursor.getColumnIndex(EQUIPTMENT)));
	        consumer.setOthersSurcharge(cursor.getDouble(cursor.getColumnIndex(OTHERSSURCHARGE)));
	        consumer.setDisco(cursor.getDouble(cursor.getColumnIndex(DISCO)));
	        consumer.setIncentives(cursor.getDouble(cursor.getColumnIndex(INCENTIVES)));
	        consumer.setMaterial(cursor.getDouble(cursor.getColumnIndex(MATERIAL)));
	        consumer.setEquiptment(cursor.getDouble(cursor.getColumnIndex(EQUIPTMENT)));
	        consumer.setOthersSurcharge(cursor.getDouble(cursor.getColumnIndex(OTHERSSURCHARGE)));
	        consumer.setNumberOfArrears(cursor.getInt(cursor.getColumnIndex(NUMBER_OF_ARREARS)));
	        consumer.setKw(cursor.getDouble(cursor.getColumnIndex(KW)));
	        consumer.setDemandCharge((double) cursor.getColumnIndex(DEMANDCHARGE));
	        return consumer;
	    }

	    public void truncate() {
	        this.db = this.dbHelper.getWritableDatabase();
	        this.dbHelper.refreshTable(this.db, com.generic.readandbill.database.ConsumerDataSource.TABLE_CONSUMERS, consumerFields());
	        this.db.close();
	    }
	}

