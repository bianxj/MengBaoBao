package com.doumengmengandroidbady.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.doumengmengandroidbady.entity.DoctorEntity;
import com.doumengmengandroidbady.response.Doctor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/27.
 */

public class DoctorDao {

    public final static String TABLE_NAME = "Doctor";

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
        String builder = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" + DOCTOR_ID + " varchar," +
                DOCTOR_CODE + " varchar," +
                STATE + " varchar," +
                CERTIFICATE_A + " varchar," +
                CERTIFICATE_B + " varchar," +
                DOCTOR_PHONE + " varchar," +
                COST + " varchar," +
                LOGIN_PWD + " varchar," +
                DOCTOR_IMG + " varchar," +
                DOCTOR_NAME + " varchar," +
                POSITIONAL_TITLES + " varchar," +
                HOSPITAL_ID + " varchar," +
                SPECIALITY + " varchar," +
                DOCTOR_ORDER + " varchar," +
                DOCTOR_DESC + " varchar" +
                ")";
        db.execSQL(builder);
    }

    public static void updateTable(SQLiteDatabase db , int oldVersion, int newVersion){

    }

    public void saveDoctorList(Context context,List<Doctor> doctors){
        if ( doctors == null ){
            return;
        }
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

    public List<DoctorEntity> searchDoctorListByNameAndCity(Context context,String name,String city){
        List<DoctorEntity> doctorEntities = new ArrayList<>();
        String builder = "select " +
                "a." + DOCTOR_ID + "," +
                "a." + DOCTOR_IMG + "," +
                "a." + DOCTOR_NAME + "," +
                "a." + POSITIONAL_TITLES + "," +
                "a." + SPECIALITY + "," +
                "a." + DOCTOR_DESC + "," +
                "b." + HospitalDao.HOSPITAL_ID + "," +
                "b." + HospitalDao.HOSPITAL_NAME +
                " from " + TABLE_NAME + " a ," + HospitalDao.TABLE_NAME + " b " +
                " where a." + HOSPITAL_ID + " = " + "b." + HospitalDao.HOSPITAL_ID +
                " and a." + DOCTOR_NAME + " like '%" + name + "%' " +
                " and b." + HospitalDao.PROVINCE + " = '" + city + "' " +
                " order by a." + DOCTOR_ORDER;

        SQLiteDatabase db = DataBaseUtil.openDataBase(context);
        Cursor cursor = db.rawQuery(builder,null);

        if ( cursor != null ){
            while(cursor.moveToNext()){
                DoctorEntity entity = new DoctorEntity();
                entity.setDoctorid(cursor.getString(cursor.getColumnIndex(DOCTOR_ID)));
                entity.setDoctordesc(cursor.getString(cursor.getColumnIndex(DOCTOR_DESC)));
                entity.setDoctorimg(cursor.getString(cursor.getColumnIndex(DOCTOR_IMG)));
                entity.setDoctorname(cursor.getString(cursor.getColumnIndex(DOCTOR_NAME)));
                entity.setHospital(cursor.getString(cursor.getColumnIndex(HospitalDao.HOSPITAL_NAME)));
                entity.setHospitalid(cursor.getString(cursor.getColumnIndex(HospitalDao.HOSPITAL_ID)));
                entity.setPositionaltitles(cursor.getString(cursor.getColumnIndex(POSITIONAL_TITLES)));
                entity.setSpeciality(cursor.getString(cursor.getColumnIndex(SPECIALITY)));
                doctorEntities.add(entity);
            }
        }
        cursor.close();
        DataBaseUtil.closeDataBase();
        return doctorEntities;
    }

    public List<DoctorEntity> searchDoctorListByName(Context context , String name){
        List<DoctorEntity> doctorEntities = new ArrayList<>();
        String builder = "select " +
                "a." + DOCTOR_ID + "," +
                "a." + DOCTOR_IMG + "," +
                "a." + DOCTOR_NAME + "," +
                "a." + POSITIONAL_TITLES + "," +
                "a." + SPECIALITY + "," +
                "a." + DOCTOR_DESC + "," +
                "b." + HospitalDao.HOSPITAL_ID + "," +
                "b." + HospitalDao.HOSPITAL_NAME +
                " from " + TABLE_NAME + " a ," + HospitalDao.TABLE_NAME + " b " +
                " where a." + HOSPITAL_ID + " = " + "b." + HospitalDao.HOSPITAL_ID +
                " and a." + DOCTOR_NAME + " like '%" + name + "%' " +
                " order by a." + DOCTOR_ORDER;

        SQLiteDatabase db = DataBaseUtil.openDataBase(context);
        Cursor cursor = db.rawQuery(builder,null);

        if ( cursor != null ){
            while(cursor.moveToNext()){
                DoctorEntity entity = new DoctorEntity();
                entity.setDoctorid(cursor.getString(cursor.getColumnIndex(DOCTOR_ID)));
                entity.setDoctordesc(cursor.getString(cursor.getColumnIndex(DOCTOR_DESC)));
                entity.setDoctorimg(cursor.getString(cursor.getColumnIndex(DOCTOR_IMG)));
                entity.setDoctorname(cursor.getString(cursor.getColumnIndex(DOCTOR_NAME)));
                entity.setHospital(cursor.getString(cursor.getColumnIndex(HospitalDao.HOSPITAL_NAME)));
                entity.setHospitalid(cursor.getString(cursor.getColumnIndex(HospitalDao.HOSPITAL_ID)));
                entity.setPositionaltitles(cursor.getString(cursor.getColumnIndex(POSITIONAL_TITLES)));
                entity.setSpeciality(cursor.getString(cursor.getColumnIndex(SPECIALITY)));
                doctorEntities.add(entity);
            }
        }
        cursor.close();
        DataBaseUtil.closeDataBase();
        return doctorEntities;
    }

    public List<DoctorEntity> searchDoctorList(Context context , int offset , int limit ){
        List<DoctorEntity> doctorEntities = new ArrayList<>();
        SQLiteDatabase db = DataBaseUtil.openDataBase(context);
        String builder = "select " +
                "a." + DOCTOR_ID + "," +
                "a." + DOCTOR_IMG + "," +
                "a." + DOCTOR_NAME + "," +
                "a." + POSITIONAL_TITLES + "," +
                "a." + SPECIALITY + "," +
                "a." + DOCTOR_DESC + "," +
                "b." + HospitalDao.HOSPITAL_ID + "," +
                "b." + HospitalDao.HOSPITAL_NAME +
                " from " + TABLE_NAME + " a ," + HospitalDao.TABLE_NAME + " b " +
                " where a." + HOSPITAL_ID + " = " + "b." + HospitalDao.HOSPITAL_ID +
                " order by a." + DOCTOR_ORDER +
                " limit " + limit +
                " offset " + offset * limit;

        Cursor cursor = db.rawQuery(builder,null);

        if ( cursor != null ){
            while(cursor.moveToNext()){
                DoctorEntity entity = new DoctorEntity();
                entity.setDoctorid(cursor.getString(cursor.getColumnIndex(DOCTOR_ID)));
                entity.setDoctordesc(cursor.getString(cursor.getColumnIndex(DOCTOR_DESC)));
                entity.setDoctorimg(cursor.getString(cursor.getColumnIndex(DOCTOR_IMG)));
                entity.setDoctorname(cursor.getString(cursor.getColumnIndex(DOCTOR_NAME)));
                entity.setHospital(cursor.getString(cursor.getColumnIndex(HospitalDao.HOSPITAL_NAME)));
                entity.setHospitalid(cursor.getString(cursor.getColumnIndex(HospitalDao.HOSPITAL_ID)));
                entity.setPositionaltitles(cursor.getString(cursor.getColumnIndex(POSITIONAL_TITLES)));
                entity.setSpeciality(cursor.getString(cursor.getColumnIndex(SPECIALITY)));
                doctorEntities.add(entity);
            }
        }
        cursor.close();
        DataBaseUtil.closeDataBase();

        return doctorEntities;
    }

    public List<DoctorEntity> searchDoctorList(Context context){
        List<DoctorEntity> doctorEntities = new ArrayList<>();
        SQLiteDatabase db = DataBaseUtil.openDataBase(context);
        String builder = "select " +
                "a." + DOCTOR_ID + "," +
                "a." + DOCTOR_IMG + "," +
                "a." + DOCTOR_NAME + "," +
                "a." + POSITIONAL_TITLES + "," +
                "a." + SPECIALITY + "," +
                "a." + DOCTOR_DESC + "," +
                "b." + HospitalDao.HOSPITAL_ID + "," +
                "b." + HospitalDao.HOSPITAL_NAME +
                " from " + TABLE_NAME + " a ," + HospitalDao.TABLE_NAME + " b " +
                " where a." + HOSPITAL_ID + " = " + "b." + HospitalDao.HOSPITAL_ID +
                " order by a." + DOCTOR_ORDER;

        Cursor cursor = db.rawQuery(builder,null);

        if ( cursor != null ){
            while(cursor.moveToNext()){
                DoctorEntity entity = new DoctorEntity();
                entity.setDoctorid(cursor.getString(cursor.getColumnIndex(DOCTOR_ID)));
                entity.setDoctordesc(cursor.getString(cursor.getColumnIndex(DOCTOR_DESC)));
                entity.setDoctorimg(cursor.getString(cursor.getColumnIndex(DOCTOR_IMG)));
                entity.setDoctorname(cursor.getString(cursor.getColumnIndex(DOCTOR_NAME)));
                entity.setHospital(cursor.getString(cursor.getColumnIndex(HospitalDao.HOSPITAL_NAME)));
                entity.setHospitalid(cursor.getString(cursor.getColumnIndex(HospitalDao.HOSPITAL_ID)));
                entity.setPositionaltitles(cursor.getString(cursor.getColumnIndex(POSITIONAL_TITLES)));
                entity.setSpeciality(cursor.getString(cursor.getColumnIndex(SPECIALITY)));
                doctorEntities.add(entity);
            }
        }
        cursor.close();
        DataBaseUtil.closeDataBase();
        return doctorEntities;
    }

    public List<DoctorEntity> searchDoctorsByHospitalId(Context context,String hospitalId){
        List<DoctorEntity> entities = new ArrayList<>();
        String sql = "select * from " + TABLE_NAME + " where " + HOSPITAL_ID + " = " + hospitalId + " order by " + DOCTOR_ORDER;
        SQLiteDatabase db = DataBaseUtil.openDataBase(context);
        Cursor cursor = db.rawQuery(sql,null);
        if ( cursor != null ){
            while(cursor.moveToNext()){
                DoctorEntity entity = new DoctorEntity();
                entity.setDoctorid(cursor.getString(cursor.getColumnIndex(DOCTOR_ID)));
                entity.setDoctordesc(cursor.getString(cursor.getColumnIndex(DOCTOR_DESC)));
                entity.setDoctorimg(cursor.getString(cursor.getColumnIndex(DOCTOR_IMG)));
                entity.setDoctorname(cursor.getString(cursor.getColumnIndex(DOCTOR_NAME)));
                entity.setHospitalid(cursor.getString(cursor.getColumnIndex(HospitalDao.HOSPITAL_ID)));
                entity.setPositionaltitles(cursor.getString(cursor.getColumnIndex(POSITIONAL_TITLES)));
                entity.setSpeciality(cursor.getString(cursor.getColumnIndex(SPECIALITY)));
                entities.add(entity);
            }
        }
        cursor.close();
        DataBaseUtil.closeDataBase();
        return entities;
    }

    public Doctor searchDoctorByName(Context context,String doctorName){
        Doctor doctor = null;
        String sql = "select * from " + TABLE_NAME + " where " + DOCTOR_NAME + " = '" + doctorName+"'";
        SQLiteDatabase db = DataBaseUtil.openDataBase(context);
        Cursor cursor = db.rawQuery(sql,null);
        if ( cursor.moveToNext() ){
            doctor = new Doctor();
            doctor.setDoctorid(cursor.getString(cursor.getColumnIndex(DOCTOR_ID)));
            doctor.setDoctorcode(cursor.getString(cursor.getColumnIndex(DOCTOR_CODE)));
            doctor.setState(cursor.getString(cursor.getColumnIndex(STATE)));
            doctor.setCertificatea(cursor.getString(cursor.getColumnIndex(CERTIFICATE_A)));
            doctor.setCertificateb(cursor.getString(cursor.getColumnIndex(CERTIFICATE_B)));
            doctor.setDoctorphone(cursor.getString(cursor.getColumnIndex(DOCTOR_PHONE)));
            doctor.setCost(cursor.getString(cursor.getColumnIndex(COST)));
            doctor.setLoginpwd(cursor.getString(cursor.getColumnIndex(LOGIN_PWD)));
            doctor.setDoctorimg(cursor.getString(cursor.getColumnIndex(DOCTOR_IMG)));
            doctor.setDoctorname(cursor.getString(cursor.getColumnIndex(DOCTOR_NAME)));
            doctor.setPositionaltitles(cursor.getString(cursor.getColumnIndex(POSITIONAL_TITLES)));
            doctor.setHospitalid(cursor.getString(cursor.getColumnIndex(HOSPITAL_ID)));
            doctor.setSpeciality(cursor.getString(cursor.getColumnIndex(SPECIALITY)));
            doctor.setDoctordesc(cursor.getString(cursor.getColumnIndex(DOCTOR_DESC)));
            doctor.setDoctororder(cursor.getString(cursor.getColumnIndex(DOCTOR_ORDER)));
        }
        cursor.close();
        DataBaseUtil.closeDataBase();
        return doctor;
    }

    public Doctor searchDoctorById(Context context , String doctorId){
        Doctor doctor = null;
        String sql = "select * from " + TABLE_NAME + " where " + DOCTOR_ID + " = " + doctorId;
        SQLiteDatabase db = DataBaseUtil.openDataBase(context);
        Cursor cursor = db.rawQuery(sql,null);
        if ( cursor.moveToNext() ){
            doctor = new Doctor();
            doctor.setDoctorid(cursor.getString(cursor.getColumnIndex(DOCTOR_ID)));
            doctor.setDoctorcode(cursor.getString(cursor.getColumnIndex(DOCTOR_CODE)));
            doctor.setState(cursor.getString(cursor.getColumnIndex(STATE)));
            doctor.setCertificatea(cursor.getString(cursor.getColumnIndex(CERTIFICATE_A)));
            doctor.setCertificateb(cursor.getString(cursor.getColumnIndex(CERTIFICATE_B)));
            doctor.setDoctorphone(cursor.getString(cursor.getColumnIndex(DOCTOR_PHONE)));
            doctor.setCost(cursor.getString(cursor.getColumnIndex(COST)));
            doctor.setLoginpwd(cursor.getString(cursor.getColumnIndex(LOGIN_PWD)));
            doctor.setDoctorimg(cursor.getString(cursor.getColumnIndex(DOCTOR_IMG)));
            doctor.setDoctorname(cursor.getString(cursor.getColumnIndex(DOCTOR_NAME)));
            doctor.setPositionaltitles(cursor.getString(cursor.getColumnIndex(POSITIONAL_TITLES)));
            doctor.setHospitalid(cursor.getString(cursor.getColumnIndex(HOSPITAL_ID)));
            doctor.setSpeciality(cursor.getString(cursor.getColumnIndex(SPECIALITY)));
            doctor.setDoctordesc(cursor.getString(cursor.getColumnIndex(DOCTOR_DESC)));
            doctor.setDoctororder(cursor.getString(cursor.getColumnIndex(DOCTOR_ORDER)));
        }
        cursor.close();
        DataBaseUtil.closeDataBase();
        return doctor;
    }

}
