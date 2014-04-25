package com.noodle.common.cache;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import com.noodle.common.dao.DbConnection;
import com.noodle.common.utils.AllConstants;
import com.noodle.weixin.pojo.VoiceMessage;

public class Cache {

	public static List<VoiceMessage> tucaoCache = new ArrayList<VoiceMessage>();

	public static PropertiesConfiguration config = null;
	public static Map<String, Map<String, String>> travelCache = new HashMap<String, Map<String, String>>();

	public static void init(String contextPath) throws ConfigurationException {
		config = new PropertiesConfiguration(contextPath + File.separator
				+ AllConstants.WEB_INF + File.separator
				+ AllConstants.PROPERTIES_FILE);
	}

	public static void getDataToCache() throws Exception {
		DbConnection db = new DbConnection();
		db.selectAllFromTravelRecord();
		db.selectAllFromWeixinMessageRecord(AllConstants.TU_CAO);
	}

	public static String getResultFromCache(String type, String keyword) {
		if (travelCache.get(type) != null) {
			System.out.println("get xml content from cache, type = " + type
					+ ". keyword = " + keyword);
			return travelCache.get(type).get(keyword);
		}
		return null;
	}

	public static String getRandom(String type) {
		Map<String, String> m = travelCache.get(type);
		if (m != null && !m.isEmpty()) {
			int i = new Random().nextInt(m.size());
			int j = 0;
			for (Entry<String, String> e : m.entrySet()) {
				if (i == j) {
					return e.getValue();
				}
				j++;
			}
		}
		return null;
	}
}
