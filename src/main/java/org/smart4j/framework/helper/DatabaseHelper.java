package org.smart4j.framework.helper;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.smart4j.framework.utils.DBUtil;

/**
 * 数据库操作助手类
 * 
 * @author zxw
 *
 */
public abstract class DatabaseHelper {
	private static final ThreadLocal<Connection> CONNECTION_HOLDER = new ThreadLocal<>();

	/**
	 * 开启事务
	 */
	public static void beginTransaction() {
		Connection conn = DBUtil.getConnection();
		if (conn != null) {
			try {
				conn.setAutoCommit(false);
				System.out.println("事务开启");
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException();
			} finally {
				CONNECTION_HOLDER.set(conn);
			}
		}
	}

	/**
	 * 提交事务
	 */
	public static void commitTransaction() {
		Connection conn = DBUtil.getConnection();
		if (conn != null) {
			try {
				System.out.println("事务提交");
				conn.commit();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				CONNECTION_HOLDER.remove();
			}
		}
	}

	/**
	 * 回滚事务
	 */
	public static void rollbackTransaction() {
		Connection conn = DBUtil.getConnection();
		if (conn != null) {
			try {
				System.out.println("事务回滚");
				conn.rollback();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				CONNECTION_HOLDER.remove();
			}
		}
	}
}
