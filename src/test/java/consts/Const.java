package consts;

import java.util.HashMap;
import java.util.Map;

public class Const {

    // excel路径
    public static final String SRC_PATH = "src/test/resources/keyou.xlsx";

    // excel中实际响应的列号
    public static final int ACTUAL_RESPONSE = 5;
    
    // excel中断言的列号
    public static final int ASSERT = 6;

    // 数据库连接url
    public static final String JDBC_URL = "jdbc:mysql://api.lemonban.com:3306/futureloan?useUnicode=true&characterEncoding=utf-8";
    // 数据库连接用户名
    public static final String JDBC_USER = "future";
    // 数据库连接密码
    public static final String JDBC_PASSWORD = "123456";
    
    //参数化替换字段
    public static final String REGISTER_USERNAME_TEXT = "${toBeRegisterUsername}";
    public static final String REGISTER_EMAIL_TEXT = "${toBeRegisterEmail}";
    public static final String ADDPROJECTNAME_TEXT = "${toBeAddProjectsName}";
    public static final String LOGIN_PASSWORD_TEXT = "${toBeLoginPassword}";
    public static final String MEMBER_ID = "${member_id}";

    //参数化替换值
    public static final String REGISTER_USERNAME_VALUE = getRandom();
    public static final String REGISTER_EMAIL_VALUE = getRandom() +"@qq.cpm";
    public static final String ADDPROJECTNAME_VALUE = "柠檬" + getRandom();
    public static final String LOGIN_PASSWORD_VALUE = "12345678";	
    
    public static int getNum(int start,int end) {
        return (int)(Math.random()*(end-start+1)+start);
    }
    	

    /**
     * 返回手机号码 
     */
    public static String getRandom() {
	String[] telFirst="134,135,136,137,138,139,150,151,152,157,158,159,130,131,132,155,156,133,153".split(",");
        int index=getNum(0,telFirst.length-1);
        String first=telFirst[index];
        String second=String.valueOf(getNum(1,888)+10000).substring(1);
        String third=String.valueOf(getNum(1,9100)+10000).substring(1);
        return first+second+third;
    }
    public static final Map<String, String> map = new HashMap<String, String>();
    	
    static {

    	map.put(REGISTER_USERNAME_TEXT,REGISTER_USERNAME_VALUE);
    	map.put(REGISTER_EMAIL_TEXT,REGISTER_EMAIL_VALUE);
    	map.put(ADDPROJECTNAME_TEXT,ADDPROJECTNAME_VALUE);
    	map.put(LOGIN_PASSWORD_TEXT,LOGIN_PASSWORD_VALUE);
    }

}
