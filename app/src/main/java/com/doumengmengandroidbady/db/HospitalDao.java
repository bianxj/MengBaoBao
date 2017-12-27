package com.doumengmengandroidbady.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.doumengmengandroidbady.response.Hospital;

import java.util.List;

/**
 * Created by Administrator on 2017/12/27.
 */

public class HospitalDao {

    private final static String TABLE_NAME = "Hospital";

    private final static String AREA = "area";
    private final static String HOSPITAL_ADDRESS = "hospitaladdress";
    private final static String HOSPITAL_DESC = "hospitaldesc";
    private final static String HOSPITAL_ICON = "hospitalicon";
    private final static String HOSPITAL_ID = "hospitalid";
    private final static String HOSPITAL_MAP = "hospitalmap";
    private final static String HOSPITAL_NAME = "hospitalname";
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

}
