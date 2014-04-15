package com.noodle.travel.assistant.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;

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
import com.noodle.travel.assistant.util.NoodleStringUtils;
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
				keyword = keyword.replace(" ", "");
				if ("desc".equalsIgnoreCase(type)) {
					result = getDesc(keyword);
				} else if ("impress".equalsIgnoreCase(type)) {
					result = getImpress(keyword);
				} else if ("live".equalsIgnoreCase(type)) {
					result = getLive(keyword);
				} else if ("tips".equalsIgnoreCase(type)) {
					result = getTips(keyword);
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

	private static String getTips(String keyword) throws Exception {
		String html = HTMLUtils.getHtml("http://lvyou.baidu.com/"
				+ PinyinUtils.getStringPinYin(keyword));
		if (!html.contains("error-back")) {
			StringBuilder sb = new StringBuilder();

			sb.append(getItem(
					"http://rs.v5kf.com/upload/55797/13975812481.jpg",
					keyword + "活动信息",
					"http://lvyou.baidu.com/"
							+ PinyinUtils.getStringPinYin(keyword) + "/huodong"));
			
			sb.append(getItem(
					"http://rs.v5kf.com/upload/55797/13975812883.jpg",
					keyword + "地图信息",
					"http://lvyou.baidu.com/"
							+ PinyinUtils.getStringPinYin(keyword) + "/ditu"));
			
			sb.append(getItem(
					"http://rs.v5kf.com/upload/55797/13975812767.jpg",
					keyword + "文化信息",
					"http://lvyou.baidu.com/"
							+ PinyinUtils.getStringPinYin(keyword) + "/wenhua"));
			
			sb.append(getItem(
					"http://rs.v5kf.com/upload/55797/13975812982.jpg",
					keyword + "贴士信息",
					"http://lvyou.baidu.com/"
							+ PinyinUtils.getStringPinYin(keyword) + "/tips"));
			
			
			if (StringUtils.isNotEmpty(sb.toString())) {
				return AllConstants.WEI_XIN_ARTICLES_MESSAGE_START.replace(
						AllConstants.NOODLE_ARTICLE_COUNT, String.valueOf(5))
						+ sb.toString()
						+ AllConstants.WEI_XIN_ARTICLES_MESSAGE_END;
			}
		}
		return AllConstants.WEI_XIN_ARTICLES_MESSAGE_START.replace(
				AllConstants.NOODLE_ARTICLE_COUNT, String.valueOf(1))
				+ AllConstants.WEI_XIN_IMAGE_ITEM
						.replace(AllConstants.NOODLE_PIC_URL, "")
						.replace(AllConstants.NOODLE_IMAGE_TITLE,
								"抱歉，亲，没找到该景点。试试别的景点可以吗")
						.replace(AllConstants.NOODLE_IMAGE_CLICK_URL, "")
				+ AllConstants.WEI_XIN_ARTICLES_MESSAGE_END;
	}
	
	private static String getLive(String keyword) throws Exception {
		String html = HTMLUtils.getHtml("http://lvyou.baidu.com/"
				+ PinyinUtils.getStringPinYin(keyword));
		if (!html.contains("error-back")) {
			StringBuilder sb = new StringBuilder();

			sb.append(getItem(
					"http://rs.v5kf.com/upload/55797/13975813565.jpg",
					keyword + "路线信息",
					"http://lvyou.baidu.com/"
							+ PinyinUtils.getStringPinYin(keyword) + "/luxian"));
			
			sb.append(getItem(
					"http://rs.v5kf.com/upload/55797/13975812632.jpg",
					keyword + "住宿信息",
					"http://lvyou.baidu.com/"
							+ PinyinUtils.getStringPinYin(keyword) + "/zhusu"));
			
			sb.append(getItem(
					"http://rs.v5kf.com/upload/55797/13975813126.jpg",
					keyword + "美食信息",
					"http://lvyou.baidu.com/"
							+ PinyinUtils.getStringPinYin(keyword) + "/meishi"));
			
			sb.append(getItem(
					"http://rs.v5kf.com/upload/55797/139758140110.jpg",
					keyword + "购物信息",
					"http://lvyou.baidu.com/"
							+ PinyinUtils.getStringPinYin(keyword) + "/gouwu"));
			
			if (StringUtils.isNotEmpty(sb.toString())) {
				return AllConstants.WEI_XIN_ARTICLES_MESSAGE_START.replace(
						AllConstants.NOODLE_ARTICLE_COUNT, String.valueOf(5))
						+ sb.toString()
						+ AllConstants.WEI_XIN_ARTICLES_MESSAGE_END;
			}
		}
		return AllConstants.WEI_XIN_ARTICLES_MESSAGE_START.replace(
				AllConstants.NOODLE_ARTICLE_COUNT, String.valueOf(1))
				+ AllConstants.WEI_XIN_IMAGE_ITEM
						.replace(AllConstants.NOODLE_PIC_URL, "")
						.replace(AllConstants.NOODLE_IMAGE_TITLE,
								"抱歉，亲，没找到该景点。试试别的景点可以吗")
						.replace(AllConstants.NOODLE_IMAGE_CLICK_URL, "")
				+ AllConstants.WEI_XIN_ARTICLES_MESSAGE_END;
	}

	private static String getItem(String picUrl, String title, String clickUrl) {
		return AllConstants.WEI_XIN_IMAGE_ITEM
				.replace(AllConstants.NOODLE_PIC_URL, picUrl)
				.replace(AllConstants.NOODLE_IMAGE_TITLE, title)
				.replace(AllConstants.NOODLE_IMAGE_CLICK_URL, clickUrl);
	}

	private static String getImpress(String keyword)
			throws UnsupportedEncodingException, Exception {
		String html = HTMLUtils.getHtml("http://lvyou.baidu.com/"
				+ PinyinUtils.getStringPinYin(keyword) + "/jingdian/");
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
			return AllConstants.WEI_XIN_ARTICLES_MESSAGE_START.replace(
					AllConstants.NOODLE_ARTICLE_COUNT, String.valueOf(size))
					+ sb.toString() + AllConstants.WEI_XIN_ARTICLES_MESSAGE_END;
		}
		String result = AllConstants.WEI_XIN_ARTICLES_MESSAGE_START.replace(
				AllConstants.NOODLE_ARTICLE_COUNT, String.valueOf(1))
				+ AllConstants.WEI_XIN_IMAGE_ITEM
						.replace(AllConstants.NOODLE_PIC_URL, "")
						.replace(AllConstants.NOODLE_IMAGE_TITLE,
								"抱歉，亲，没找到该景点。试试别的景点可以吗")
						.replace(AllConstants.NOODLE_IMAGE_CLICK_URL, "")
				+ AllConstants.WEI_XIN_ARTICLES_MESSAGE_END;
		return result;
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
		Date d = new Date();
		System.out.println(getImpress("横店"));
		System.out.println(getImpress("横店").getBytes().length);
		System.out.println(new Date().getTime() - d.getTime());
	}
}
