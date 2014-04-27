package com.noodle.v5.task;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.noodle.common.cache.Cache;
import com.noodle.common.dao.DbConnection;
import com.noodle.common.utils.HTMLUtils;
import com.noodle.v5.service.TourService;

public class AckTask {

	public static void saveRecord(final String type, final String keyword,
			final String xmlContent) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				DbConnection db = new DbConnection();
				if (Cache.travelCache.get(type) != null
						&& Cache.travelCache.get(type).get(keyword) != null) {
					if (!Cache.travelCache.get(type).get(keyword)
							.equalsIgnoreCase(xmlContent)) {
						db.updateToTravelRecord(type, keyword, xmlContent);
						Cache.travelCache.get(type).put(keyword, xmlContent);
					}
				} else {
					db.insertToTravelRecord(type, keyword, xmlContent);
					if (Cache.travelCache.get(type) != null) {
						Cache.travelCache.get(type).put(keyword, xmlContent);
					} else {
						Map<String, String> m = new HashMap<String, String>();
						m.put(keyword, xmlContent);
						Cache.travelCache.put(type, m);
					}
				}
			}
		}, "saveRecord").start();
	}

	public static void getImpressAck(final String url, final String keyword)
			throws Exception {
		new Thread(new Runnable() {
			@Override
			public void run() {
				String result = null;
				try {
					result = TourService.getImpressFromBD(url, keyword);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (StringUtils.isNotEmpty(result)) {
					saveRecord("impress", keyword, result);
				}
			}
		}, "getImpressAck").start();
	}

	public static void saveMessage(final String type, final String subType,
			final String message) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				DbConnection db = new DbConnection();
				db.insertToMessageTable(type, subType, message);
			}
		}, "saveMessage").start();
	}

}
