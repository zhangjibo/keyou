package utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

import io.qameta.allure.Step;

public class HttpUtils {

    private static Logger log = Logger.getLogger(HttpUtils.class);

    public static BasicHeader mediaTypeHeader = new BasicHeader("X-Lemonban-Media-Type", "lemonban.v2");
    public static BasicHeader formContentTypeHeader = new BasicHeader("Content-Type",
	    "application/x-www-form-urlencoded");
    public static BasicHeader jsonContentTypeHeader = new BasicHeader("Content-Type", "application/json");

    /**
     * 接口调用的方法
     * 
     * @param url
     * @param params
     * @param type
     * @param contentType
     * @return
     */
    @Step("接口调用{url}/{params}")
    public static String call(String url, String params, String type, String contentType) {
	if ("get".equalsIgnoreCase(type)) {
	    return HttpUtils.testGet(url, params);

	} else if ("post".equalsIgnoreCase(type)) {

	    return jsonOrFormMethod(url, params, contentType);

	}

	return null;
    }

    /**
     * get请求
     * 
     * @param url
     * @param params
     * @return
     */
    public static String testGet(String url, String params) {

	// 1.创建一个request，携带了method和url
	HttpGet get = new HttpGet(url);
	// 1.1添加请求头
	get.addHeader(mediaTypeHeader);
	EnvironmentUtils.addToken(get);
	log.info("token：" + EnvironmentUtils.evn.get("token"));
	// get.addHeader("Content-Type", "application/json");

	// 2.创建发送请求的客户端,HttpClients是HttpClient的工具类
	CloseableHttpClient client = HttpClients.createDefault();

	// 3.客户端调用发送get请求,立即返回http响应
	CloseableHttpResponse response = null;
	try {
	    response = client.execute(get);
	} catch (IOException e) {
	    e.printStackTrace();
	}

	// 4.response整个响应对象
	// 4.1获取所有的响应头
	Header[] headers = response.getAllHeaders();
	// 4.2获取状态码
	int statusCode = response.getStatusLine().getStatusCode();
	// 4.3获取响应体
	HttpEntity entity = response.getEntity();
	// 4.4格式化响应体
	String body = null;
	try {
	    body = EntityUtils.toString(entity);
	    
	    log.info("请求头：" + Arrays.toString(headers));
	    log.info("状态码：" + statusCode);
	    log.info("响应体：" + body);
	    return body;

	} catch (IOException e) {
	    e.printStackTrace();
	}

	

	return null;

    }

    /**
     * post请求判断传参是json还是form格式
     * 
     * @param url
     * @param params
     * @param contentType
     * @return
     */
    public static String jsonOrFormMethod(String url, String params, String contentType) {
	if ("json".equalsIgnoreCase(contentType)) {
	    return HttpUtils.testPostJson(url, params);

	}else if ("form".equalsIgnoreCase(contentType)) {
	    // params是json格式,通过fastJson转成Map对象
	    String kvParams = jsonToKV(params);
	    return HttpUtils.testPostForm(url, kvParams);

	}
	return null;
    }

    /**
     * form格式的post请求
     * 
     * @param url
     * @param params
     * @return
     */
    public static String testPostForm(String url, String params) {
	try {
	    HttpPost post = new HttpPost(url);

	    // 添加请求头
	    post.addHeader(mediaTypeHeader);
	    post.addHeader(formContentTypeHeader);
	    // 请求头添加token
	    EnvironmentUtils.addToken(post);
	    log.info("token：" + EnvironmentUtils.evn.get("token"));
	    // 添加请求体
	    StringEntity postEntity = new StringEntity(params, "utf-8");

	    post.setEntity(postEntity);

	    // 发送请求的客户端
	    CloseableHttpClient client = HttpClients.createDefault();

	    // 发送post请求
	    CloseableHttpResponse response = client.execute(post);

	    // response整个响应对象(body, header, status_code)
	    Header[] headers = response.getAllHeaders(); // 所有的响应头

	    int statusCode = response.getStatusLine().getStatusCode(); // 获取状态码

	    HttpEntity responseEntity = response.getEntity(); // 获取响应体

	    String body = EntityUtils.toString(responseEntity); // 格式化响应体
	    // 存储token
	    EnvironmentUtils.storeToken(body);

	    log.info("请求头：" + Arrays.toString(headers));
	    log.info("请求参数：" + params);
	    log.info("状态码：" + statusCode);
	    log.info("响应体：" + body);

	    return body;

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return null;
    }

    /**
     * json格式转成form格式
     * 
     * @param params
     * @return
     */
    public static String jsonToKV(String params) {
	@SuppressWarnings("unchecked")
	HashMap<String, String> map = JSONObject.parseObject(params, HashMap.class);

	// 再将Map对象转成想要的字符串格式
	String kvParams = "";
	Set<String> keySet = map.keySet();
	for (String key : keySet) {
	    String value = map.get(key);
	    kvParams += key + "=" + value + "&";

	}
	kvParams = kvParams.substring(0, kvParams.length() - 1);
	return kvParams;
    }

    /**
     * json格式的post请求
     * 
     * @param url
     * @param params
     * @return
     */
    public static String testPostJson(String url, String params) {

	try {
	    HttpPost post = new HttpPost(url);

	    // 添加请求头
	    post.addHeader(mediaTypeHeader);
	    post.addHeader(jsonContentTypeHeader);

	    // 请求头添加token

	    log.info("token：" + EnvironmentUtils.evn.get("token"));
	    EnvironmentUtils.addToken(post);

	    // 添加请求体
	    StringEntity postEntity = new StringEntity(params, "utf-8");
	    post.setEntity(postEntity);

	    // 发送请求的客户端
	    CloseableHttpClient client = HttpClients.createDefault();

	    // 发送post请求
	    CloseableHttpResponse response = client.execute(post);

	    // response整个响应对象(body, header, status_code)
	    Header[] headers = response.getAllHeaders(); // 所有的响应头

	    int statusCode = response.getStatusLine().getStatusCode(); // 获取状态码

	    HttpEntity responseEntity = response.getEntity(); // 获取响应体

	    String body = EntityUtils.toString(responseEntity); // 格式化响应体

	    // 存储token
	    EnvironmentUtils.storeToken(body);

	    log.info("请求头：" + Arrays.toString(headers));
	    log.info("请求参数：" + params);
	    log.info("状态码：" + statusCode);
	    log.info("响应体：" + body);

	    return body;

	} catch (Exception e) {
	    e.printStackTrace();
	}
	return null;
    }

}
