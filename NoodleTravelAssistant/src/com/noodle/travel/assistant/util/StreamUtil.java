package com.noodle.travel.assistant.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class StreamUtil {
	public static String url2String(String url) throws Exception{
		URL resjson = new URL(url);
		BufferedReader in = new BufferedReader(new InputStreamReader(resjson.openStream()));
		String res;
		StringBuilder sb = new StringBuilder("");
		while ((res = in.readLine()) != null) {
			sb.append(res);
		}
		in.close();
		return sb.toString();
	}
}
