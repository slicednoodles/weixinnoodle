package com.noodle.travel.assistant.task;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.noodle.travel.assistant.dao.DbConnection;
import com.noodle.travel.assistant.util.CacheUtils;
import com.noodle.travel.assistant.util.HTMLUtils;

public class AckTask {

	public static void saveRecord(final String type, final String keyword,
			final String xmlContent) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				DbConnection db = new DbConnection();
				if (CacheUtils.map.get(type) != null
						&& CacheUtils.map.get(type).get(keyword) != null) {
					if (!CacheUtils.map.get(type).get(keyword)
							.equalsIgnoreCase(xmlContent)) {
						db.updateToTravelRecord(type, keyword, xmlContent);
						CacheUtils.map.get(type).put(keyword, xmlContent);
					}
				} else {
					db.insertToTravelRecord(type, keyword, xmlContent);
					if (CacheUtils.map.get(type) != null) {
						CacheUtils.map.get(type).put(keyword, xmlContent);
					} else {
						Map<String, String> m = new HashMap<String, String>();
						m.put(keyword, xmlContent);
						CacheUtils.map.put(type, m);
					}
				}
			}
		}, "saveRecord").start();
	}

	public static void getImpressAck(final String keyword) throws Exception {
		new Thread(new Runnable() {
			@Override
			public void run() {
				String result = null;
				try {
					result = HTMLUtils.getImpressFromBD(keyword);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (StringUtils.isNotEmpty(result)) {
					saveRecord("impress", keyword, result);
				}
			}
		}, "saveRecord").start();
	}

}
