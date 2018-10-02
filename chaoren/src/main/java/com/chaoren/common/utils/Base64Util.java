package com.chaoren.common.utils;

import org.apache.commons.codec.binary.Base64;

import java.io.*;
import java.net.URL;

public class Base64Util {

    /**
     * 二进制数据编码为BASE64字符串
     *
     * @param b
     * @return
     * @throws
     */

    public static String encode(byte[] b) {
        String base64 = Base64.encodeBase64String(b);
        return base64;
    }

    /**
     * 解码
     *
     * @param str
     * @return string
     */
    public static byte[] decode(String str) {
        byte[] b = null;
        b=Base64.decodeBase64(str);
        return b;
    }

    public static byte[]  getBase64(String urlpath) {
        BufferedInputStream in = null;
        try {
            URL url = new URL( urlpath );
            in = new BufferedInputStream( url.openStream() );
            byte[] bys = streamToByte( in );
            return bys;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (in!=null)
                    in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static byte[] streamToByte(InputStream input) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int len = 0;
        byte[] b = new byte[1024];
        while ((len = input.read( b, 0, b.length )) != -1) {
            baos.write( b, 0, len );
        }
        byte[] buffer = baos.toByteArray();
        return buffer;
    }

    public static byte[] getBytes(String filePath){
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    public static byte[] fileGetBytes(File file){
        byte[] buffer = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

/*
    public static void main(String[] args ){
       *//* String url="http://10.1.4.7:18080/g1/M01/00/00/CgEEB1sra-iIGIZOAAA5WrXUfiUAAAAOgAPVIsAADly757.jpg";
        byte[] data =Base64Util.getBase64(url);
        String base64=Base64Util.encode(data);
        System.out.println(base64);*//*
        String filename ="ccsdasd.jpg";
        String suffix = filename.substring(filename.lastIndexOf(".")).toLowerCase();

        System.out.println(suffix);
    }*/

}
