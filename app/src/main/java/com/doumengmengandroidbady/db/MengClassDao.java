package com.doumengmengandroidbady.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.doumengmengandroidbady.response.MengClass;

import java.util.List;

/**
 * Created by Administrator on 2017/12/27.
 */

public class MengClassDao {

    private final static String TABLE_NAME = "MengClass";

    private final static String CLASS_CONTEXT = "classcontext";
    private final static String CLASS_ID = "classid";
    private final static String CLASS_TIME = "classtime";
    private final static String CLASS_TITLE = "classtitle";

    public static void createTable(SQLiteDatabase db){
        StringBuilder builder = new StringBuilder("CREATE TABLE IF NOT EXISTS "+TABLE_NAME+"(");
        builder.append(CLASS_CONTEXT+" varchar,");
        builder.append(CLASS_ID + " varchar,");
        builder.append(CLASS_TIME+" varchar,");
        builder.append(CLASS_TITLE+" varchar");
        builder.append(")");
        db.execSQL(builder.toString());
    }

    public static void updateTable(SQLiteDatabase db , int oldVersion, int newVersion){

    }

    public void saveMengClassList(Context context, List<MengClass> mengClasses){
        SQLiteDatabase db = DataBaseUtil.openDataBase(context);
        db.beginTransaction();
        for (int i = 0; i < mengClasses.size() ;i++){
            MengClass mengClass = mengClasses.get(i);
            ContentValues values = new ContentValues();
            values.put(CLASS_CONTEXT,mengClass.getClasscontext());
            values.put(CLASS_ID,mengClass.getClassid());
            values.put(CLASS_TIME,mengClass.getClasstime());
            values.put(CLASS_TITLE,mengClass.getClasstitle());
            db.insert(TABLE_NAME,null,values);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        DataBaseUtil.closeDataBase();
    }

}
