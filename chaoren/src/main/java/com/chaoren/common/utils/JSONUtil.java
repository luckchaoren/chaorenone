//package com.chaoren.common.utils;
//
//import com.fasterxml.jackson.annotation.JsonInclude.Include;
//import com.fasterxml.jackson.core.JsonParser;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import org.apache.commons.lang.StringUtils;
//
//import java.text.SimpleDateFormat;
//
//
///**
// * JSON工具类
// *
// * @author qugf
// */
//public class JSONUtil {
//
//    /**
//     * 将给定的JSON串，转换为指定的对象
//     *
//     * @param jsonStr
//     * @param clz
//     * @return
//     */
//    public static <T> T jsonToObject(String jsonStr, Class<T> clz) {
//        if (StringUtils.isEmpty(jsonStr)) {
//            return null;
//        }
//
//        ObjectMapper mapper = getObjectMapper();
//        try {
//            return mapper.readValue(jsonStr, clz);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
//
//    /**
//     * 将给定的JSON串，转换为指定型的对象
//     * <pre>
//     * 示例：
//     * <code>
//     * Map&ltString,User&gt result = JSONUtil.jsonToObject(jsonStr, new TypeReference&ltMap&ltString,User&gt&gt() { });
//     * </code>
//     * </pre>
//     *
//     * @param jsonStr
//     * @param typeRef 泛型引用对象
//     * @return
//     */
//    public static <T> T jsonToObject(String jsonStr, TypeReference<T> typeRef) {
//        if (StringUtils.isEmpty(jsonStr)) {
//            return null;
//        }
//
//        ObjectMapper mapper = getObjectMapper();
//        try {
//            return mapper.readValue(jsonStr, typeRef);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
//
//    /**
//     * 将给定的对象，转换为JSON字符串
//     *
//     * @param object 待转换为JSON字符串的对象
//     * @return
//     */
//    public static String objectToJson(Object object) {
//        if (object == null) {
//            return null;
//        }
//
//        ObjectMapper mapper = getObjectMapper();
//        try {
//            return mapper.writeValueAsString(object);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
//
//    public static ObjectMapper getObjectMapper() {
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.setSerializationInclusion(Include.NON_NULL);
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
//        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
//        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
//
//        return objectMapper;
//    }
//}
