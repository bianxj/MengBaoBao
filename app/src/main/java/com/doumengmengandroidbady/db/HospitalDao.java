package com.doumengmengandroidbady.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.doumengmengandroidbady.entity.HospitalEntity;
import com.doumengmengandroidbady.response.Hospital;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/27.
 */

public class HospitalDao {

    public final static String TABLE_NAME = "Hospital";

    private final static String AREA = "area";
    private final static String HOSPITAL_ADDRESS = "hospitaladdress";
    private final static String HOSPITAL_DESC = "hospitaldesc";
    private final static String HOSPITAL_ICON = "hospitalicon";
    protected final static String HOSPITAL_ID = "hospitalid";
    private final static String HOSPITAL_MAP = "hospitalmap";
    protected final static String HOSPITAL_NAME = "hospitalname";
    private final static String HOSPITAL_ORDER = "hospitalorder";
    private final static String HOSPITAL_PHONE = "hospitalphone";
    private final static String HOSPITAL_TYPE = "hospitaltype";
    private final static String HOSPITAL_URL = "hospitalurl";
    private final static String PEDIATRICS_DESC = "pediatricsdesc";
    private final static String PROVINCE = "province";
    private final static String SHOW_TAG = "showtag";
    private final static String STATE = "state";

    public static void createTable(SQLiteDatabase db){
        StringBuilder builder = new StringBuilder("CREATE TABLE IF NOT EXISTS "+TABLE_NAME+"(");
        builder.append(AREA+" varchar,");
        builder.append(HOSPITAL_ADDRESS + " varchar,");
        builder.append(STATE+" varchar,");
        builder.append(HOSPITAL_DESC+" varchar,");
        builder.append(HOSPITAL_ICON+" varchar,");
        builder.append(HOSPITAL_MAP+" varchar,");
        builder.append(HOSPITAL_NAME+" varchar,");
        builder.append(HOSPITAL_ORDER+" varchar,");
        builder.append(HOSPITAL_PHONE+" varchar,");
        builder.append(HOSPITAL_TYPE+" varchar,");
        builder.append(HOSPITAL_URL+" varchar,");
        builder.append(HOSPITAL_ID+" varchar,");
        builder.append(PEDIATRICS_DESC+" varchar,");
        builder.append(PROVINCE+" varchar,");
        builder.append(SHOW_TAG+" varchar");
        builder.append(")");
        db.execSQL(builder.toString());
    }

    public static void updateTable(SQLiteDatabase db , int oldVersion, int newVersion){

    }

