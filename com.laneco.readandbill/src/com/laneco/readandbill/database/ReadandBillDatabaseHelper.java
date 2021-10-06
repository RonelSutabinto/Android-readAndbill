package com.laneco.readandbill.database;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.generic.readandbill.database.ConsumerDataSource;
import com.generic.readandbill.database.RateDataSource;
import com.generic.readandbill.database.ReadingDataSource;
import com.generic.readandbill.database.UserProfileDataSource;
public class ReadandBillDatabaseHelper extends com.generic.readandbill.database.ReadandBillDatabaseHelper{
	private static final String DATABASE_NAME = "readandbill.db";
    private static final int DATABASE_VERSION = 8;

    public ReadandBillDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTable(ConsumerDataSource.TABLE_CONSUMERS, ConsumerDataSource.consumerFields()));
        db.execSQL(createTable(ReadingDataSource.TABLE_READINGS, ReadingDataSource.readingFields()));
        db.execSQL(createTable(RateDataSource.TABLE_RATES, RateDataSource.ratesFields()));
        db.execSQL(createTable(UserProfileDataSource.TABLE_USER_PROFILE, UserProfileDataSource.userProfileFields()));
    }

    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        refreshTable(arg0, ConsumerDataSource.TABLE_CONSUMERS, ConsumerDataSource.consumerFields());
        refreshTable(arg0, ReadingDataSource.TABLE_READINGS, ReadingDataSource.readingFields());
        refreshTable(arg0, UserProfileDataSource.TABLE_USER_PROFILE, UserProfileDataSource.userProfileFields());
        refreshTable(arg0, RateDataSource.TABLE_RATES, RateDataSource.ratesFields());
    }
}
