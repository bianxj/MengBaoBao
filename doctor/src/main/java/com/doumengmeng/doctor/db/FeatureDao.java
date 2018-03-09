package com.doumengmeng.doctor.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.doumengmeng.doctor.response.entity.Feature;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2018/1/9.
 */

public class FeatureDao {

    public final static String TABLE_NAME = "Feature";

    private final static String ID = "id";
    private final static String FEATURE_TYPE_ID = "featuretypeid";
    private final static String FEATURE_ORDER = "featureorder";
    private final static String POINT_TAG = "pointtag";
    private final static String DETAIL_DESC = "detaildesc";
    private final static String EXAMPLE_IMG_URL = "exampleimgurl";
    private final static String AGE = "age";
    private final static String FEATURE_TYPE = "featuretype";
    private final static String FEATURE_CODE = "featurecode";
    private final static String IS_USE = "isuse";

    public static void createTable(SQLiteDatabase db){
        String builder = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" + ID + " varchar," +
                FEATURE_TYPE_ID + " varchar," +
                FEATURE_ORDER + " varchar," +
                POINT_TAG + " varchar," +
                DETAIL_DESC + " varchar," +
                EXAMPLE_IMG_URL + " varchar," +
                AGE + " varchar," +
                FEATURE_TYPE + " varchar," +
                FEATURE_CODE + " varchar," +
                IS_USE + " varchar" +
                ")";
        db.execSQL(builder);
    }

    public static void updateTable(SQLiteDatabase db , int oldVersion, int newVersion){

    }

    public void saveFeatureList(Context context, List<Feature> features){
        if ( features == null ){
            return;
        }
        SQLiteDatabase db = DataBaseUtil.openDataBase(context);
        db.beginTransaction();

        for (int i = 0; i < features.size() ;i++){
            Feature feature = features.get(i);
            ContentValues values = new ContentValues();
            values.put(ID,feature.getId());
            values.put(FEATURE_TYPE_ID,feature.getFeaturetypeid());
            values.put(FEATURE_ORDER,feature.getFeatureorder());
            values.put(POINT_TAG,feature.getPointtag());
            values.put(DETAIL_DESC,feature.getDetaildesc());
            values.put(EXAMPLE_IMG_URL,feature.getExampleimgurl());
            values.put(AGE,feature.getAge());
            values.put(FEATURE_TYPE,feature.getFeaturetype());
            values.put(FEATURE_CODE,feature.getFeaturecode());
            values.put(IS_USE,feature.getIsuse());
            db.insert(TABLE_NAME,null,values);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        DataBaseUtil.closeDataBase();
    }

    public List<Feature> searchFeatureList(Context context,List<String> ages,String recordTime){
        List<Feature> features = new ArrayList<>();
        List<String> isUse = conculateIsUse(recordTime);

        String builder = "select " +
                ID + "," +
                FEATURE_TYPE_ID + "," +
                POINT_TAG + "," +
                EXAMPLE_IMG_URL + "," +
                AGE + "," +
                FEATURE_TYPE + "," +
                FEATURE_CODE + "," +
                DETAIL_DESC +
                " from " + TABLE_NAME +
                " where " +
                generateWhereIn(AGE, ages) +
                " and " +
                generateWhereIn(IS_USE, isUse) +
                " order by " + FEATURE_ORDER;

        SQLiteDatabase db = DataBaseUtil.openDataBase(context);
        Cursor cursor = db.rawQuery(builder,null);

        String title = "";
        while(cursor.moveToNext()){
            Feature feature = new Feature();
            feature.setId(cursor.getString(cursor.getColumnIndex(ID)));
            feature.setFeaturetypeid(cursor.getString(cursor.getColumnIndex(FEATURE_TYPE_ID)));
            feature.setPointtag(cursor.getString(cursor.getColumnIndex(POINT_TAG)));
            feature.setExampleimgurl(cursor.getString(cursor.getColumnIndex(EXAMPLE_IMG_URL)));
            feature.setAge(cursor.getString(cursor.getColumnIndex(AGE)));
            feature.setFeaturetype(cursor.getString(cursor.getColumnIndex(FEATURE_TYPE)));
            feature.setFeaturecode(cursor.getString(cursor.getColumnIndex(FEATURE_CODE)));
            feature.setDetaildesc(cursor.getString(cursor.getColumnIndex(DETAIL_DESC)));
            if ( !title.equals(feature.getFeaturetype()) ){
                feature.setTitle(true);
            }
            title = feature.getFeaturetype();
            features.add(feature);
        }

        cursor.close();
        DataBaseUtil.closeDataBase();
        return features;
    }

    public Map<String,List<String>> searchFeatureListById(Context context,List<String> ids){
        Map<String,List<String>> maps = new HashMap<>();

        String builder = "select " +
                FEATURE_TYPE + "," +
                DETAIL_DESC +
                " from " + TABLE_NAME +
                " where " +
                generateWhereIn(ID, ids) +
                " order by " + FEATURE_ORDER;

        SQLiteDatabase db = DataBaseUtil.openDataBase(context);
        Cursor cursor = db.rawQuery(builder,null);

        while ( cursor.moveToNext() ){
            List<String> list;
            String type = cursor.getString(cursor.getColumnIndex(FEATURE_TYPE));
            if ( !maps.containsKey(type) ){
                list = new ArrayList<>();
                maps.put(type,list);
            } else {
                list = maps.get(type);
            }
            list.add(cursor.getString(cursor.getColumnIndex(DETAIL_DESC)));
        }

        cursor.close();
        DataBaseUtil.closeDataBase();
        return maps;
    }

    private final static Map<String,List<String>> versionMap = new LinkedHashMap<>();

    static {
        versionMap.put("2017-06-01 18:00:00",new ArrayList<String>(Arrays.asList("0","1")));
        versionMap.put("2017-11-10 18:00:00",new ArrayList<String>(Arrays.asList("0","2")));
        versionMap.put("2018-02-01 18:00:00",new ArrayList<String>(Arrays.asList("3")));
        versionMap.put("2099-12-31 18:00:00",new ArrayList<String>(Arrays.asList("4")));
    }

    private List<String> conculateIsUse(String recordTime){
        Set<String> times = versionMap.keySet();
        for (String time:times){
            if ( time.compareTo(recordTime) > 0 ){
                return versionMap.get(time);
            }
        }
        return versionMap.get("2099-12-31 18:00:00");
    }

    private String generateWhereIn(String columnName,List<String> list){
        StringBuffer buffer = new StringBuffer();
        buffer.append(columnName).append(" in (");
        for (int i=0;i<list.size();i++){
            if ( i == 0 ){
                buffer.append(list.get(i));
            } else {
                buffer.append(",").append(list.get(i));
            }
        }
        buffer.append(")");
        return buffer.toString();
    }

}
