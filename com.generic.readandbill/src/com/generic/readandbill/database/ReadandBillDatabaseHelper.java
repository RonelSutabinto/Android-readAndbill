package com.generic.readandbill.database;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.List;

public class ReadandBillDatabaseHelper extends SQLiteOpenHelper{
	  private static final String DATABASE_NAME = "readandbill.db";
	    private static final int DATABASE_VERSION = 8;

	    protected String databaseCreate() {
	        return ((("" + createTable(ConsumerDataSource.TABLE_CONSUMERS, ConsumerDataSource.consumerFields())) + createTable(RateDataSource.TABLE_RATES, RateDataSource.ratesFields())) + createTable(ReadingDataSource.TABLE_READINGS, ReadingDataSource.readingFields())) + createTable(UserProfileDataSource.TABLE_USER_PROFILE, UserProfileDataSource.userProfileFields());
	    }

	    protected String databaseDrop() {
	        return ((("" + dropTable(ConsumerDataSource.TABLE_CONSUMERS)) + dropTable(RateDataSource.TABLE_RATES)) + dropTable(ReadingDataSource.TABLE_READINGS)) + dropTable(UserProfileDataSource.TABLE_USER_PROFILE);
	    }

	    public ReadandBillDatabaseHelper(Context context) {
	        super(context, DATABASE_NAME, null, DATABASE_VERSION);
	    }

	    public ReadandBillDatabaseHelper(Context context, String databaseName, int databaseVersion) {
	        super(context, databaseName, null, databaseVersion);
	    }

	    public void onCreate(SQLiteDatabase db) {
	        db.execSQL(databaseCreate() + ");");
	    }

	    public void refreshDatabase(SQLiteDatabase db) {
	        db.execSQL(databaseDrop());
	        onCreate(db);
	    }

	    protected String createTable(String tableName, List<String> tableFields) {
	        String result = "create table " + tableName + "(";
	        for (String string : tableFields) {
	            result = result + string;
	        }
	        return result + "); ";
	    }

	    public void refreshTable(SQLiteDatabase db, String tableName, List<String> tableFields) {
	        db.execSQL(dropTable(tableName));
	        db.execSQL(createTable(tableName, tableFields));
	    }

	    protected String dropTable(String tableName) {
	        return "drop table if exists " + tableName + "; ";
	    }

	    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
	        refreshDatabase(arg0);
	    }
}
