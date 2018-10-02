package com.chaoren.common.utils;

import java.util.UUID;

/**
 * Created by zgming on 2017/12/4.
 */
public class UUIDUtil {

    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}
