package com.doumengmeng.doctor.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.doumengmeng.doctor.response.entity.Nurture;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/12.
 */

public class NurtureDao {
    public final static String TABLE_NAME = "Nurture";

    private final static String ID = "id";
    private final static String NURTURE_TYPE = "nurture_type";
    private final static String NURTURE_TITLE = "nurture_title";
    private final static String NURTURE_DESC = "nurture_desc";
    private final static String AGE = "age";
    private final static String NURTURE_ORDER = "nurture_order";
    private final static String NURTURE_TYPE_ID = "nurture_type_id";

    public static void createTable(SQLiteDatabase db){
        String builder = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" + ID + " varchar," +
                NURTURE_TYPE + " varchar," +
                NURTURE_TITLE + " varchar," +
                NURTURE_DESC + " varchar," +
                AGE + " int," +
                NURTURE_ORDER + " varchar," +
                NURTURE_TYPE_ID + " varchar" +
                ")";
        db.execSQL(builder);
    }

    public static void updateTable(SQLiteDatabase db , int oldVersion, int newVersion){

    }

    public void saveNurtureList(Context context, List<Nurture> nurtures){
        if ( nurtures == null ){
            return;
        }
        SQLiteDatabase db = DataBaseUtil.openDataBase(context);
        db.beginTransaction();

        for (int i = 0; i < nurtures.size() ;i++){
            Nurture nurture = nurtures.get(i);
            ContentValues values = new ContentValues();
            values.put(ID,nurture.getId());
            values.put(NURTURE_TYPE,nurture.getNurtureType());
            values.put(NURTURE_TITLE,nurture.getNurtureTitle());
            values.put(NURTURE_DESC,nurture.getNurtureDesc());
            values.put(AGE,Integer.parseInt(nurture.getAge()));
            values.put(NURTURE_ORDER,nurture.getNurtureOrder());
            values.put(NURTURE_TYPE_ID,nurture.getNurtureTypeId());
            db.insert(TABLE_NAME,null,values);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        DataBaseUtil.closeDataBase();
    }

    public List<Nurture> searchNurtureByAge(Context context , int age){
        List<Nurture> nurtures = new ArrayList<>();
        String builder = "select " +
                ID + "," +
                NURTURE_TYPE + "," +
                NURTURE_TITLE + "," +
                NURTURE_DESC + "," +
                AGE + "," +
                NURTURE_ORDER + "," +
                NURTURE_TYPE_ID +
                " from " + TABLE_NAME +
                " where " + AGE + " between '" + (age-2) + "' and '" + (age+2) +
                "' order by " + NURTURE_TYPE_ID + " , " + AGE + " , " + ID + " DESC" ;
//                "' order by " + ID + " DESC";
//                "' order by " + NURTURE_TYPE_ID + " , " + AGE + " , " + NURTURE_ORDER ;

        SQLiteDatabase db = DataBaseUtil.openDataBase(context);
        Cursor cursor = db.rawQuery(builder,null);
        if ( cursor != null ){
            while ( cursor.moveToNext() ){
                Nurture nurture = new Nurture();
                nurture.setId(cursor.getString(cursor.getColumnIndex(ID)));
                nurture.setAge(cursor.getInt(cursor.getColumnIndex(AGE))+"");
                nurture.setNurtureDesc(cursor.getString(cursor.getColumnIndex(NURTURE_DESC)));
                nurture.setNurtureOrder(cursor.getString(cursor.getColumnIndex(NURTURE_ORDER)));
                nurture.setNurtureTitle(cursor.getString(cursor.getColumnIndex(NURTURE_TITLE)));
                nurture.setNurtureType(cursor.getString(cursor.getColumnIndex(NURTURE_TYPE)));
                nurture.setNurtureTypeId(cursor.getString(cursor.getColumnIndex(NURTURE_TYPE_ID)));
                nurture.setChoose(false);
                nurture.setCustom(false);
                nurtures.add(nurture);
            }
        }
        cursor.close();
        DataBaseUtil.closeDataBase();
        return nurtures;
    }

}
