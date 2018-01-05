package com.doumengmengandroidbady.util;

/**
 * Created by Administrator on 2018/1/5.
 */

public class FormulaUtil {

    public static float formulaBMI(float weight,float height){
        return (Math.round((weight * 10000) / (height*height)*100))/100f;
    }

}
