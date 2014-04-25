package com.noodle.common.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;

import com.noodle.common.cache.Cache;
import com.noodle.common.utils.AllConstants;
import com.noodle.common.utils.MessageUtils;
import com.noodle.weixin.pojo.VoiceMessage;

public class DbConnection {

	private String dbDriver; // 与本地设置相同
	private String dbUrl; // 为新浪app数据库名称，开通mysql服务后，通过[服务管理]-〉[MySql]->[管理MySql]中，查看数据库名称
	private String dbUser; // 为[应用信息]->[汇总信息]->[key]中的access key
	private String dbPassword; // 为[应用信息]->[汇总信息]->[key]中的secret
								// key

	public DbConnection() {
		this.dbDriver = Cache.config.getString("dbDriver");
		this.dbUrl = Cache.config.getString("dbUrl");
		this.dbUser = Cache.config.getString("dbUser");
		this.dbPassword = Cache.config.getString("dbPassword");
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
						if (Cache.travelCache.get(type) != null) {
							Cache.travelCache.get(type)
									.put(keyword, xmlContent);
						} else {
							Map<String, String> keywordMap = new HashMap<String, String>();
							keywordMap.put(keyword, xmlContent);
							Cache.travelCache.put(type, keywordMap);
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

	public ResultSet selectAllFromWeixinMessageRecord(String type) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			connection = createConnection();
			statement = connection
					.prepareStatement(AllConstants.SELECT_ALL_FROM_WEIXIN_MESSAGE_RECORD_WITH_TYPE);
			statement.setString(1, type);
			result = statement.executeQuery();
			if (result != null) {
				while (result.next()) {
					String message = result.getString("MESSAGE");
					if (StringUtils.isNotEmpty(message)) {
						Document document = DocumentHelper.parseText(message);
						Map<String,String> map = MessageUtils.parseXml(document);
						VoiceMessage voiceMessage = new VoiceMessage();
						voiceMessage.setFromUserName(map.get(AllConstants.FROM_USER_NAME));
						voiceMessage.setFormat(map.get(AllConstants.VOICE_FORMAT));
						voiceMessage.setMediaId(map.get(AllConstants.MEDIA_ID));
						Cache.tucaoCache.add(voiceMessage);
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

	public void insertToMessageTable(String type, String subType, String message) {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = createConnection();
			statement = connection
					.prepareStatement(AllConstants.INSERT_TO_WEIXIN_MESSAGE_RECORD);
			statement.setString(1, type);
			statement.setString(2, subType);
			statement.setString(3, message);
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
