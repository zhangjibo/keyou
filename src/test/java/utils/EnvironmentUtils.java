package utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.methods.HttpRequestBase;

import com.alibaba.fastjson.JSONPath;

import consts.Const;

public class EnvironmentUtils {
    
    //环境变量
    public static Map<String, String> evn = new HashMap<String, String>();
    
    /**
     * 从响应体中获取token信息存储到缓存中
     * @param responseBody
     */
    public static void storeToken(String responseBody) {
	//取出token
	Object token = JSONPath.read(responseBody, "$.token");
	//存储
	if(token != null) {
	    evn.put("token", String.valueOf(token));
	    Object member_id = JSONPath.read(responseBody, "$.data.id");
	    if(member_id != null) {
		evn.put(Const.MEMBER_ID, member_id.toString());
	    }
	}
	
    }
    
    /**
     * 添加token到请求头
     * @param request
     */
    public static void addToken(HttpRequestBase request) {
	String token = evn.get("token");
	if(token != null) {
	    request.addHeader("Authorization", "JWT " + token);
	}
	
    }

}
