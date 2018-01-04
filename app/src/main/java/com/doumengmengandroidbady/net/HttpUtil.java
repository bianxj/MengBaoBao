package com.doumengmengandroidbady.net;

import com.doumengmengandroidbady.base.BaseApplication;
import com.doumengmengandroidbady.request.ResponseErrorCode;
import com.doumengmengandroidbady.util.GsonUtil;

import java.io.File;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
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
    public final static String  TYPE_FILE = "UPLOAD_FILES";

    private final OkHttpClient client;
    private MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded;charset=utf-8");

    private HttpUtil() {
        client = defaultHttpClient();
    }

    public String httpsRequestPost(String url, Map<String,String> map){
        String result = null;
        try {
            Request.Builder builder  = new Request.Builder();
            builder.url(url);
            builder.addHeader("content-type","text/html;charset:utf-8");
            MultipartBody.Builder bodyBuilder = new MultipartBody.Builder();
            bodyBuilder.setType(MultipartBody.FORM);
            BaseApplication.getInstance().getMLog().info("HttpUtil:url:"+url);
            if ( map != null ) {
                Set<String> keys = map.keySet();
                for (String key : keys) {
                    if ( TYPE_FILE.equals(key) ) {
                        UploadFile uploadFile = GsonUtil.getInstance().getGson().fromJson(map.get(key),UploadFile.class);
                        File file = new File(uploadFile.getFilePath());
                        bodyBuilder.addFormDataPart(uploadFile.getFileName(),file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"),file));
                    } else {
                        BaseApplication.getInstance().getMLog().info("HttpUtil:value:" + key + ":" + map.get(key));
                        bodyBuilder.addFormDataPart(key, map.get(key));
                    }
                }
            }
            Request request = builder.post(bodyBuilder.build()).build();
            Response response = client.newCall(request).execute();
            BaseApplication.getInstance().getMLog().info("HttpUtil:code:"+response.code());
            if (response.code() == 200) {
                result = response.body().string();
            } else {
                result = ResponseErrorCode.ERROR_REQUEST_FAILED_MSG;
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = ResponseErrorCode.ERROR_REQUEST_FAILED_MSG;
        }
        BaseApplication.getInstance().getMLog().info("HttpUtil:result:"+result);
        return result;
    }

//    public String httpsRequestPost(String url) {
//        String result = null;
//        try {
//            RequestBody body = RequestBody.create(mediaType, "");
//            Request.Builder builder = new Request.Builder();
//            String urlUtf = new String(url.getBytes(),"utf-8");
//            BaseApplication.getInstance().getMLog().info("HttpUtil:url:"+urlUtf);
//            builder.url(urlUtf);
//            builder.post(body);
//            Request request = builder.build();
//            Response response = client.newCall(builder.build()).execute();
//            BaseApplication.getInstance().getMLog().info("HttpUtil:code:"+response.code());
//            if (response.code() == 200) {
//                result = response.body().string();
//            } else {
//                result = ResponseErrorCode.ERROR_REQUEST_FAILED_MSG;
//            }
//            BaseApplication.getInstance().getMLog().info("HttpUtil:result:"+result);
//        } catch (Exception e) {
//            e.printStackTrace();
//            result = ResponseErrorCode.ERROR_REQUEST_FAILED_MSG;
//        }
//        return result;
//    }

//    public String httpsRequestPost(String urlString){
//        URL url = null;
//        try {
//            String par = URLEncoder.encode("{\"accountMobile\":\"测试\",\"loginPwd\":\"测试\"}","UTF-8");
//            url = new URL("http://192.168.31.112:8080/mbbPhoneServerV2/babyUser.do?method=Login&paramStr="+par);
//            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//            httpURLConnection.setRequestMethod("POST");// 提交模式
//////            httpURLConnection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
//            httpURLConnection.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//            httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
//////            httpURLConnection.setRequestProperty("Charset", "utf-8");
//            httpURLConnection.setConnectTimeout(10000);//连接超时 单位毫秒
//            httpURLConnection.setReadTimeout(2000);//读取超时 单位毫秒
////            // 发送POST请求必须设置如下两行
//            httpURLConnection.setDoOutput(true);
//            httpURLConnection.setDoInput(true);
////            httpURLConnection.setInstanceFollowRedirects(true);
////            // 获取URLConnection对象对应的输出流
////            //开始获取数据
////
////            BufferedInputStream bis = new BufferedInputStream(httpURLConnection.getInputStream());
////            ByteArrayOutputStream bos = new ByteArrayOutputStream();
////            int len;
////            byte[] arr = new byte[1024];
////            while((len=bis.read(arr))!= -1){
////                bos.write(arr,0,len);
////                bos.flush();
////            }
////            bos.close();
////            return bos.toString("utf-8");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

//    public String httpsRequestPost2(String urlString){
//        try {
//            URL url = new URL(urlString);
//            url.openConnection();
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//    }

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
