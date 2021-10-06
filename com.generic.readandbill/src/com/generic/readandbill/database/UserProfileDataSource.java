package com.generic.readandbill.database;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;
public class UserProfileDataSource {
	    public static final String ID = "_id";
	    public static final String NAME = "name";
	    
	    public static final String INITIAL_READING_DATE = "initialreadingdate";
	    public static final String READING_DATE = "readingDate";
	    public static final String ROUTE = "route";
	    
	    public static final String TABLE_USER_PROFILE = "userprofile";
	    protected String[] allColumns;
	    protected SQLiteDatabase db;
	    protected ReadandBillDatabaseHelper dbHelper;

	    public static List<String> userProfileFields() {
	        List<String> userProfileFields = new ArrayList();
	        userProfileFields.add("_id integer primary key autoincrement, ");
	        userProfileFields.add("name text not null, ");
	        
	        userProfileFields.add("route text not null, ");
	        userProfileFields.add("readingDate text not null, ");
	        userProfileFields.add("initialreadingdate text not null");
	        return userProfileFields;
	    }

	    public UserProfileDataSource(Context context) {
	        this.allColumns = new String[]{ID, NAME, ROUTE, READING_DATE, INITIAL_READING_DATE};
	        this.dbHelper = new ReadandBillDatabaseHelper(context);
	    }

	    public UserProfileDataSource(ReadandBillDatabaseHelper dbHelper, Context context) {
	        this.allColumns = new String[]{ID, NAME, ROUTE, READING_DATE, INITIAL_READING_DATE};
	        this.dbHelper = dbHelper;
	        if (this.dbHelper == null) {
	            dbHelper = new ReadandBillDatabaseHelper(context);
	        }
	    }

	    protected ContentValues upValues(UserProfile userProfile) {
	        ContentValues values = new ContentValues();
	        values.put(NAME, userProfile.getName());
	        
	        values.put(ROUTE, userProfile.getRoute());
	        values.put(READING_DATE, userProfile.getReadingDate());
	        values.put(INITIAL_READING_DATE, userProfile.getInitialReadingDate());
	        return values;
	    }

	    public UserProfile createUserProfile(UserProfile userProfile) {
	        this.db = this.dbHelper.getWritableDatabase();
	        Cursor cursor = this.db.query(TABLE_USER_PROFILE, this.allColumns, "_id=" + this.db.insert(TABLE_USER_PROFILE, null, upValues(userProfile)), null, null, null, null);
	        cursor.moveToFirst();
	        UserProfile newUserProfile = cursorToUserProfile(cursor, new UserProfile());
	        cursor.close();
	        this.db.close();
	        return newUserProfile;
	    }

	    public UserProfile getUserProfile() {
	        this.db = this.dbHelper.getReadableDatabase();
	        Cursor cursor = this.db.query(TABLE_USER_PROFILE, this.allColumns, null, null, null, null, null);
	        cursor.moveToFirst();
	        UserProfile userProfile = cursorToUserProfile(cursor, new UserProfile());
	        this.db.close();
	        return userProfile;
	    }

	    protected UserProfile cursorToUserProfile(Cursor cursor, UserProfile userProfile) {
	        if (cursor.getCount() > 0) {
	            userProfile.setId(cursor.getLong(cursor.getColumnIndex(ID)));
	            userProfile.setName(cursor.getString(cursor.getColumnIndex(NAME)));
	            
	            userProfile.setRoute(cursor.getString(cursor.getColumnIndex(ROUTE)));
		        userProfile.setReadingDate(cursor.getString(cursor.getColumnIndex(READING_DATE)));
		        userProfile.setInitialReadingDate(cursor.getString(cursor.getColumnIndex(INITIAL_READING_DATE)));
	        }
	        return userProfile;
	    }

	    public void truncate() {
	        this.db = this.dbHelper.getWritableDatabase();
	        this.dbHelper.refreshTable(this.db, TABLE_USER_PROFILE, userProfileFields());
	        this.db.close();
	    }
}
