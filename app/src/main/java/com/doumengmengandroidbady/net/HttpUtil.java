package com.doumengmengandroidbady.net;

import com.doumengmengandroidbady.base.BaseApplication;
import com.doumengmengandroidbady.request.ResponseErrorCode;
import com.doumengmengandroidbady.util.GsonUtil;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.Util;

/**
 * 作者:边贤君
 * 描述:
 * 创建日期:2017/12/2 18:53
 */
public class HttpUtil {

    private static HttpUtil util;

    public static HttpUtil getInstance(){
        if ( null == util ){
            util = new HttpUtil();
        }
        return util;
    }

    private final static long CONNECTION_TIMEOUT = 60 * 1000;
    private final static long READ_TIMEOUT = 60 * 1000;
    public final static String  TYPE_FILE = "UPLOAD_FILES";

    private final OkHttpClient client;

    private HttpUtil() {
        client = defaultHttpClient();
    }

    public String httpsRequestFile(String urlString){
        String result = null;
        Request.Builder builder = new Request.Builder();
        builder.url(urlString);
        builder.post(Util.EMPTY_REQUEST);
//        builder.addHeader("content-type","text/html;charset:utf-8");
        builder.addHeader("Connection","close");
        Request request = builder.build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            if ( response != null && response.body() != null ) {
                result = response.body().string();
                response.body().close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if ( builder != null ){
                builder.delete();
            }
            if ( response != null ){
                response.close();
            }
        }
        return result;
    }

    public String httpsRequestPost(String url, Map<String,String> map){
        Response response = null;
        Request request = null;
        Request.Builder builder = null;
        String result;
        try {
            builder  = new Request.Builder();
            builder.url(url);
            builder.addHeader("content-type","text/html;charset:utf-8");
            MultipartBody.Builder bodyBuilder = new MultipartBody.Builder();
            bodyBuilder.setType(MultipartBody.FORM);
            BaseApplication.getInstance().getMLog().info("HttpUtil:url:"+url);
            if ( map != null ) {
                Set<String> keys = map.keySet();
                for (String key : keys) {
                    if ( TYPE_FILE.equals(key) ) {
                        List<UploadFile> uploadFiles = GsonUtil.getInstance().fromJson(map.get(key),new TypeToken<List<UploadFile>>(){}.getType());
                        if ( uploadFiles != null && uploadFiles.size() > 0 ){
                            for (UploadFile uploadFile :uploadFiles){
                                File file = new File(uploadFile.getFilePath());
                                bodyBuilder.addFormDataPart(uploadFile.getFileName(),file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"),file));
                            }
                        }
                    } else {
                        BaseApplication.getInstance().getMLog().info("HttpUtil:value:" + key + ":" + map.get(key));
                        bodyBuilder.addFormDataPart(key, map.get(key));
                    }
                }
                map.clear();
            }
            request = builder.post(bodyBuilder.build()).build();
            response = client.newCall(request).execute();
            BaseApplication.getInstance().getMLog().info("HttpUtil:code:"+response.code());
            if (response.code() == 200 && response.body() != null) {
                result = response.body().string();
                response.body().close();
            } else {
                result = ResponseErrorCode.ERROR_REQUEST_FAILED_MSG;
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = ResponseErrorCode.ERROR_REQUEST_FAILED_MSG;
        } finally {
            if ( builder != null ){
                builder.delete();
            }
            if ( response != null ){
                response.close();
            }
        }
        BaseApplication.getInstance().getMLog().info("HttpUtil:result:"+result);
        return result;
    }

    private OkHttpClient defaultHttpClient(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS);

        builder.readTimeout(READ_TIMEOUT,TimeUnit.MILLISECONDS);
        return builder.build();
    }

    public static class UploadFile{
        private String fileName;
        private String filePath;

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }
    }

}
