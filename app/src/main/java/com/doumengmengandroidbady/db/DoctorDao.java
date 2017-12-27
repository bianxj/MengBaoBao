package com.doumengmengandroidbady.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.doumengmengandroidbady.response.Doctor;

import java.util.List;

/**
 * Created by Administrator on 2017/12/27.
 */

public class DoctorDao {

    private final static String TABLE_NAME = "Doctor";

    private final static String DOCTOR_ID = "doctorid";
    private final static String DOCTOR_CODE = "doctorcode";
    private final static String STATE = "state";
    private final static String CERTIFICATE_A = "certificatea";
    private final static String CERTIFICATE_B = "certificateb";
    private final static String DOCTOR_PHONE = "doctorphone";
    private final static String COST = "cost";
    private final static String LOGIN_PWD = "loginpwd";
    private final static String DOCTOR_IMG = "doctorimg";
    private final static String DOCTOR_NAME = "doctorname";
    private final static String POSITIONAL_TITLES = "positionaltitles";
    private final static String HOSPITAL_ID = "hospitalid";
    private final static String SPECIALITY = "speciality";
    private final static String DOCTOR_DESC = "doctordesc";
    private final static String DOCTOR_ORDER = "doctororder";

    public static void createTable(SQLiteDatabase db){
        StringBuilder builder = new StringBuilder("CREATE TABLE IF NOT EXISTS "+TABLE_NAME+"(");
        builder.append(DOCTOR_ID+" varchar,");
        builder.append(DOCTOR_CODE + " varchar,");
        builder.append(STATE+" varchar,");
        builder.append(CERTIFICATE_A+" varchar,");
        builder.append(CERTIFICATE_B+" varchar,");
        builder.append(DOCTOR_PHONE+" varchar,");
        builder.append(COST+" varchar,");
        builder.append(LOGIN_PWD+" varchar,");
        builder.append(DOCTOR_IMG+" varchar,");
        builder.append(DOCTOR_NAME+" varchar,");
        builder.append(POSITIONAL_TITLES+" varchar,");
        builder.append(HOSPITAL_ID+" varchar,");
        builder.append(SPECIALITY+" varchar,");
        builder.append(DOCTOR_ORDER+" varchar,");
        builder.append(DOCTOR_DESC+" varchar");
        builder.append(")");
        db.execSQL(builder.toString());
    }

    public static void updateTable(SQLiteDatabase db , int oldVersion, int newVersion){

    }

    public void saveDoctorList(Context context,List<Doctor> doctors){
        SQLiteDatabase db = DataBaseUtil.openDataBase(context);
        db.beginTransaction();

        for (int i = 0; i < doctors.size() ;i++){
            Doctor doctor = doctors.get(i);
            ContentValues values = new ContentValues();
            values.put(DOCTOR_ID,doctor.getDoctorid());
            values.put(CERTIFICATE_A,doctor.getCertificatea());
            values.put(CERTIFICATE_B,doctor.getCertificateb());
            values.put(DOCTOR_CODE,doctor.getDoctorcode());
            values.put(COST,doctor.getCost());
            values.put(DOCTOR_DESC,doctor.getDoctordesc());
            values.put(DOCTOR_IMG,doctor.getDoctorimg());
            values.put(DOCTOR_ORDER,doctor.getDoctororder());
            values.put(DOCTOR_PHONE,doctor.getDoctorphone());
            values.put(HOSPITAL_ID,doctor.getHospitalid());
            values.put(LOGIN_PWD,doctor.getLoginpwd());
            values.put(DOCTOR_NAME,doctor.getDoctorname());
            values.put(POSITIONAL_TITLES,doctor.getPositionaltitles());
            values.put(SPECIALITY,doctor.getSpeciality());
            values.put(STATE,doctor.getState());
            db.insert(TABLE_NAME,null,values);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        DataBaseUtil.closeDataBase();
    }

}
