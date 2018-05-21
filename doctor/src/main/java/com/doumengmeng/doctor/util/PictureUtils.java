package com.doumengmeng.doctor.util;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2017/12/28.
 */

public class PictureUtils {

    public static void saveBitmap(File file , Bitmap bitmap){
        try {
            FileOutputStream os = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,os);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void saveGalleryBitmapToLocal(String savePath,Context context,Uri uri) throws IOException {
        String path = getGalleryImagePath(context,uri);
        if ( !savePath.equals(path) ) {
            File saveFile = FileUtil.getIntance().createNewFile(savePath);
            int degree = getRotationDegree(path);
            Bitmap bitmap = getSmallBitmap(path, 1280);
            bitmap = rotateBitmap(bitmap, degree);
            saveBitmap(saveFile, bitmap);
        }
    }

    public static String getGalleryImagePath(Context context,Uri uri){
        int sdkVersion = Build.VERSION.SDK_INT;
        if (sdkVersion >= 19) {
            return PictureUtils.getPath_above19(context, uri);
        } else {
            return PictureUtils.getFilePath_below19(context,uri);
        }
    }

    /**
     * API19以下获取图片路径的方法
     *
     * @param uri
     */
    private static String getFilePath_below19(Context context, Uri uri) {
        //这里开始的第二部分，获取图片的路径：低版本的是没问题的，但是sdk>19会获取不到
        String[] proj = {MediaStore.Images.Media.DATA};
        //好像是android多媒体数据库的封装接口，具体的看Android文档
        String path = null;
        Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
        if ( cursor != null ) {
            //获得用户选择的图片的索引值
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            //将光标移至开头 ，这个很重要，不小心很容易引起越界
            cursor.moveToFirst();
            //最后根据索引值获取图片路径   结果类似：/mnt/sdcard/DCIM/Camera/IMG_20151124_013332.jpg
            path = cursor.getString(column_index);
            cursor.close();
        }
        return path;
    }

    /**
     * APIlevel 19以上才有
     * 创建项目时，我们设置了最低版本API Level，比如我的是10，
     * 因此，AS检查我调用的API后，发现版本号不能向低版本兼容，
     * 比如我用的“DocumentsContract.isDocumentUri(context, uri)”是Level 19 以上才有的，
     * 自然超过了10，所以提示错误。
     * 添加    @TargetApi(Build.VERSION_CODES.KITKAT)即可。
     *
     * @param context
     * @param uri
     * @return
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static String getPath_above19(final Context context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    private static String getDataColumn(Context context, Uri uri, String selection,
                                        String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    private static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

//    public static Bitmap zoomBitmap(String path,float zoom){
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        Bitmap bitmap = BitmapFactory.decodeFile(path,options);
//        int outHeight = (int) (options.outHeight);
//        int outWidth = (int) (options.outWidth);
//        options.inJustDecodeBounds = false;
//        return bitmap.createBitmap(BitmapFactory.decodeFile(path,options),0,0,outWidth,outHeight);
//    }

    /**
     * 图片的压缩
     *
     * @param options
     * @param reqWidth
     * @return
     */
    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth) {
        int width;
        int inSampleSize = 1;

        if ( options.outHeight > options.outWidth ){
            width = options.outWidth;
        } else {
            width = options.outHeight;
        }

        if ( width > reqWidth ){
            inSampleSize = Math.round((float) width / (float) reqWidth);
        }
        return inSampleSize;
    }

//    /**
//     * 压缩指定byte[]图片，并得到压缩后的图像
//     *
//     * @param bts
//     * @param reqsW
//     * @param reqsH
//     * @return
//     */
//    public static Bitmap getSmallBitmap(byte[] bts, int reqsW, int reqsH) {
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        BitmapFactory.decodeByteArray(bts, 0, bts.length, options);
//        options.inSampleSize = calculateInSampleSize(options, reqsW, reqsH);
//        options.inJustDecodeBounds = false;
//        return BitmapFactory.decodeByteArray(bts, 0, bts.length, options);
//    }