    public void saveHospitalList(Context context, List<Hospital> hospitals){
        SQLiteDatabase db = DataBaseUtil.openDataBase(context);
        db.beginTransaction();

        for (int i = 0; i < hospitals.size() ;i++){
            Hospital hospital = hospitals.get(i);
            ContentValues values = new ContentValues();
            values.put(AREA,hospital.getArea());
            values.put(HOSPITAL_ADDRESS,hospital.getHospitaladdress());
            values.put(STATE,hospital.getState());
            values.put(HOSPITAL_DESC,hospital.getHospitaldesc());
            values.put(HOSPITAL_ICON,hospital.getHospitalicon());
            values.put(HOSPITAL_MAP,hospital.getHospitalmap());
            values.put(HOSPITAL_NAME,hospital.getHospitalname());
            values.put(HOSPITAL_ORDER,hospital.getHospitalorder());
            values.put(HOSPITAL_PHONE,hospital.getHospitalphone());
            values.put(HOSPITAL_ID,hospital.getHospitalid());
            values.put(HOSPITAL_TYPE,hospital.getHospitaltype());
            values.put(HOSPITAL_URL,hospital.getHospitalurl());
            values.put(PEDIATRICS_DESC,hospital.getPediatricsdesc());
            values.put(PROVINCE,hospital.getProvince());
            values.put(SHOW_TAG,hospital.getShowtag());
            db.insert(TABLE_NAME,null,values);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        DataBaseUtil.closeDataBase();
    }

    public List<HospitalEntity> searchHospitalListByName(Context context,String name){
        List<HospitalEntity> entities = new ArrayList<>();

        StringBuilder builder = new StringBuilder();
        builder.append("select ");
        builder.append(HOSPITAL_ID+",");
        builder.append(HOSPITAL_ICON+",");
        builder.append(HOSPITAL_NAME+",");
        builder.append(HOSPITAL_ADDRESS);
        builder.append(" from " + TABLE_NAME);
        builder.append(" where " + HOSPITAL_NAME + " like '%"+name+"%'");
        builder.append(" order by " + HOSPITAL_ORDER);

        SQLiteDatabase db = DataBaseUtil.openDataBase(context);
        Cursor cursor = db.rawQuery(builder.toString(),null);

        if ( cursor != null ){
            while(cursor.moveToNext()){
                HospitalEntity entity = new HospitalEntity();
                entity.setHospitalid(cursor.getString(cursor.getColumnIndex(HOSPITAL_ID)));
                entity.setHospitaladdress(cursor.getString(cursor.getColumnIndex(HOSPITAL_ADDRESS)));
                entity.setHospitalicon(cursor.getString(cursor.getColumnIndex(HOSPITAL_ICON)));
                entity.setHospitalname(cursor.getString(cursor.getColumnIndex(HOSPITAL_NAME)));
                entities.add(entity);
            }
        }
        DataBaseUtil.closeDataBase();
        return entities;
    }

    public List<HospitalEntity> searchHospitalList(Context context , int offset , int limit){
        List<HospitalEntity> entities = new ArrayList<>();

        StringBuilder builder = new StringBuilder();
        builder.append("select ");
        builder.append(HOSPITAL_ID+",");
        builder.append(HOSPITAL_ICON+",");
        builder.append(HOSPITAL_NAME+",");
        builder.append(HOSPITAL_ADDRESS);
        builder.append(" from " + TABLE_NAME);
        builder.append(" order by " + HOSPITAL_ORDER);
        builder.append(" limit " + limit);
        builder.append(" offset " + offset*limit);

        SQLiteDatabase db = DataBaseUtil.openDataBase(context);
        Cursor cursor = db.rawQuery(builder.toString(),null);

        if ( cursor != null ){
            while(cursor.moveToNext()){
                HospitalEntity entity = new HospitalEntity();
                entity.setHospitalid(cursor.getString(cursor.getColumnIndex(HOSPITAL_ID)));
                entity.setHospitaladdress(cursor.getString(cursor.getColumnIndex(HOSPITAL_ADDRESS)));
                entity.setHospitalicon(cursor.getString(cursor.getColumnIndex(HOSPITAL_ICON)));
                entity.setHospitalname(cursor.getString(cursor.getColumnIndex(HOSPITAL_NAME)));
                entities.add(entity);
            }
        }
        DataBaseUtil.closeDataBase();
        return entities;
    }

    public List<HospitalEntity> searchHospitalList(Context context){
        List<HospitalEntity> entities = new ArrayList<>();

        StringBuilder builder = new StringBuilder();
        builder.append("select ");
        builder.append(HOSPITAL_ID+",");
        builder.append(HOSPITAL_ICON+",");
        builder.append(HOSPITAL_NAME+",");
        builder.append(HOSPITAL_ADDRESS);
        builder.append(" from " + TABLE_NAME);
        builder.append(" order by " + HOSPITAL_ORDER);

        SQLiteDatabase db = DataBaseUtil.openDataBase(context);
        Cursor cursor = db.rawQuery(builder.toString(),null);

        if ( cursor != null ){
            while(cursor.moveToNext()){
                HospitalEntity entity = new HospitalEntity();
                entity.setHospitalid(cursor.getString(cursor.getColumnIndex(HOSPITAL_ID)));
                entity.setHospitaladdress(cursor.getString(cursor.getColumnIndex(HOSPITAL_ADDRESS)));
                entity.setHospitalicon(cursor.getString(cursor.getColumnIndex(HOSPITAL_ICON)));
                entity.setHospitalname(cursor.getString(cursor.getColumnIndex(HOSPITAL_NAME)));
                entities.add(entity);
            }
        }
        DataBaseUtil.closeDataBase();
        return entities;
    }

    public Hospital searchHospitalById(Context context,String hospitalId){
        Hospital hospital = null;
        String sql = "select * from " + TABLE_NAME + " where " + HOSPITAL_ID + " = " + hospitalId;
        SQLiteDatabase db = DataBaseUtil.openDataBase(context);
        Cursor cursor = db.rawQuery(sql,null);
        if ( cursor.moveToNext() ){
            hospital = new Hospital();
            hospital.setArea(cursor.getString(cursor.getColumnIndex(AREA)));
            hospital.setHospitaladdress(cursor.getString(cursor.getColumnIndex(HOSPITAL_ADDRESS)));
            hospital.setHospitaldesc(cursor.getString(cursor.getColumnIndex(HOSPITAL_DESC)));
            hospital.setHospitalicon(cursor.getString(cursor.getColumnIndex(HOSPITAL_ICON)));
            hospital.setHospitalid(cursor.getString(cursor.getColumnIndex(HOSPITAL_ID)));
            hospital.setHospitalmap(cursor.getString(cursor.getColumnIndex(HOSPITAL_MAP)));
            hospital.setHospitalname(cursor.getString(cursor.getColumnIndex(HOSPITAL_NAME)));
            hospital.setHospitalorder(cursor.getString(cursor.getColumnIndex(HOSPITAL_ORDER)));
            hospital.setHospitalphone(cursor.getString(cursor.getColumnIndex(HOSPITAL_PHONE)));
            hospital.setHospitaltype(cursor.getString(cursor.getColumnIndex(HOSPITAL_TYPE)));
            hospital.setHospitalurl(cursor.getString(cursor.getColumnIndex(HOSPITAL_URL)));
            hospital.setPediatricsdesc(cursor.getString(cursor.getColumnIndex(PEDIATRICS_DESC)));
            hospital.setProvince(cursor.getString(cursor.getColumnIndex(PROVINCE)));
            hospital.setShowtag(cursor.getString(cursor.getColumnIndex(SHOW_TAG)));
            hospital.setState(cursor.getString(cursor.getColumnIndex(STATE)));
        }
        DataBaseUtil.closeDataBase();
        return hospital;
    }

}