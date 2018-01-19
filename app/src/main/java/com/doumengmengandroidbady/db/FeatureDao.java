package com.doumengmengandroidbady.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.doumengmengandroidbady.response.Feature;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        StringBuilder builder = new StringBuilder("CREATE TABLE IF NOT EXISTS "+TABLE_NAME+"(");
        builder.append(ID+" varchar,");
        builder.append(FEATURE_TYPE_ID + " varchar,");
        builder.append(FEATURE_ORDER+" varchar,");
        builder.append(POINT_TAG+" varchar,");
        builder.append(DETAIL_DESC+" varchar,");
        builder.append(EXAMPLE_IMG_URL+" varchar,");
        builder.append(AGE+" varchar,");
        builder.append(FEATURE_TYPE+" varchar,");
        builder.append(FEATURE_CODE+" varchar,");
        builder.append(IS_USE +" varchar");
        builder.append(")");
        db.execSQL(builder.toString());
    }

    public static void updateTable(SQLiteDatabase db , int oldVersion, int newVersion){

    }

    public void saveFeatureList(Context context, List<Feature> features){
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

        StringBuilder builder = new StringBuilder();
        builder.append("select ");
        builder.append(ID+",");
        builder.append(FEATURE_TYPE_ID+",");
        builder.append(POINT_TAG+",");
        builder.append(EXAMPLE_IMG_URL+",");
        builder.append(AGE+",");
        builder.append(FEATURE_TYPE+",");
        builder.append(FEATURE_CODE+",");
        builder.append(DETAIL_DESC);
        builder.append(" from " + TABLE_NAME);
        builder.append(" where ");
        builder.append(generateWhereIn(AGE,ages));
        builder.append(" and ");
        builder.append(generateWhereIn(IS_USE,isUse));
        builder.append(" order by " + FEATURE_ORDER);

        SQLiteDatabase db = DataBaseUtil.openDataBase(context);
        Cursor cursor = db.rawQuery(builder.toString(),null);

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

        return features;
    }

    public Map<String,List<String>> searchFeatureListById(Context context,List<String> ids){
        Map<String,List<String>> maps = new HashMap<>();

        StringBuilder builder = new StringBuilder();
        builder.append("select ");
        builder.append(FEATURE_TYPE+",");
        builder.append(DETAIL_DESC);
        builder.append(" from " + TABLE_NAME);
        builder.append(" where ");
        builder.append(generateWhereIn(ID,ids));
        builder.append(" order by " + FEATURE_ORDER);

        SQLiteDatabase db = DataBaseUtil.openDataBase(context);
        Cursor cursor = db.rawQuery(builder.toString(),null);

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

        return maps;
    }

    private final static String VERSION_ONE = "2017-06-01 18:00:00";
    private final static String VERSION_TWO = "2017-11-10 18:00:00";

    private static List<String> versionOne;
    private static List<String> versionTwo;
    private static List<String> versionThree;

    private static Map<String,List<String>> versionMap;

    static {
        versionOne = new ArrayList<>();
        versionOne.add("0");
        versionOne.add("1");

        versionTwo = new ArrayList<>();
        versionTwo.add("0");
        versionTwo.add("2");

        versionThree = new ArrayList<>();
        versionThree.add("3");
    }

    private List<String> conculateIsUse(String recordTime){
        if ( VERSION_ONE.compareTo(recordTime) > 0 ){
            return versionOne;
        } else if ( VERSION_TWO.compareTo(recordTime) > 0 ){
            return versionTwo;
        } else {
            return versionThree;
        }
    }

    private String generateWhereIn(String columnName,List<String> list){
        StringBuffer buffer = new StringBuffer();
        buffer.append(columnName + " in (");
        for (int i=0;i<list.size();i++){
            if ( i == 0 ){
                buffer.append(list.get(i));
            } else {
                buffer.append(","+list.get(i));
            }
        }
        buffer.append(")");
        return buffer.toString();
    }

}
