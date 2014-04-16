package com.noodle.travel.assistant.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import com.noodle.travel.assistant.dao.DbConnection;

public class CacheUtils {

	public static PropertiesConfiguration config = null;
	public static Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();

	public static void init(String contextPath) throws ConfigurationException {
		config = new PropertiesConfiguration(contextPath + File.separator
				+ AllConstants.WEB_INF + File.separator
				+ AllConstants.PROPERTIES_FILE);
	}

	public static void getDataToCache() throws Exception {
		DbConnection db = new DbConnection();
		db.selectAllFromTravelRecord();
	}

	public static String getResultFromCache(String type, String keyword) {
		if (map.get(type) != null) {
			System.out.println("get xml content from cache, type = " + type
					+ ". keyword = " + keyword);
			return map.get(type).get(keyword);
		}
		return null;
	}

	public static String getRandom(String type) {
		Map<String, String> m = map.get(type);
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
