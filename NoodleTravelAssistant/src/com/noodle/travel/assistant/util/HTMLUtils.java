package com.noodle.travel.assistant.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTMLUtils {

	public static String getHtml(String urlStr) throws Exception {
		URL url = null;
		HttpURLConnection connection = null;
		String temp;
		StringBuilder sb = new StringBuilder();
		try {
			url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			InputStreamReader isr = new InputStreamReader(conn.getInputStream());
			BufferedReader br = new BufferedReader(isr);
			while ((temp = br.readLine()) != null) {
				sb.append(temp);
			}
			br.close();
			isr.close();
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
		return null;
	}
}
