package com.doumengmeng.doctor.db;

import android.content.Context;

/**
 * Created by Administrator on 2017/12/27.
 */

public class DaoManager {

    private static DaoManager manager;

    private DaoManager(){

    }

    public static DaoManager getInstance(){
        if ( manager == null ){
            manager = new DaoManager();
        }
        return  manager;
    }

    private HospitalDao hospitalDao;
    public HospitalDao getHospitalDao(){
        if ( hospitalDao == null ) {
            hospitalDao = new HospitalDao();
        }
        return hospitalDao;
    }

    private FeatureDao featureDao;
    public FeatureDao getFeatureDao(){
        if ( featureDao == null ){
            featureDao = new FeatureDao();
        }
        return featureDao;
    }

    private NurtureDao nutureDao;
    public NurtureDao getNurtureDao(){
        if ( nutureDao == null ){
            nutureDao = new NurtureDao();
        }
        return nutureDao;
    }

    public void deleteTable(Context context ,String tableName){
        try {
            DataBaseUtil.openDataBase(context).delete(tableName,null,null);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
