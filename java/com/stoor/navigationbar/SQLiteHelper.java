package com.stoor.navigationbar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class SQLiteHelper extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "UBSDeveloper.db";

    // User table name
    private static final String TABLE_USER = "User";

    // User Table Columns names
    private static final String COLUMN_MAV_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_PASSWORD = "user_password";
    private static final String COLUMN_USER_GENDER = "user_gender";
    private static final String COLUMN_USER_PHONENUMBER = "user_phonenumber";

    // create table sql query
    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER +
            "("+ COLUMN_MAV_ID + " INTEGER PRIMARY KEY,"
            + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT,"
            + COLUMN_USER_PASSWORD + " TEXT"
            + COLUMN_USER_GENDER + " TEXT"
            + COLUMN_USER_PHONENUMBER + " TEXT"
            + ")";

    // drop table sql query

    // drop table sql query
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public boolean loginSearch(String mavid, String password)
    {
        boolean flag = false;
        SQLiteDatabase database = this.getWritableDatabase();
        String sql = "SELECT * FROM " +TABLE_USER +" WHERE "+COLUMN_MAV_ID+"=?";
        Cursor cursor = database.rawQuery(sql, new String[]{mavid});
        while(cursor.moveToNext())
        {
                String mavIDFromDB = cursor.getString(1);
                String passwordFromDB = cursor.getString(4);
            if(mavid.equals(mavIDFromDB)&&password.equals(passwordFromDB))
            {
                flag = true;
            }
            else
            {
                flag = false;
            }
        }
        database.close();
        cursor.close();
        return flag;
    }
    public void insertUserData(UserPOJO obj)
    {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MAV_ID,obj.getMavID());
        values.put(COLUMN_USER_NAME,obj.getName());
        values.put(COLUMN_USER_EMAIL,obj.getEmail());
        values.put(COLUMN_USER_PASSWORD,obj.getPassword());
        values.put(COLUMN_USER_GENDER,obj.getGender());
        values.put(COLUMN_USER_PHONENUMBER,obj.getPhoneNumber());
        database.insert(TABLE_USER, null, values);
        database.close();


    }

    public void queryData(String sql)
    {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public void insertData(String name,
                           String description,
                           String establishedYear,
                           String officeHours,
                           String officeLocation,
                           byte[] image){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO CLUB VALUES (NULL, ?, ?, ?, ?, ?, ?)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, name);
        statement.bindString(2, description);
        statement.bindString(3, establishedYear);
        statement.bindString(4, officeHours);
        statement.bindString(5, officeLocation);
        statement.bindBlob(6, image);

        statement.executeInsert();
    }

    public void insertDataMarket(String name,
                           String description,
                           double price,
                           byte[] image){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO MARKET VALUES (NULL, ?, ?, ?, ?)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, name);
        statement.bindString(2, description);
        statement.bindDouble(3, price);
        statement.bindBlob(4, image);

        statement.executeInsert();
    }

    public void updateData(String name, String establishedYear, byte[] image, int id) {
        SQLiteDatabase database = getWritableDatabase();

        String sql = "UPDATE CLUB SET name = ?, establishedYear = ?, image = ? WHERE id = ?";
        SQLiteStatement statement = database.compileStatement(sql);

        statement.bindString(1, name);
        statement.bindString(2, establishedYear);
        statement.bindBlob(3, image);
        statement.bindDouble(4, (double)id);

        statement.execute();
        database.close();
    }
    public void updateDataMarket(String name, double price, byte[] image, int id) {
        SQLiteDatabase database = getWritableDatabase();

        String sql = "UPDATE CLUB SET name = ?, price = ?, image = ? WHERE id = ?";
        SQLiteStatement statement = database.compileStatement(sql);

        statement.bindString(1, name);
        statement.bindDouble(2, price);
        statement.bindBlob(3, image);
        statement.bindDouble(4, (double)id);

        statement.execute();
        database.close();
    }

    public  void deleteData(int id) {
        SQLiteDatabase database = getWritableDatabase();

        String sql = "DELETE FROM CLUB WHERE id = ?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, (double)id);

        statement.execute();
        database.close();
    }
    public  void deleteDataMarket(int id) {
        SQLiteDatabase database = getWritableDatabase();

        String sql = "DELETE FROM MARKET WHERE id = ?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, (double)id);

        statement.execute();
        database.close();
    }

    public Cursor getData(String sql)
    {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion)
    {

    }
}
