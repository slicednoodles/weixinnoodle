package com.noodle.travel.assistant.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.noodle.travel.assistant.util.AllConstants;
import com.noodle.travel.assistant.util.CacheUtils;

public class DbConnection {

	private String dbDriver; // 与本地设置相同
	private String dbUrl; // 为新浪app数据库名称，开通mysql服务后，通过[服务管理]-〉[MySql]->[管理MySql]中，查看数据库名称
	private String dbUser; // 为[应用信息]->[汇总信息]->[key]中的access key
	private String dbPassword; // 为[应用信息]->[汇总信息]->[key]中的secret
								// key

	public DbConnection() {
		this.dbDriver = CacheUtils.config.getString("dbDriver");
		this.dbUrl = CacheUtils.config.getString("dbUrl");
		this.dbUser = CacheUtils.config.getString("dbUser");
		this.dbPassword = CacheUtils.config.getString("dbPassword");
	}

	private Connection createConnection() throws Exception {
		try {
			Class.forName(dbDriver);
			Connection connection = DriverManager.getConnection(dbUrl, dbUser,
					dbPassword);
			return connection;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public ResultSet selectAllFromTravelRecord() {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			connection = createConnection();
			statement = connection
					.prepareStatement(AllConstants.SELECT_ALL_FROM_TRAVEL_RECORD);
			result = statement.executeQuery();
			if (result != null) {
				while (result.next()) {
					String keyword = result.getString("KEYWORD");
					String type = result.getString("TYPE");
					String xmlContent = result.getString("XML_CONTENT");
					if (StringUtils.isNotEmpty(keyword)
							&& StringUtils.isNotEmpty(type)) {
						if (CacheUtils.map.get(type) != null) {
							CacheUtils.map.get(type).put(keyword, xmlContent);
						} else {
							Map<String, String> keywordMap = new HashMap<String, String>();
							keywordMap.put(keyword, xmlContent);
							CacheUtils.map.put(type, keywordMap);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (statement != null)
					statement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public void insertToTravelRecord(String type, String keyword,
			String xmlContent) {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = createConnection();
			statement = connection
					.prepareStatement(AllConstants.INSERT_TO_TRAVEL_RECORD);
			statement.setString(1, type);
			statement.setString(2, keyword);
			statement.setString(3, xmlContent);
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (statement != null)
					statement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void updateToTravelRecord(String type, String keyword,
			String xmlContent) {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = createConnection();
			statement = connection
					.prepareStatement(AllConstants.UPDATE_TO_TRAVEL_RECORD);
			statement.setString(1, xmlContent);
			statement.setString(2, type);
			statement.setString(3, keyword);
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (statement != null)
					statement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
