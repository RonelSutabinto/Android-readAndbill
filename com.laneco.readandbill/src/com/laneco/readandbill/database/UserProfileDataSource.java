package com.laneco.readandbill.database;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import com.androidapp.mytools.objectmanager.ArrayManager;
import java.util.List;
public class UserProfileDataSource extends com.generic.readandbill.database.UserProfileDataSource{
	    //public static final String INITIAL_READING_DATE = "initialreadingdate";
	    //public static final String READING_DATE = "readingDate";
	    //public static final String ROUTE = "route";
	    private ReadandBillDatabaseHelper dbHelper;
	    private String[] lAllColumns;

	    public static List<String> userProfileFields() {
	        List<String> userProfileFields = com.generic.readandbill.database.UserProfileDataSource.userProfileFields();
	        //userProfileFields.set(userProfileFields.size() - 1, ((String) userProfileFields.get(userProfileFields.size() - 1)) + ", ");
	        //userProfileFields.add("route text not null, ");
	        //userProfileFields.add("readingDate text not null, ");
	        //userProfileFields.add("initialreadingdate text not null");
	        return userProfileFields;
	    }

	    public UserProfileDataSource(Context context) {
	        super(new ReadandBillDatabaseHelper(context), context);
	        //this.lAllColumns = new String[]{ROUTE, READING_DATE, INITIAL_READING_DATE};
	        this.dbHelper = new ReadandBillDatabaseHelper(context);
	        //this.allColumns = ArrayManager.concat(this.allColumns, this.lAllColumns);
	    }

	    protected ContentValues upValues(UserProfile userProfile) {
	        ContentValues values = super.upValues(userProfile);
	        //values.put(ROUTE, userProfile.getRoute());
	        //values.put(READING_DATE, userProfile.getReadingDate());
	        //values.put(INITIAL_READING_DATE, userProfile.getInitialReadingDate());
	        return values;
	    }

	    public UserProfile createUserProfile(UserProfile userProfile) {
	        this.db = this.dbHelper.getWritableDatabase();
	        this.db.insert(com.generic.readandbill.database.UserProfileDataSource.TABLE_USER_PROFILE, null, upValues(userProfile));
	        this.db.close();
	        return getUserProfile();
	    }

	    protected UserProfile cursorToUserProfile(Cursor cursor) {
	        UserProfile userProfile = new UserProfile();
	        if (cursor.getCount() == 0) {
	            return userProfile;
	        }
	        userProfile = (UserProfile) super.cursorToUserProfile(cursor, userProfile);
	        //userProfile.setRoute(cursor.getString(cursor.getColumnIndex(ROUTE)));
	        //userProfile.setReadingDate(cursor.getString(cursor.getColumnIndex(READING_DATE)));
	        //userProfile.setInitialReadingDate(cursor.getString(cursor.getColumnIndex(INITIAL_READING_DATE)));
	        return userProfile;
	    }

	    public UserProfile getUserProfile() {
	        this.db = this.dbHelper.getReadableDatabase();
	        Cursor cursor = this.db.query(com.generic.readandbill.database.UserProfileDataSource.TABLE_USER_PROFILE, this.allColumns, null, null, null, null, null);
	        cursor.moveToFirst();
	        UserProfile userProfile = cursorToUserProfile(cursor);
	        this.db.close();
	        return userProfile;
	    }

	    public void truncate() {
	        this.db = this.dbHelper.getWritableDatabase();
	        this.dbHelper.refreshTable(this.db, com.generic.readandbill.database.UserProfileDataSource.TABLE_USER_PROFILE, userProfileFields());
	        this.db.close();
	    }
}
