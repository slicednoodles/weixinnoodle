package com.noodle.travel.assistant.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.noodle.travel.assistant.util.AllConstants;
import com.noodle.travel.assistant.util.HTMLUtils;
import com.noodle.travel.assistant.util.PinyinUtils;

public class TravelTourDescServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4593519237025758140L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			String keyword = req.getParameter("query");
			String type = req.getParameter("type");
			System.out.println("input keyword = " + keyword);
			System.out.println("input keyword = " + type);
			String result = "";
			if (StringUtils.isNotEmpty(keyword) && StringUtils.isNotEmpty(type)) {
				if ("desc".equalsIgnoreCase(type)) {
					result = getDesc(keyword);
				} else if ("image".equalsIgnoreCase(type)) {
					result = getImageUrl(keyword);
				} else if ("strategy".equalsIgnoreCase(type)) {
					// result = getStrategy(keyword);
				}
			}
			resp.setContentType("text/plain");
			resp.setCharacterEncoding("UTF-8");
			System.out.println(result);
			resp.getWriter().println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getStrategy(String keyword) throws Exception {
		// 获得交通信息
		String html = HTMLUtils.getHtml("http://lvyou.baidu.com/"
				+ PinyinUtils.getStringPinYin(keyword) + "/jiaotong/");
		Document doc = Jsoup.parse(html);
		Elements es = doc.getElementsByClass("photo-frame");
		return null;
	}

	private static String getImageUrl(String keyword)
			throws UnsupportedEncodingException, Exception {
		String html = HTMLUtils.getHtml("http://lvyou.baidu.com/"
				+ PinyinUtils.getStringPinYin(keyword) + "/fengjing/");
		Document doc = Jsoup.parse(html);
		Elements es = doc.getElementsByClass("photo-frame");
		StringBuilder sb = new StringBuilder();
		String partContent = "";
		int count = 0;
		for (int i = 0; i < es.size(); i++) {
			count++;
			partContent = AllConstants.WEI_XIN_IMAGE_ITEM.replace(
					AllConstants.NOODLE_PIC_URL,
					es.get(i).children().attr("src")).replace(
					AllConstants.NOODLE_IMAGE_NUMBER, keyword + "图片" + count);
			html = HTMLUtils.getHtml("http://lvyou.baidu.com/"
					+ es.get(i).attr("href"));
			int bigImageIdIndex = html.indexOf("\"pic_url\":\"");
			String part = html.substring(bigImageIdIndex);
			int bigImageIdEndIndex = part.indexOf("\",\"ext\":{");
			part = part.substring(0, bigImageIdEndIndex);
			partContent = partContent.replace(
					AllConstants.NOODLE_IMAGE_CLICK_URL,
					"http://hiphotos.baidu.com/lvpics/pic/item/"
							+ part.replace("\"pic_url\":\"", "") + ".jpg");
			if (StringUtils.isNotEmpty(partContent)) {
				sb.append(partContent);
			}
			if (5 == count) {
				break;
			}
		}
		if (StringUtils.isNotEmpty(sb.toString())) {
			return AllConstants.WEI_XIN_ARTICLES_MESSAGE_START.replace(
					AllConstants.NOODLE_ARTICLE_COUNT, String.valueOf(count))
					+ sb.toString() + AllConstants.WEI_XIN_ARTICLES_MESSAGE_END;
		}
		return "没找到该景点的图库";
	}

	private static String getDesc(String keyword)
			throws UnsupportedEncodingException, Exception {
		if (StringUtils.isNotEmpty(keyword)) {
			String html = HTMLUtils
					.getHtml("http://lvyou.baidu.com/search?word="
							+ java.net.URLEncoder.encode(keyword, "utf-8")
							+ "&form=1");
			if (StringUtils.isNotEmpty(html)) {
				String result = doParse(html);

				if (StringUtils.isEmpty(result)) {
					html = HTMLUtils.getHtml("http://lvyou.baidu.com/"
							+ PinyinUtils.getStringPinYin(keyword));
					result = doParse(html);
				}
				if (StringUtils.isNotEmpty(result)) {
					return result;
				}
			}
		}
		return "很遗憾，没能找到大家对该景点的印象";
	}

	private static String doParse(String html) {
		StringBuilder sb = new StringBuilder();
		Document doc = Jsoup.parse(html);
		Elements ps = doc.select("p");
		for (int i = 0; i < ps.size(); i++) {
			if (ps.get(i).hasClass("text-desc-p")) {
				sb.append(ps.get(i).text() + "\r\n");
			}
		}
		if (StringUtils.isEmpty(sb.toString())) {
			ps = doc.select("span");
			for (int i = 0; i < ps.size(); i++) {
				if (ps.get(i).hasClass("view-head-scene-abstract")) {
					sb.append(ps.get(i).text());
				}
			}
		}
		return sb.toString();
	}

	public static void main(String[] args) throws UnsupportedEncodingException,
			Exception {
		System.out.println(getImageUrl("乌镇"));
	}
}
