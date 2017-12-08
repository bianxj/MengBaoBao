package com.doumengmengandroidbady.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 作者:边贤君
 * 描述:
 * 创建日期:2017/12/2 19:49
 */

public class DataBaseUtil extends SQLiteOpenHelper {

    private final static String DB_NAME = "";
    private final static int DB_VERSION = 1;
    private static DataBaseUtil dbUtil;
    private static SQLiteDatabase db;
    private static Integer openCout = 0;


    private DataBaseUtil(Context context) {
        super(context.getApplicationContext(), DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static SQLiteDatabase openDataBase(Context context){
        synchronized (openCout){
            if ( openCout < 0 ){
                openCout = 1;
            } else {
                openCout++;
            }
            if ( null == dbUtil ){
                dbUtil = new DataBaseUtil(context.getApplicationContext());
            }
            if ( null == db || !db.isOpen() ) {
                db = dbUtil.getWritableDatabase();
            }
            return db;
        }
    }

    public static synchronized void closeDataBase(){
        synchronized (openCout) {
            openCout--;
            if ( 0 == openCout && null != db && db.isOpen() ) {
                db.close();
                db = null;
            }
        }
    }

}
