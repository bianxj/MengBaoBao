package com.doumengmengandroidbady.net;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
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
public class BaseHttpUtil {

    private final static long CONNECTION_TIMEOUT = 30 * 1000;
    private final static long READ_TIMEOUT = 30 * 1000;
    private MediaType mediaType = MediaType.parse("");

    public BaseHttpUtil() {
    }

    public Response httpsRequestPost(String url , String content) throws IOException {
        OkHttpClient client = defaultHttpClient();

        RequestBody body = RequestBody.create(mediaType,content);
        Request.Builder builder = new Request.Builder();
        defineHeader(builder);
//        builder.addHeader("","");
        builder.url(url);
        builder.post(body);
        builder.build();
        return client.newCall(builder.build()).execute();
    }

    public Response uploadFile(File file){
        return null;
    }

    private void defineHeader(Request.Builder builder){

    }

    private OkHttpClient defaultHttpClient(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS);

        builder.readTimeout(READ_TIMEOUT,TimeUnit.MILLISECONDS);
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Response response = chain.proceed(originalRequest);
                return response;
            }
        });
        return builder.build();
    }

}
