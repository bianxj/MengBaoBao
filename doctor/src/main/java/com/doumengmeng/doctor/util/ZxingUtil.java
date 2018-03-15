package com.doumengmeng.doctor.util;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.Hashtable;

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

        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix encode = writer.encode(content, BarcodeFormat.QR_CODE,side,side,hints);
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
