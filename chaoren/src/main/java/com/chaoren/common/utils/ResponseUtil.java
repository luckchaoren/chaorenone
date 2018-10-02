package com.chaoren.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class ResponseUtil {
    public static final int Rtn_OK=200;
    public static final String Rtn_OK_Message="成功";

    
    public static String getResult(int code, String message, JSONObject dataObj){
        JSONObject result =new JSONObject();
        result.put("code",code);
        result.put("message",message);
        if(dataObj==null){
            result.put("data",new JSONObject());
        }else{
            result.put("data",dataObj);
        }

        return JSONObject.toJSONString(result, SerializerFeature.WriteMapNullValue);
//        return result.toString();
    }

    public static String getEditResult(String result,JSONObject rtJson){
        try {
            int number = Integer.parseInt(result);
            if(number>0) {
                return ResponseUtil.getResult(200, "成功", rtJson);
            }else{
                return ResponseUtil.getExResult(result,rtJson);
            }
        }catch (Exception e){
            return ResponseUtil.getExResult(result,rtJson);
        }
    }

    public static String getExResult(String result,JSONObject rtJson){
        JSONObject jsonResult = JSONObject.parseObject(result);
        int code = jsonResult.getInteger("errorCode");
        String message = jsonResult.getString("message");
        return ResponseUtil.getResult(code, message, rtJson);
    }
}
