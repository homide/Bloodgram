package com.project.bloodgram.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    Context context;
    public static final String DATABASE_NAME = "allData";
    public static final String LOGIN_DATABASE = "loginDetails";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table IF NOT EXISTS " + LOGIN_DATABASE + "(email TEXT NOT NULL, password TEXT NOT NULL, loggedIn TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + LOGIN_DATABASE);
        db.execSQL("create table IF NOT EXISTS " + LOGIN_DATABASE + "(email TEXT NOT NULL, password TEXT NOT NULL, loggedIn TEXT NOT NULL)");
    }

    public Cursor getData(String tableName){
        try{
            SQLiteDatabase db = getReadableDatabase();
            Cursor res = db.rawQuery("SELECT * FROM " + tableName,null);
            return res;
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

    public void insertLoginDetails(String email, String password, String loggedIn){
        try{
            SQLiteDatabase db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("email",email);
            contentValues.put("password",password);
            contentValues.put("loggedIn",loggedIn);
            db.insert(LOGIN_DATABASE,null,contentValues);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void deleteLoginDetails(){
        try{
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL("DELETE FROM " + LOGIN_DATABASE);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public boolean checkLoginDetails(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor res = db.rawQuery("SELECT loggedIn FROM " + LOGIN_DATABASE,null);
        if (res.getCount() > 0){
            while (res.moveToNext()){
                String val = res.getString(0);
                if (val.equals("true")){
                    return true;
                }else{
                    return false;
                }
            }
        }else{
            return false;
        }

        return false;
    }

    public String getEmail(){
        try{
            SQLiteDatabase db = getReadableDatabase();
            Cursor cur = db.rawQuery("SELECT email FROM " + LOGIN_DATABASE, null);
            while (cur.moveToNext()){
                return cur.getString(0);
            }
            return "";
        }catch (Exception e){
            System.out.println(e);
            return "";
        }
    }

    public String getPass(){
        try{
            SQLiteDatabase db = getReadableDatabase();
            Cursor cur = db.rawQuery("SELECT password FROM " + LOGIN_DATABASE, null);
            while (cur.moveToNext()){
                return cur.getString(0);
            }
            return "";
        }catch (Exception e){
            System.out.println(e);
            return "";
        }
    }
}