package com.doumengmeng.doctor.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.doumengmeng.doctor.response.entity.Hospital;

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
    final static String HOSPITAL_ID = "hospitalid";
    private final static String HOSPITAL_MAP = "hospitalmap";
    final static String HOSPITAL_NAME = "hospitalname";
    private final static String HOSPITAL_ORDER = "hospitalorder";
    private final static String HOSPITAL_PHONE = "hospitalphone";
    private final static String HOSPITAL_TYPE = "hospitaltype";
    private final static String HOSPITAL_URL = "hospitalurl";
    private final static String PEDIATRICS_DESC = "pediatricsdesc";
    final static String PROVINCE = "province";
    private final static String SHOW_TAG = "showtag";
    private final static String STATE = "state";

    public static void createTable(SQLiteDatabase db){
        String builder = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" + AREA + " varchar," +
                HOSPITAL_ADDRESS + " varchar," +
                STATE + " varchar," +
                HOSPITAL_DESC + " varchar," +
                HOSPITAL_ICON + " varchar," +
                HOSPITAL_MAP + " varchar," +
                HOSPITAL_NAME + " varchar," +
                HOSPITAL_ORDER + " varchar," +
                HOSPITAL_PHONE + " varchar," +
                HOSPITAL_TYPE + " varchar," +
                HOSPITAL_URL + " varchar," +
                HOSPITAL_ID + " varchar," +
                PEDIATRICS_DESC + " varchar," +
                PROVINCE + " varchar," +
                SHOW_TAG + " varchar" +
                ")";
        db.execSQL(builder);
    }

    public static void updateTable(SQLiteDatabase db , int oldVersion, int newVersion){

    }

    public void saveHospitalList(Context context, List<Hospital> hospitals){
        if ( hospitals == null ){
            return;
        }
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

    public boolean hasHospitalInCity(Context context, String city){
        boolean has = false;

        String builder = "select " +
                HOSPITAL_ID + "," +
                HOSPITAL_ICON + "," +
                HOSPITAL_NAME + "," +
                HOSPITAL_ADDRESS +
                " from " + TABLE_NAME +
                " where " + PROVINCE + " = '" + city + "' " +
                " order by " + HOSPITAL_ORDER;

        SQLiteDatabase db = DataBaseUtil.openDataBase(context);
        Cursor cursor = db.rawQuery(builder,null);

        if ( cursor.moveToNext() ){
            has = true;
        }

        cursor.close();
        DataBaseUtil.closeDataBase();
        return has;
    }

    public List<Hospital> searchHospitalListByCity(Context context,String city){
        List<Hospital> hospitals = new ArrayList<>();
        String builder = "select " +
                HOSPITAL_ID + "," +
                HOSPITAL_ICON + "," +
                HOSPITAL_NAME + "," +
                HOSPITAL_ADDRESS +
                " from " + TABLE_NAME +
                " where " + PROVINCE + " = '" + city + "' " +
                " order by " + HOSPITAL_ORDER;

        SQLiteDatabase db = DataBaseUtil.openDataBase(context);
        Cursor cursor = db.rawQuery(builder,null);
        if ( cursor != null ){
            while ( cursor.moveToNext() ){
                Hospital hospital = new Hospital();
                hospital.setHospitalid(cursor.getString(cursor.getColumnIndex(HOSPITAL_ID)));
                hospital.setHospitaladdress(cursor.getString(cursor.getColumnIndex(HOSPITAL_ADDRESS)));
                hospital.setHospitalicon(cursor.getString(cursor.getColumnIndex(HOSPITAL_ICON)));
                hospital.setHospitalname(cursor.getString(cursor.getColumnIndex(HOSPITAL_NAME)));
                hospitals.add(hospital);
            }
        }
        cursor.close();
        DataBaseUtil.closeDataBase();
        return hospitals;
    }

    public String searchHospitalNameById(Context context,String hospitalId){
        String hospitalName = null;
        String builder = "select " +
                HOSPITAL_NAME +
                " from " + TABLE_NAME +
                " where " + HOSPITAL_ID + " = '" + hospitalId + "' ";
        SQLiteDatabase db = DataBaseUtil.openDataBase(context);
        Cursor cursor = db.rawQuery(builder,null);
        if ( cursor != null && cursor.moveToNext() ){
            hospitalName = cursor.getString(cursor.getColumnIndex(HOSPITAL_NAME));
        }
        cursor.close();
        DataBaseUtil.closeDataBase();
        return hospitalName;
    }

}
