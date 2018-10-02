package com.chaoren.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.http.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import sun.net.www.protocol.ftp.FtpURLConnection;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HttpUtil {
	private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

	public static String httpGet(String url) {
		try {
			logger.debug(url);
			InputStream resStream = httpGetReturnStream(url);
			BufferedReader br = new BufferedReader(new InputStreamReader(resStream, "utf-8"));
			StringBuffer resBuffer = new StringBuffer();
			String resTemp = null;
			while ((resTemp = br.readLine()) != null) {
				resBuffer.append(resTemp);
			}
			String response = resBuffer.toString();
			logger.debug(response);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static InputStream httpGetReturnStream(String url) {
		try {
			HttpClient client = new HttpClient();
			//设置代理服务器地址和端口

			//client.getHostConfiguration().setProxy("proxy_host_addr",proxy_port);
			//使用GET方法，如果服务器需要通过HTTPS连接，那只需要将下面URL中的http换成https
			HttpMethod method = new GetMethod(url);

			//使用POST方法
			//HttpMethod method = new PostMethod("http://java.sun.com/");

			method.getParams().setParameter("Content-Type", "text/html;charset=UTF-8");

			client.executeMethod(method);
			logger.debug(method.getStatusLine().toString());
			return method.getResponseBodyAsStream();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String get(String url) {
		try {
			HttpGet get = new HttpGet(url);
			HttpResponse response = HttpClients.createDefault().execute(get);
			HttpEntity entity = response.getEntity();
			return EntityUtils.toString(entity, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getWithHeader(String url, Header header) {
		try {
			HttpGet get = new HttpGet(url);
			if (header != null) {
				get.addHeader(header.getName(), header.getValue());
			}

			logger.debug(get.toString());

			HttpResponse response = HttpClients.createDefault().execute(get);
			HttpEntity entity = response.getEntity();
			return EntityUtils.toString(entity, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String post(Map<String, String> params, String url) {
		try {
			List<NameValuePair> nv = new ArrayList<>();
			if (params != null) {
				Set<String> keys = params.keySet();
				for (String key : keys) {
					nv.add(new BasicNameValuePair(key, params.get(key)));
				}
			}

			HttpPost post = new HttpPost(url);
			post.setEntity(new UrlEncodedFormEntity(nv, "utf-8"));
			HttpResponse response = HttpClients.createDefault().execute(post);
			HttpEntity entity = response.getEntity();

			return EntityUtils.toString(entity, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static HttpResponse post2(Map<String, String> params, String url) {
		try {
			List<NameValuePair> nv = new ArrayList<>();
			if (params != null) {
				Set<String> keys = params.keySet();
				for (String key : keys) {
					nv.add(new BasicNameValuePair(key, params.get(key)));
				}
			}
			HttpPost post = new HttpPost(url);
			post.setEntity(new UrlEncodedFormEntity(nv, "utf-8"));
			return HttpClients.createDefault().execute(post);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String postWithHeader(Map<String, String> params, String url, Header header) {
		try {
			List<NameValuePair> nv = new ArrayList<NameValuePair>();
			if (params != null) {
				Set<String> keys = params.keySet();
				for (String key : keys) {
					nv.add(new BasicNameValuePair(key, params.get(key)));
				}
			}

			HttpPost post = new HttpPost(url);
			post.setEntity(new UrlEncodedFormEntity(nv, "utf-8"));
			post.addHeader(header);
			HttpResponse response = HttpClients.createDefault().execute(post);
			HttpEntity entity = response.getEntity();

			return EntityUtils.toString(entity, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String sendPostRequest(String content, String urlStr) throws Exception {
		URL url = new URL(urlStr);
		logger.info("try to open http connection to " + urlStr + "");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-type", "application/Json");
		conn.setRequestProperty("User-Agent", "rms.ott.letv.com");

		logger.info("try to get output stream");
		OutputStream output = conn.getOutputStream();

		logger.info("try to write '" + content + "'");
		output.write(content.getBytes());
		output.flush();
		output.close();

		logger.info("try to get input stream");
		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
		String line;
		StringBuffer buffer = new StringBuffer("");

		while ((line = reader.readLine()) != null) {
			buffer.append(line);
		}
		String value = buffer.toString();
		logger.info("get resposne: '" + value + "'");
		return value;
	}

	public static String sendPostRequestXml(String content, String urlStr) throws Exception {
		URL url = new URL(urlStr);
		logger.info("try to open http connection to " + urlStr + "");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-type", "text/xml");
		conn.setRequestProperty("user-agent",
				"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
		//      conn.setRequestProperty("User-Agent", "rms.ott.letv.com");

		logger.info("try to get output stream");
		OutputStream output = conn.getOutputStream();

		logger.info("try to write '" + content + "'");
		output.write(content.getBytes("utf-8"));
		output.flush();
		output.close();

		logger.info("try to get input stream");
		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
		String line;
		StringBuffer buffer = new StringBuffer("");

		while ((line = reader.readLine()) != null) {
			buffer.append(line);
		}
		String value = buffer.toString();
		logger.info("get resposne: '" + value + "'");
		return value;
	}

	public static String postByJson(String url, String encoderJson) {
		try {

			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(url);
			httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");
			StringEntity se = new StringEntity(encoderJson, Charset.forName("utf-8"));
			/*se.setContentType("text/json");
			se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));*/
			httpPost.setEntity(se);
			CloseableHttpResponse response = httpclient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			return EntityUtils.toString(entity, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String postByJsonWithHeader(String url, String encoderJson, Header header) {
		try {

			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(url);
			httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");
			StringEntity se = new StringEntity(encoderJson, Charset.forName("utf-8"));
			/*se.setContentType("text/json");
			se.setContentEncoding(new BasicHeader(HTTP.CONTENT_ENCODING, HTTP.UTF_8));*/
			httpPost.setEntity(se);
			httpPost.addHeader(header);
			CloseableHttpResponse response = httpclient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			return EntityUtils.toString(entity, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static HttpResponse postByJsonWithHeader2(String url, String encoderJson, Header header) {
		try {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(url);
			httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");
			StringEntity se = new StringEntity(encoderJson, Charset.forName("utf-8"));
			/*se.setContentType("text/json");
			se.setContentEncoding(new BasicHeader(HTTP.CONTENT_ENCODING, HTTP.UTF_8));*/
			httpPost.setEntity(se);
			httpPost.addHeader(header);
			return httpclient.execute(httpPost);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String putByJsonWithHeader(String url, String encoderJson, Header header) {
		try {

			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpPut httpPut = new HttpPut(url);

			httpPut.addHeader(HTTP.CONTENT_TYPE, "application/json");
			StringEntity se = new StringEntity(encoderJson, Charset.forName("utf-8"));
			/*se.setContentType("text/json");
			se.setContentEncoding(new BasicHeader(HTTP.CONTENT_ENCODING, HTTP.UTF_8));*/
			httpPut.setEntity(se);
			httpPut.addHeader(header);
			CloseableHttpResponse response = httpclient.execute(httpPut);
			HttpEntity entity = response.getEntity();
			return EntityUtils.toString(entity, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String deleteByJsonWithHeader(String url, Header header) {
		try {

			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpDelete httpDelete = new HttpDelete(url);

			httpDelete.addHeader(HTTP.CONTENT_TYPE, "application/json");


			httpDelete.addHeader(header);
			CloseableHttpResponse response = httpclient.execute(httpDelete);
			HttpEntity entity = response.getEntity();
			return EntityUtils.toString(entity, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	public static String convertObjectToString(Object object) {
		String obj = null;
		ObjectMapper map = new ObjectMapper();
		try {
			obj = map.writeValueAsString(object);
		} catch (IOException e) {
		}
		return obj;
	}

	public static InputStream downloadXML(final String urlStr) {
		InputStream inStream = null;
		URL url = null;
		try {
			url = new URL(urlStr);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpURLConnection conn = null;
		try {
			conn = (HttpURLConnection) url.openConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		conn.setConnectTimeout(5 * 1000);
		try {
			conn.setRequestMethod("GET");
			conn.connect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}

		try {
			inStream = conn.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return inStream;
	}

	public static InputStream downloaFtpdXML(final String urlStr) {
		InputStream inStream = null;
		URL url = null;
		try {
			url = new URL(urlStr);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FtpURLConnection conn = null;
		try {
			conn = (FtpURLConnection) url.openConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		conn.setConnectTimeout(5 * 1000);
		try {
			conn.connect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}

		try {
			inStream = conn.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return inStream;
	}

	public static String convertStreamToString(InputStream is) throws UnsupportedEncodingException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	/**
	 * 从网络Url中下载文件
	 *
	 * @param urlStr
	 * @param fileName
	 * @param savePath
	 * @throws IOException
	 */
	public static void downLoadFromUrl(String urlStr, String fileName, String savePath) throws IOException {
		URL url = new URL(urlStr);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		//文件保存位置
		File saveDir = new File(savePath);
		if (!saveDir.exists()) {
			saveDir.mkdir();
		}
		File file = new File(saveDir + File.separator + fileName);

		//设置超时间为3秒
		conn.setConnectTimeout(3 * 1000);
		//防止屏蔽程序抓取而返回403错误
		conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:53.0) Gecko/20100101 Firefox/53.0");
		if (file.exists()) {
            /*conn.setRequestProperty("RANGE","bytes="+file.length());
			//得到输入流
			InputStream inputStream = conn.getInputStream();
			RandomAccessFile oSavedFile = new RandomAccessFile(saveDir+File.separator+fileName,"rw");
			long nPos = file.length();
			// 定位文件指针到 nPos 位置
			oSavedFile.seek(nPos);
			byte[] getData = readInputStream(inputStream);
			oSavedFile.write(getData);
			if(inputStream!=null){
				inputStream.close();
			}
			if (oSavedFile != null){
				oSavedFile.close();
			}*/

			return;
		} else {

			//得到输入流
			InputStream inputStream = conn.getInputStream();
			//获取自己数组
			byte[] getData = readInputStream(inputStream);


			FileOutputStream fos = new FileOutputStream(file);
			fos.write(getData);
			if (fos != null) {
				fos.close();
			}
			if (inputStream != null) {
				inputStream.close();
			}
		}


		//System.out.println("info:"+url+" download success");

	}


	/**
	 * 从输入流中获取字节数组
	 *
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	public static byte[] readInputStream(InputStream inputStream) throws IOException {
		byte[] buffer = new byte[1024];
		int len = 0;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		while ((len = inputStream.read(buffer)) != -1) {
			bos.write(buffer, 0, len);
		}
		bos.close();
		return bos.toByteArray();
	}

	public static String postFiles(String url, MultipartFile[] images, Map<String, String> paramMap, Header header) {
		JSONObject result = new JSONObject();
		org.apache.http.client.HttpClient httpClient = org.apache.http.impl.client.HttpClients.createDefault();
		HttpPost httppost = new HttpPost(url);
		MultipartEntityBuilder mEntityBuilder = MultipartEntityBuilder.create();
		try {
			if (images != null) {
				for (MultipartFile image : images) {
					String filename = image.getOriginalFilename();
					if (StringUtils.isEmpty(filename)) {
						filename = UUIDUtil.getUUID() + ".jpg";
					}
					String suffix = filename.substring(filename.lastIndexOf(".")).toLowerCase();

					if (filename.length() > 64) {
						filename = filename.substring(0, 63) + suffix;
					}
					mEntityBuilder.addBinaryBody("images", image.getBytes(), ContentType.DEFAULT_BINARY, filename);

				}
				for (String key : paramMap.keySet()) {
					mEntityBuilder.addTextBody(key, paramMap.get(key));
				}

				httppost.setEntity(mEntityBuilder.build());
				httppost.addHeader(header);
				HttpResponse responce = httpClient.execute(httppost);
				int resStatu = responce.getStatusLine().getStatusCode();
				result.put("code", resStatu);
				if (resStatu == HttpStatus.SC_OK) {
					org.apache.http.HttpEntity entity = responce.getEntity();
					result.put("message", "成功");
					result.put("data", EntityUtils.toString(entity));
					return result.toJSONString();
				} else {
					logger.info(url + ": resStatu is " + resStatu);
					result.put("message", "失败");
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}


	public static String postFile(String url, MultipartFile image) {
		JSONObject result = new JSONObject();
		org.apache.http.client.HttpClient httpClient = org.apache.http.impl.client.HttpClients.createDefault();
		HttpPost httppost = new HttpPost(url);
		MultipartEntityBuilder mEntityBuilder = MultipartEntityBuilder.create();
		try {
			if (image != null) {
				String filename = image.getOriginalFilename();
				if (StringUtils.isEmpty(filename)) {
					filename = UUIDUtil.getUUID() + ".jpg";
				}
				String suffix = filename.substring(filename.lastIndexOf(".")).toLowerCase();

				if (filename.length() > 64) {
					filename = filename.substring(0, 63) + suffix;
				}
				mEntityBuilder.addBinaryBody("imageData", image.getBytes(), ContentType.DEFAULT_BINARY, filename);
				httppost.setEntity(mEntityBuilder.build());
				HttpResponse responce = httpClient.execute(httppost);
				int resStatu = responce.getStatusLine().getStatusCode();
				result.put("code", resStatu);
				if (resStatu == HttpStatus.SC_OK) {
					org.apache.http.HttpEntity entity = responce.getEntity();
					result.put("message", "成功");
					result.put("data", EntityUtils.toString(entity));
					return result.toJSONString();
				} else {
					logger.info(url + ": resStatu is " + resStatu);
					result.put("message", "失败");
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}


	public static String postFileData2(String url, MultipartFile image, Map<String, Object> map, Header header) {
		org.apache.http.client.HttpClient httpClient = org.apache.http.impl.client.HttpClients.createDefault();
		HttpPost httppost = new HttpPost(url);
		MultipartEntityBuilder mEntityBuilder = MultipartEntityBuilder.create();
		try {
			if (image != null) {
				String filename = image.getOriginalFilename();
				if (StringUtils.isEmpty(filename)) {
					filename = UUIDUtil.getUUID() + ".jpg";
				}
				String suffix = filename.substring(filename.lastIndexOf(".")).toLowerCase();

				if (filename.length() > 64) {
					filename = filename.substring(0, 63) + suffix;
				}
				mEntityBuilder.addBinaryBody("image", image.getBytes(), ContentType.DEFAULT_BINARY, filename);

				for (String key : map.keySet()) {
					mEntityBuilder.addTextBody(key, map.get(key).toString());
				}

				httppost.setEntity(mEntityBuilder.build());
				httppost.addHeader(header);
				HttpResponse response = httpClient.execute(httppost);
				int resStatus = response.getStatusLine().getStatusCode();
				if (resStatus == HttpStatus.SC_OK) {
					org.apache.http.HttpEntity entity = response.getEntity();
					String result = EntityUtils.toString(entity);
					logger.info(result);
					return result;
				} else {
					HttpEntity entity = response.getEntity();
					logger.info(EntityUtils.toString(entity, "utf-8"));
					logger.info(url + ": resStatus is " + resStatus);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}


	public static String postFileData(String url, MultipartFile image, Header header) {
		JSONObject result = new JSONObject();
		org.apache.http.client.HttpClient httpClient = org.apache.http.impl.client.HttpClients.createDefault();
		HttpPost httppost = new HttpPost(url);
		MultipartEntityBuilder mEntityBuilder = MultipartEntityBuilder.create();
		try {
			if (image != null) {
				String filename = image.getOriginalFilename();
				if (StringUtils.isEmpty(filename)) {
					filename = UUIDUtil.getUUID() + ".jpg";
				}
				String suffix = filename.substring(filename.lastIndexOf(".")).toLowerCase();

				if (filename.length() > 64) {
					filename = filename.substring(0, 63) + suffix;
				}
				mEntityBuilder.addBinaryBody("file", image.getBytes(), ContentType.DEFAULT_BINARY, filename);
				mEntityBuilder.addTextBody("base64Image", "");
				mEntityBuilder.addTextBody("imageUuid", "");
				httppost.setEntity(mEntityBuilder.build());
				httppost.addHeader(header);
				HttpResponse responce = httpClient.execute(httppost);
				int resStatu = responce.getStatusLine().getStatusCode();
				if (resStatu == HttpStatus.SC_OK) {
					org.apache.http.HttpEntity entity = responce.getEntity();
					JSONObject dataObj = JSON.parseObject(EntityUtils.toString(entity));
					result.put("data", dataObj);
					logger.info(result.toJSONString());
					return result.toJSONString();
				} else {
					HttpEntity entity = responce.getEntity();
					logger.info(EntityUtils.toString(entity, "utf-8"));
					logger.info(url + ": resStatu is " + resStatu);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String postFiles(String url, Map<String, File> files) {
		JSONObject result = new JSONObject();
		org.apache.http.client.HttpClient httpClient = org.apache.http.impl.client.HttpClients.createDefault();
		HttpPost httppost = new HttpPost(url);
		MultipartEntityBuilder mEntityBuilder = MultipartEntityBuilder.create();
		for (String key : files.keySet()) {
			mEntityBuilder.addBinaryBody(key, files.get(key));
		}
		try {
			httppost.setEntity(mEntityBuilder.build());
			HttpResponse responce = httpClient.execute(httppost);
			int resStatu = responce.getStatusLine().getStatusCode();
			result.put("code", resStatu);
			if (resStatu == HttpStatus.SC_OK) {
				org.apache.http.HttpEntity entity = responce.getEntity();
				result.put("message", "成功");
				result.put("data", EntityUtils.toString(entity));
				return result.toJSONString();
			} else {
				logger.info(url + ": resStatu is " + resStatu);
				result.put("message", "失败");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String postFiles2WithHeader(String url, Map<String, Object> map, MultipartFile multipartFile, Header header) {
		JSONObject result = new JSONObject();
		org.apache.http.client.HttpClient httpClient = org.apache.http.impl.client.HttpClients.createDefault();
		HttpPost httppost = new HttpPost(url);
		MultipartEntityBuilder mEntityBuilder = MultipartEntityBuilder.create();
		for (String key : map.keySet()) {
			mEntityBuilder.addTextBody(key, map.get(key).toString());
		}
		try {
			String filename = multipartFile.getOriginalFilename();
			if (StringUtils.isEmpty(filename)) {
				filename = UUIDUtil.getUUID() + ".jpg";
			}
			String suffix = filename.substring(filename.lastIndexOf(".")).toLowerCase();

			if (filename.length() > 64) {
				filename = filename.substring(0, 63) + suffix;
			}
			mEntityBuilder.addBinaryBody("images", multipartFile.getBytes(), ContentType.DEFAULT_BINARY, filename);
			httppost.setEntity(mEntityBuilder.build());
			httppost.addHeader(header);
			HttpResponse response = httpClient.execute(httppost);
			int resStatus = response.getStatusLine().getStatusCode();
			result.put("code", resStatus);
			if (resStatus == HttpStatus.SC_OK) {
				org.apache.http.HttpEntity entity = response.getEntity();
				result.put("message", "成功");
				result.put("data", EntityUtils.toString(entity));
				return result.toJSONString();
			} else {
				logger.info(url + ": resStatu is " + resStatus);
				result.put("message", "失败");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String deleteByJsonWithHeader(String url, String encoderJson, Header header) {

		try {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpDeleteWithBody httpDelete = new HttpDeleteWithBody(url);
			httpDelete.addHeader(HTTP.CONTENT_TYPE, "application/json");
			StringEntity se = new StringEntity(encoderJson, Charset.forName("utf-8"));
			/*se.setContentType("text/json");
			se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));*/
			httpDelete.setEntity(se);
			httpDelete.addHeader(header);
			CloseableHttpResponse response = httpclient.execute(httpDelete);
			HttpEntity entity = response.getEntity();
			return EntityUtils.toString(entity, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String postCode(String url, String encoderJson) {
		try {

			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(url);
			httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");
			StringEntity se = new StringEntity(encoderJson, Charset.forName("utf-8"));
			/*se.setContentType("text/json");
			se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));*/
			httpPost.setEntity(se);
			CloseableHttpResponse response = httpclient.execute(httpPost);
			int code = response.getStatusLine().getStatusCode();
			HttpEntity entity = response.getEntity();
			JSONObject data = new JSONObject();
			data.put("data",EntityUtils.toString(entity, "utf-8"));
			data.put("code",code);
			return data.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String deleteCode(String url, Header header) {
		try {

			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpDelete httpDelete = new HttpDelete(url);

			httpDelete.addHeader(HTTP.CONTENT_TYPE, "application/json");


			httpDelete.addHeader(header);
			CloseableHttpResponse response = httpclient.execute(httpDelete);
			int code = response.getStatusLine().getStatusCode();
			HttpEntity entity = response.getEntity();
			JSONObject data = new JSONObject();
			data.put("data",EntityUtils.toString(entity, "utf-8"));
			data.put("code",code);
			return data.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
