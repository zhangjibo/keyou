package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import consts.Const;



public class JDBCUtils {

	/**
	 * 获取数据库连接
	 * @return
	 */
	public static Connection getConnection() {
		//定义数据库连接对象
		Connection conn = null;
		try {
			//你导入的数据库驱动包， mysql。
			//jdbc:mysql://api.lemonban.com:3306/futureloan?useUnicode=true&characterEncoding=utf-8
			//jdbc:数据库名称://数据库IP:端口/数据库名称?参数
			conn = DriverManager.getConnection(Const.JDBC_URL, Const.JDBC_USER, Const.JDBC_PASSWORD);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	/**
	 * 关闭数据库连接
	 * @param conn
	 */
	public static void close(Connection conn) {
		try {
			if(conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}