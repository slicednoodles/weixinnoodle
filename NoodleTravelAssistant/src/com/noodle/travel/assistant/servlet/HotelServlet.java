package com.noodle.travel.assistant.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.noodle.travel.assistant.util.HTMLUtils;
import com.noodle.travel.assistant.util.PinyinUtils;

public class HotelServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4593519237025758140L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String keyword = req.getParameter("query");
		System.out.println("input keyword = " + keyword);
		resp.setContentType("text/plain");
		resp.setCharacterEncoding("UTF-8");
		String result = process(keyword);
		System.out.println(result);
		resp.getWriter().println(result);
	}

	private static String process(String keyword) {
		try {
			http://api.map.baidu.com/place/v2/search?&q=%E9%A5%AD%E5%BA%97&region=%E5%8C%97%E4%BA%AC&output=json&ak=E4805d16520de693a3fe707cdc962045
			
			
			if(StringUtils.isNotEmpty(keyword)){
				String html = HTMLUtils
						.getHtml("http://api.map.baidu.com/place/v2/search?&output=xml&ak=l0wKGnh4oTlA9DyRstrMy3QG&q="
								+ java.net.URLEncoder.encode("酒店", "utf-8")
								+ "&region=")
								+ java.net.URLEncoder.encode("上海", "utf-8");
				if (StringUtils.isNotEmpty(html)) {
					String result = doParse(html);

//					if (StringUtils.isEmpty(result)) {
//						PinyinUtils pu = new PinyinUtils();
//						html = HTMLUtils.getHtml("http://lvyou.baidu.com/"
//								+ pu.getStringPinYin(keyword));
//						result = doParse(html);
//					}
					if (StringUtils.isNotEmpty(result)) {
						return result;
					}
				}
			}
			return "很遗憾，没能找到大家对该景点的印象";
		} catch (Exception e) {
			e.printStackTrace();
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

	public static void main(String[] args) {
		System.out.println(process("东方明珠"));
	}
}
