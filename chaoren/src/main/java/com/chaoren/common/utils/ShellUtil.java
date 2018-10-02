package com.chaoren.common.utils;

import java.io.InputStreamReader;
import java.io.LineNumberReader;

public class ShellUtil {
    /**
     * 执行Linux命令静态服务重启
     * @param cmd
     * @return
     */
    public static String reboot(String cmd) {
        try {
            String[] cmdA = { "/bin/sh", "-c", cmd };
            Process process = Runtime.getRuntime().exec(cmdA);
            LineNumberReader br = new LineNumberReader(new InputStreamReader(
                    process.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                sb.append(line);
                if (sb.toString().contains("Service Started")){
                    System.out.println("success");
                    return "success";
                }
            }
            return "fail";

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /*public static void main(String[] args){
        String feature="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";
        System.out.println(feature.hashCode());
    }*/
}
