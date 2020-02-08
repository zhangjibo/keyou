package cases;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.testng.Assert;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;

import consts.Const;
import io.qameta.allure.Step;
import pojo.API;
import pojo.Case;
import pojo.JsonPathAssert;
import pojo.WriteBackData;
import utils.EnvironmentUtils;
import utils.ExcelUtils;
import utils.HttpUtils;
import utils.SQLUtils;

public abstract class BaseCase {

    private Logger log = Logger.getLogger(BaseCase.class);

    Object beforeRequestSqlCheck = null;
    Object afterRequestSqlCheck = null;
    String sqlCheck = null;
    String passContent = "Fail";

    /**
     * 初始化方法
     */
    protected void init() {

	// 配置参数化变量
	EnvironmentUtils.evn.put(Const.REGISTER_USERNAME_TEXT, Const.REGISTER_USERNAME_VALUE);
	EnvironmentUtils.evn.put(Const.REGISTER_EMAIL_TEXT, Const.REGISTER_EMAIL_VALUE);
	EnvironmentUtils.evn.put(Const.ADDPROJECTNAME_TEXT, Const.ADDPROJECTNAME_VALUE);
	//EnvironmentUtils.evn.put(Const.LOGIN_PASSWORD_TEXT, Const.LOGIN_PASSWORD_VALUE);
    }

    /**
     * 接口公共的流程处理方法
     * 
     * @param api
     * @param c
     */
    protected void test(API api, Case c) {

	log.info("======================参数化替换======================" + api.getUrl());
	String params = paramReplace(c.getParams());
	String sqlCheck = paramReplace(c.getSqlCheck());
	log.info("替换后的参数：" + params);
	log.info("sql校验：" + sqlCheck);
	c.setParams(params);
	c.setSqlCheck(sqlCheck);

	String url = api.getUrl();
	String type = api.getType();
	String contentType = api.getContentType();

	// 接口调用前数据库查询结果

	beforeRequestSqlCheck = SQLUtils.query(sqlCheck);

	// 调用接口获取实际响应
	String content = HttpUtils.call(url, params, type, contentType);

	// 行号
	int rowNum = c.getId();
	// 实际响应列号
	int actualResponseCellNum = Const.ACTUAL_RESPONSE;
	WriteBackData writeBackActualResponse = new WriteBackData(rowNum, actualResponseCellNum, content);

	// 实际响应添加到回写集合中
	ExcelUtils.wbdlist.add(writeBackActualResponse);

	// 获取期望数据
	String expectedData = c.getExpectedData();

	boolean assertResponseData = assertResponseData(expectedData, content);
	log.info("响应断言：" + assertResponseData);

	// 接口调用后数据库查询结果

	afterRequestSqlCheck = SQLUtils.query(sqlCheck);

	// 断言数据库结果
	boolean assertSql = true;
	if (StringUtils.isNoneBlank(sqlCheck)) {
	    assertSql = assertSql(beforeRequestSqlCheck, afterRequestSqlCheck, c);
	    log.info("数据库断言结果：" + assertSql);
	}
	// 响应结果和数据库结果都通过则Pass,否则Fail
	passContent = (assertResponseData && assertSql) ? "Pass" : "Fail";
	log.info("结果断言：" + passContent);

	// 断言列号
	int assertCellNum = Const.ASSERT;
	// 添加回写断言
	WriteBackData writeAssert = new WriteBackData(rowNum, assertCellNum, passContent);
	// 把断言添加到回写集合中
	ExcelUtils.wbdlist.add(writeAssert);
	// 报表断言
	Assert.assertEquals(passContent, "Pass");

    }

    /**
     * 参数化替换方法
     * 
     * @param params
     * @return
     */

    protected String paramReplace(String params) {
	if (StringUtils.isBlank(params)) {
	    return null;
	}
	Set<String> keySet = EnvironmentUtils.evn.keySet();
	for (String key : keySet) {
	    String value = EnvironmentUtils.evn.get(key);
	    params = params.replace(key, value);
	}
	return params;
    }

    /**
     * sql断言抽象方法
     * 
     * @param beforeRequestSqlCheck
     * @param afterRequestSqlCheck
     * @return
     */
    public abstract boolean assertSql(Object beforeRequestSqlCheck, Object afterRequestSqlCheck, Case c);

    /**
     * 断言接口响应内容
     * 
     * @param expectedData 期望值
     * @param content      实际响应
     * @return
     */
    @Step("预期结果{expectedData}《====》实际结果{content}")
    protected boolean assertResponseData(String expectedData, String content) {

	Object parse = JSONObject.parse(expectedData);
	// 判断是不是JSONArray.若是数组类型,就采用多关键字匹配
	if (parse instanceof JSONArray) {

	    // 将期望数据转成list集合
	    List<JsonPathAssert> array = JSONObject.parseArray(expectedData, JsonPathAssert.class);
	    // 循环集合
	    for (JsonPathAssert jsonPathValue : array) {
		// 通过jsonPath表达式获取到实际响应的实际值
		Object realValue = JSONPath.read(content, jsonPathValue.getExpression());
		// 拿期望值和实际值比较
//		boolean flag = jsonPathValue.getValue().equals(realValue.toString());
		boolean flag = realValue.toString().equals(jsonPathValue.getValue());
		log.info("实际值：" + realValue + " | 期望值：" + expectedData + "断言结果：" + flag);

		if (flag == false) {
		    return false;
		}
	    }
	    return true;

	    // 判断是不是JSONObject,若是对象类型,就采用等值匹配
	} else if (parse instanceof JSONObject) {
	    boolean flag = expectedData.equals(content);
	    return flag;
	}
	return false;

    }

    /**
     * 套件执行完毕之后,执行批量回写
     */
    protected void finish() {

	// 将回写集合中的断言结果回写到excel中
	ExcelUtils.write(Const.SRC_PATH, 1);

	log.info("-=-=-=-=-=回写完毕！--=-=-=-=-=-=");
    }

}
