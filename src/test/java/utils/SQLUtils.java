package utils;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang3.StringUtils;

public class SQLUtils {

    /**
     * 调用DBUtils执行对应的查询sql语句，并返回查询结果。
     * 
     * @param sqlCheck
     * @return
     */
    public static Object query(String sqlCheck) {
	if (StringUtils.isBlank(sqlCheck)) {
	    return null;
	}
	// 创建QueryRunner对象
	QueryRunner qr = new QueryRunner();
	// 获取数据连接
	Connection conn = JDBCUtils.getConnection();
	try {
	    // 创建返回结果类型对象
	    @SuppressWarnings("rawtypes")
	    ScalarHandler rsh = new ScalarHandler();
	    // 执行sql查询语句
	    @SuppressWarnings("unchecked")
	    Object result = qr.query(conn, sqlCheck, rsh);
	    return result;
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return null;
    }
}
