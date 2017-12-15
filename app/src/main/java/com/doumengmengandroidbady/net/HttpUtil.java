package com.doumengmengandroidbady.net;

import com.doumengmengandroidbady.base.BaseApplication;
import com.doumengmengandroidbady.request.ResponseErrorCode;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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

    private final static long CONNECTION_TIMEOUT = 30 * 1000;
    private final static long READ_TIMEOUT = 30 * 1000;

    private final OkHttpClient client;
    private MediaType mediaType = MediaType.parse("application/json; charset=utf-8");

    private HttpUtil() {
        client = defaultHttpClient();
    }

    public String httpsRequestPost(String url) {
        String result = null;
        try {
            RequestBody body = RequestBody.create(mediaType, "");
            Request.Builder builder = new Request.Builder();
            BaseApplication.getMLog().info("url:"+url);
            builder.url(url);
            builder.post(body);
            Request request = builder.build();
            Response response = client.newCall(builder.build()).execute();
            BaseApplication.getMLog().info("code:"+response.code());
            if (response.code() == 200) {
                result = response.body().string();
            } else {
                result = ResponseErrorCode.ERROR_REQUEST_FAILED;
            }
            BaseApplication.getMLog().info("result:"+result);
        } catch (Exception e) {
            e.printStackTrace();
            result = ResponseErrorCode.ERROR_REQUEST_FAILED;
        }
        return result;
    }

    public Response uploadFile(File file){
        return null;
    }

//    private void defineHeader(Request.Builder builder){
//
//    }

    private OkHttpClient defaultHttpClient(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS);

        builder.readTimeout(READ_TIMEOUT,TimeUnit.MILLISECONDS);
//        builder.addInterceptor(new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request originalRequest = chain.request();
//                Response response = chain.proceed(originalRequest);
//                return response;
//            }
//        });
        return builder.build();
    }

}
