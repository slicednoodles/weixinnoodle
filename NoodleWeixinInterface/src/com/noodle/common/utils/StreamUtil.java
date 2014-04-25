package com.noodle.common.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class StreamUtil {
	public static String url2String(String url) throws Exception{
		URL resjson = new URL(url);
//		System.setProperty("http.proxyHost", "web-proxy.rose.hp.com");  
//        System.setProperty("http.proxyPort", "8080");  
		BufferedReader in = new BufferedReader(new InputStreamReader(resjson.openStream(), "UTF-8"));
		String res;
		StringBuilder sb = new StringBuilder("");
		while ((res = in.readLine()) != null) {
			sb.append(res);
		}
		in.close();
		return sb.toString();
	}
}
