package com.example.group19project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyDBHandler extends SQLiteOpenHelper{
    private static final String TABLE_NAME = "userAccounts";
    private static final String COLUMN_STUDENT_FIRST_NAME = "firstName";
    private static final String COLUMN_STUDENT_LAST_NAME = "lastName";
    private static final String COLUMN_USER_NAME = "userName";
    private static final String COLUMN_USER_PASSWORD = "password";
    private static final String COLUMN_USER_ACCOUNT_TYPE = "accountType";
    private static final int DATABASE_VERSION = 1;
    static String DATABASE_NAME="UserDataBase.db";

    //table 2
    private static final String TABLE_NAME_v2 = "courseSelection";
    private static final String COLUMN_COURSE_CODE = "courseCode";
    private static final String COLUMN_COURSE_NAME = "courseName";
    static String DATABASE_NAME_v2="courseDataBase.db";


    public MyDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION );
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_table_cmd = "CREATE TABLE " + TABLE_NAME +
                "("  +
                COLUMN_STUDENT_FIRST_NAME + " TEXT, " +
                COLUMN_STUDENT_LAST_NAME + " TEXT, "
                + COLUMN_USER_NAME + " TEXT, "
                + COLUMN_USER_PASSWORD + " TEXT, "
                + COLUMN_USER_ACCOUNT_TYPE + " TEXT "
                + ")";

        db.execSQL(create_table_cmd);

        //table2
        String create_table_cmd_v2 = "CREATE TABLE " + TABLE_NAME_v2 +
                "("  +
                COLUMN_COURSE_CODE + " TEXT, " +
                COLUMN_COURSE_NAME + " TEXT "
                + ")";
        db.execSQL(create_table_cmd_v2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public Cursor getData(Information user) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        return db.rawQuery(query, null); // returns "cursor" all products from the table
    }
    public void addUser( Information info){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_STUDENT_FIRST_NAME, info.getFirstName());
        values.put(COLUMN_STUDENT_LAST_NAME, info.getLastName());
        values.put(COLUMN_USER_NAME, info.getUserName());
        values.put(COLUMN_USER_PASSWORD, info.getPassword());
        values.put(COLUMN_USER_ACCOUNT_TYPE, info.getAccountType());
        db.insert(TABLE_NAME, null, values);
        db.close();


    }
    public Boolean userExists(String userName){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM userAccounts WHERE userName= ?",new String[]{userName});
        if(cursor.getCount()>0){
            cursor.close();
            return true;
        }
        return false;
    }
    public ArrayList<String> findUserDetails(String user){
        ArrayList<String> findList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE userName LIKE ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{user});

        if (cursor.getCount() == 0) {
            // do nothing
        } else {
            while (cursor.moveToNext()) {
                findList.add(cursor.getString(1));
                findList.add(cursor.getString(5));
            }

        }
        cursor.close();
        db.close();
        return findList;
    }

    public Boolean checkUserNameAndPassword(String userName, String passWord){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM userAccounts WHERE userName= ? AND password=?",new String[]{userName, passWord});
        if(cursor.getCount()>0){
            return true;
        }
        return false;
    }


}