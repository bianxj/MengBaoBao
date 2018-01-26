package com.doumengmengandroidbady.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.doumengmengandroidbady.response.Growth;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/27.
 */

public class GrowthDao {

    public final static String TABLE_NAME = "Growth";

    private final static String AGE = "age";
    private final static String OBSERVE_CONTENT = "observecontent";
    private final static String OBSERVE_ID = "observeid";
    private final static String OBSERVE_ORDER = "observeorder";

    public static void createTable(SQLiteDatabase db){
        String builder = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" + AGE + " varchar," +
                OBSERVE_CONTENT + " varchar," +
                OBSERVE_ID + " varchar," +
                OBSERVE_ORDER + " varchar" +
                ")";
        db.execSQL(builder);
    }

    public static void updateTable(SQLiteDatabase db , int oldVersion, int newVersion){

    }

    public void saveGrowthList(Context context, List<Growth> growths){
        if ( growths == null ){
            return;
        }
        SQLiteDatabase db = DataBaseUtil.openDataBase(context);
        db.beginTransaction();
        for (int i = 0; i < growths.size() ;i++){
            Growth growth = growths.get(i);
            ContentValues values = new ContentValues();
            values.put(AGE,growth.getAge());
            values.put(OBSERVE_CONTENT,growth.getObservecontent());
            values.put(OBSERVE_ID,growth.getObserveid());
            values.put(OBSERVE_ORDER,growth.getObserveorder());
            db.insert(TABLE_NAME,null,values);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        DataBaseUtil.closeDataBase();
    }

    public List<String> searchGrowth(Context context){
        List<String> contents = new ArrayList<>();
        SQLiteDatabase db = DataBaseUtil.openDataBase(context);
        String sql = "select "+OBSERVE_CONTENT+" from " + TABLE_NAME + " order by " + OBSERVE_ORDER;
        Cursor cursor = db.rawQuery(sql,null);
        while ( cursor.moveToNext() ){
            contents.add(cursor.getString(0));
        }
        cursor.close();
        DataBaseUtil.closeDataBase();
        return contents;
    }

}
