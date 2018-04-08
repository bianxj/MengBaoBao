package com.doumengmeng.customer.util;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

/**
 * Created by Administrator on 2018/1/31.
 */

public class ZxingUtil {

    private static ZxingUtil util;

    private QRCodeWriter writer;

    private ZxingUtil(){

    }

    public static ZxingUtil getInstance(){
        if ( util == null ){
            util = new ZxingUtil();
        }
        return util;
    }

    public void decodeQR(){

    }

    public Bitmap encodeQR(String content , int side) throws WriterException {
        if ( writer == null ){
            writer = new QRCodeWriter();
        }
        BitMatrix encode = writer.encode(content, BarcodeFormat.QR_CODE,side,side);
        int[] pixels = new int[side * side];
        for (int i = 0; i < side; i++) {
            for (int j = 0; j < side; j++) {
                if (encode.get(j, i)) {
                    pixels[i * side + j] = 0x00000000;
                } else {
                    pixels[i * side + j] = 0xffffffff;
                }
            }
        }
        return Bitmap.createBitmap(pixels,side,side, Bitmap.Config.RGB_565);
    }

}
