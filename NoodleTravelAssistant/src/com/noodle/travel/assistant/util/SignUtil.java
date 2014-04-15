package com.noodle.travel.assistant.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.TreeMap;

/**
 * 请求校验工具类
 * 
 * @author liufeng
 * @date 2013-05-18
 */
public class SignUtil {
	// 与接口配置信息中的Token要一致
	private static String token = "12341234";

	public static String appendParams(String timestamp, String nonce) {
		TreeMap<String, String> treeMap = new TreeMap<String, String>();
/*
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");*/

		treeMap.put(timestamp, "");
		treeMap.put(nonce, "");
		treeMap.put("12341234", "");

		StringBuffer result = new StringBuffer();
		Iterator<String> it = treeMap.keySet().iterator();

		while (it.hasNext()) {
			result.append(it.next());
		}

		return result.toString();
	}

	public static String encryptBySHA1(String src) {
		byte[] bytes = null;
		StringBuffer result = new StringBuffer();

		try {
			MessageDigest md = MessageDigest.getInstance("SHA");
			md.update(src.getBytes("utf-8"));
			bytes = md.digest();

			for (int i = 0; i < bytes.length; i++) {
				result.append(Integer.toHexString((0x000000ff & bytes[i]) | 0xffffff00).substring(6));
			}
		} catch (Exception ex) {
			return null;
		}

		return result.toString();
	}
}
