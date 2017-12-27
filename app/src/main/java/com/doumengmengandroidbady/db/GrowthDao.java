package com.doumengmengandroidbady.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.doumengmengandroidbady.response.Growth;

import java.util.List;

/**
 * Created by Administrator on 2017/12/27.
 */

public class GrowthDao {

    private final static String TABLE_NAME = "Growth";

    private final static String AGE = "age";
    private final static String OBSERVE_CONTENT = "observecontent";
    private final static String OBSERVE_ID = "observeid";
    private final static String OBSERVE_ORDER = "observeorder";

    public static void createTable(SQLiteDatabase db){
        StringBuilder builder = new StringBuilder("CREATE TABLE IF NOT EXISTS "+TABLE_NAME+"(");
        builder.append(AGE+" varchar,");
        builder.append(OBSERVE_CONTENT + " varchar,");
        builder.append(OBSERVE_ID+" varchar,");
        builder.append(OBSERVE_ORDER+" varchar");
        builder.append(")");
        db.execSQL(builder.toString());
    }

    public static void updateTable(SQLiteDatabase db , int oldVersion, int newVersion){

    }

    public void saveGrowthList(Context context, List<Growth> growths){
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

}
