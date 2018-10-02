//package com.chaoren.common.utils;
//
//import com.google.gson.Gson;
//
//public class Converter {
//    private static Gson gson;
//
//    public static Gson getGson() {
//        if (gson == null) {
//            gson = new Gson();
//        }
//        return gson;
//    }
//
//    public static <T> T deserialize(String body, Class<T> clazz) {
//        return getGson().fromJson(body, clazz);
//    }
//
//    public static String serialize(Object obj) {
//        return getGson().toJson(obj);
//    }
//
//}
