package com.chaoren.common.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.*;

/**
 * Created by lichao on 2018/9/18.
 */
public class ImageUtil {

    public static String httpPostRequest(String url, String postData) {

        CloseableHttpClient client= null;
        String res ="";

        try {

            client = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);

            httpPost.setHeader("Content-type", "application/json");
            System.out.println("url: "+url);
            System.out.println("postData: "+postData);

            StringEntity s = new StringEntity(postData, "UTF-8");
            httpPost.setEntity(s);
            HttpResponse httpResponse;
//            httpResponse = client.execute(httpPost);

            try {
                httpResponse = client.execute(httpPost);
                HttpEntity entity1 = httpResponse.getEntity();

                res = EntityUtils.toString(entity1);
                int status = httpResponse.getStatusLine().getStatusCode();

                System.out.println("status: "+status);
                System.out.println("RESULT: "+res);


            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(client !=null) {
                try {
                    client.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        return res;
    }

    /**
     * 二进制数据编码为BASE64字符串
     *
     * @param b
     * @return @throws
     */
    private static String encode(byte[] b) {
        String base64 = Base64.encodeBase64String(b);
        return base64;
    }

    /**
     * 解码
     *@param str
     * @return string
     */
    private static byte[] decode(String str) {
        byte[] b = null;
        b = Base64.decodeBase64(str);
        return b;
    }

    public static String getBase64ImageFile(File file) {
        BufferedInputStream in = null;
        try {
            // 创建文件字节读取流对象时。
            FileInputStream fis = new FileInputStream(file);

            in = new BufferedInputStream(fis);
            byte[] bys = streamToByte(in);
            String base64 = encode(bys);
            return base64;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static byte[] streamToByte(InputStream input) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int len = 0;
        byte[] b = new byte[1024];
        while ((len = input.read(b, 0, b.length)) != -1) {
            baos.write(b, 0, len);
        }
        byte[] buffer = baos.toByteArray();
        return buffer;
    }



}
