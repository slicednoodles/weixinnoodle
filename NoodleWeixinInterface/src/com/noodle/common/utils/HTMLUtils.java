package com.noodle.common.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.noodle.v5.task.AckTask;

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
	
	public static String getImpressFromBD(String keyword) throws Exception {
		String result = "";
		String url = "http://lvyou.baidu.com/"
				+ PinyinUtils.getStringPinYin(keyword) + "/jingdian/";
		String html = HTMLUtils.getHtml(url);
		if (!html.contains("error-back")) {
			Document doc = Jsoup.parse(html);
			Elements es = doc.getElementsByClass("scene-pic-container");
			Elements es1 = doc.getElementsByClass("scene-pic-figcaption");
			Elements es2 = doc.getElementsByClass("scene-pic-info");
			Elements es3 = doc.getElementsByClass("scene-pic-abstract");
			StringBuilder sb = new StringBuilder();
			String partContent = "";
			int size = 10;
			if (size > es.size()) {
				size = es.size();
			}
			for (int i = 0; i < size; i++) {
				String src = es.get(i).child(0).attr("src");
				if (StringUtils.isEmpty(src)) {
					src = es.get(i).child(0).child(0).attr("src");
				}
				String title = es1.get(i).child(0).attr("title");
				if (es1.get(i).childNodeSize() > 1) {
					title = es1.get(i).child(1).attr("title");
					if (StringUtils.isEmpty(title)) {
						title = es1.get(i).child(1).text();
					}
				}
				if (StringUtils.isEmpty(title)) {
					title = es1.get(i).child(0).attr("title");
				}
				if (StringUtils.isEmpty(title)) {
					title = es1.get(i).child(0).text();
				}
				String desc = "";
				if (es2.size() > 0) {
					desc = es2.get(i).child(0).text();
				}
				if (StringUtils.isEmpty(desc) && es3.size() > 0) {
					desc = es3.get(i).text();
				}
				if (NoodleStringUtils.isNumeric(desc)) {
					desc = title;
				} else {
					desc = title + " " + desc;
				}

				if (StringUtils.isNotEmpty(desc) && desc.length() > 28) {
					desc = desc.substring(0, 25) + "...";
				}
				partContent = AllConstants.WEI_XIN_IMAGE_ITEM.replace(
						AllConstants.NOODLE_PIC_URL, src).replace(
						AllConstants.NOODLE_IMAGE_TITLE, desc);
				String link = es1.get(i).child(0).attr("href");
				if (StringUtils.isEmpty(link)) {
					link = es1.get(i).child(1).attr("href");
				}

				partContent = partContent.replace(
						AllConstants.NOODLE_IMAGE_CLICK_URL,
						"http://lvyou.baidu.com" + link);
				if (StringUtils.isNotEmpty(partContent)) {
					sb.append(partContent);
				}
				if (i == 5) {
					break;
				}
			}
			if (StringUtils.isNotEmpty(sb.toString())) {
				result = AllConstants.WEI_XIN_ARTICLES_MESSAGE_START
						+ sb.toString()
						+ AllConstants.WEI_XIN_ARTICLES_MESSAGE_END;
				AckTask.saveRecord("impress", keyword, result);
				return result;
			} else {
				url = "http://lvyou.baidu.com/"
						+ PinyinUtils.getStringPinYin(keyword);
				html = HTMLUtils.getHtml(url);
				doc = Jsoup.parse(html);
				es = doc.getElementsByTag("meta");
				Elements ps = doc.select("p");
				Elements newPs = new Elements();
				for (int i = 0; i < ps.size(); i++) {
					if (ps.get(i).hasClass("text-desc-p")) {
						newPs.add(ps.get(i));
					}
				}

				if (es != null) {
					for (int i = 0; i < es.size(); i++) {
						if (es.get(i).hasAttr("itemprop")
								&& es.get(i).attr("itemprop")
										.equalsIgnoreCase("photo")) {
							String title = "";
							if (newPs != null && ps.size() > (i + 1)) {
								title = newPs.get(i).text();
								if (title.length() > 30) {
									title = title.substring(0, 27) + "...";
								}
							}
							es.get(i).attr("content");
							sb.append(getItem(
									es.get(i).attr("content"),
									title,
									"http://lvyou.baidu.com/"
											+ PinyinUtils
													.getStringPinYin(keyword)));
						}
					}
				}
			}
			if (StringUtils.isNotEmpty(sb.toString())) {
				result = AllConstants.WEI_XIN_ARTICLES_MESSAGE_START
						+ sb.toString()
						+ AllConstants.WEI_XIN_ARTICLES_MESSAGE_END;
				AckTask.saveRecord("impress", keyword, result);
				return result;
			}
		}
		return null;
	}
	
	public static String getItem(String picUrl, String title, String clickUrl) {
		return AllConstants.WEI_XIN_IMAGE_ITEM
				.replace(AllConstants.NOODLE_PIC_URL, picUrl)
				.replace(AllConstants.NOODLE_IMAGE_TITLE, title)
				.replace(AllConstants.NOODLE_IMAGE_CLICK_URL, clickUrl);
	}
}