        /**
         * 根据路径获得照片并压缩返回bitmap用于显示
         *
         * @param filePath
         * @return
         */
    public static Bitmap getSmallBitmap(String filePath, int width) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;  //只返回图片的大小信息
        BitmapFactory.decodeFile(filePath, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, width);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return getSmallBitmap(BitmapFactory.decodeFile(filePath, options),width);
    }

    private static Bitmap getSmallBitmap(Bitmap bitmap,int reqWidth){
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float scale;
        if ( width > height ){
            scale = ((float)reqWidth)/width;
        } else {
            scale = ((float)reqWidth)/height;
        }
        Matrix matrix = new Matrix();
        matrix.setScale(scale,scale);
        Bitmap smaller = Bitmap.createBitmap(bitmap,0,0,width,height,matrix,false);
        if ( smaller != bitmap ){
            bitmap.recycle();
        }
        return smaller;
    }

    private static Bitmap rotateBitmap(Bitmap bitmap, float degree){
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();
//        if ( height < width ){
            Matrix matrix = new Matrix();
            matrix.setRotate(degree);
            Bitmap rotateBitmap = Bitmap.createBitmap(bitmap,0,0,width,height,matrix,false);
            if ( rotateBitmap.equals(bitmap) ){
                return bitmap;
            }
            bitmap.recycle();
            return rotateBitmap;
//        } else {
//            return bitmap;
//        }
    }

//    /**
//     * 将照片添加到相册中
//     */
//    public static void galleryAddPic(String mPublicPhotoPath, Context context) {
//        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//        File f = new File(mPublicPhotoPath);
//        Uri contentUri = Uri.fromFile(f);
//        mediaScanIntent.setData(contentUri);
//        context.sendBroadcast(mediaScanIntent);
//    }
//    /**
//     * 创建临时图片存储的路径
//     *
//     * @return
//     * @throws IOException
//     */
//    public static File createPublicImageFile() throws IOException {
//        File appDir = new File(Environment.getExternalStorageDirectory() + "/自定义相册的名字");
//        if (!appDir.exists()) {
//            appDir.mkdir();
//        }
//        String fileName = System.currentTimeMillis() + ".jpg";
//        File file = new File(appDir, fileName);
//        return file;
//    }
//
//    /**
//     * 图片转成string
//     *
//     * @param bitmap
//     * @return
//     */
//    public static String convertBitmapToString(Bitmap bitmap) {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();// outputstream
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
//        byte[] bytes = baos.toByteArray();// 转为byte数组
//        return Base64.encodeToString(bytes, Base64.DEFAULT);
//    }

//    public static void compressPicture(String source , String target, int reqWidth, int reqHeight) {
//        Bitmap bitmap = getSmallBitmap(source, reqWidth, reqHeight);
//        saveBitmapToPath(bitmap,target);
//    }
//
//    private static void saveBitmapToPath(Bitmap bitmap,String path){
//        File file = new File(path);
//        try {
//            if ( !file.exists() ){
//                file.createNewFile();
//            }
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(file));
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if ( bitmap != null ){
//                bitmap.recycle();
//            }
//        }
//    }
//
//    public static void rotationNormalDegree(String path){
//        int degree = getRotationDegree(path);
//        DisplayMetrics metrics = BaseApplication.getInstance().getDisplayInfo();
//        if ( degree > 0 ){
//            Bitmap bitmap = getSmallBitmap(path,metrics.widthPixels,metrics.heightPixels);
//            bitmap = rotateBitmap(bitmap,degree);
//            saveBitmapToPath(bitmap,path);
//        }
//    }

    private static int getRotationDegree(String path){
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int type = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,ExifInterface.ORIENTATION_NORMAL);
            switch (type) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    public static Bitmap loadBitmapFromResouce(Context context,int resId) throws Exception {
        InputStream is = null;
        try{
            BitmapFactory.Options opt = new BitmapFactory.Options();
            opt.inPreferredConfig = Bitmap.Config.RGB_565;
            opt.inPurgeable = true;
            opt.inInputShareable = true;
            is = context.getResources().openRawResource(resId);

            Bitmap bm = BitmapFactory.decodeStream(is, null, opt);
            return bm;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally{
            try {
                if(is != null){
                    is.close();
                    is = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

//    public static byte[] convertBitmapToByte(Bitmap bitmap){
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        return baos.toByteArray();
//    }

}
