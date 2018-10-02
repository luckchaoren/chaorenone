package com.chaoren.common.utils;

public class NumUtil {
    public static String float2Percent(Float f){
        int temp=(int)(f*10000);
        String result=(float)temp/100+"%";
        return result;
    }
}
