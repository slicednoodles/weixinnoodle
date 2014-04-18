package com.noodle.travel.assistant.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.noodle.travel.assistant.task.AckTask;
import com.noodle.travel.assistant.util.AllConstants;
import com.noodle.travel.assistant.util.CacheUtils;
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
			if ("random".equalsIgnoreCase(type)) {
				result = getRandom("impress");
			} else if (StringUtils.isNotEmpty(keyword)
					&& StringUtils.isNotEmpty(type)) {
				keyword = keyword.replace(" ", "");
				if ("impress".equalsIgnoreCase(type)) {
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

	private String getRandom(String type) {
		String result = CacheUtils.getRandom(type);
		if (StringUtils.isNotEmpty(result)) {
			return result;
		}
		return notFound();
	}

	private static String getTips(String keyword) throws Exception {
		String result = CacheUtils.getResultFromCache("tips", keyword);
		if (StringUtils.isNotEmpty(result)) {
			return result;
		}
		String html = HTMLUtils.getHtml("http://lvyou.baidu.com/"
				+ PinyinUtils.getStringPinYin(keyword));
		if (!html.contains("error-back")) {
			StringBuilder sb = new StringBuilder();

			sb.append(HTMLUtils.getItem(
					"http://rs.v5kf.com/upload/55797/13975812481.jpg",
					keyword + "活动信息",
					"http://lvyou.baidu.com/"
							+ PinyinUtils.getStringPinYin(keyword) + "/huodong"));

			sb.append(HTMLUtils.getItem(
					"http://rs.v5kf.com/upload/55797/13975812883.jpg",
					keyword + "地图信息",
					"http://lvyou.baidu.com/"
							+ PinyinUtils.getStringPinYin(keyword) + "/ditu"));

			sb.append(HTMLUtils.getItem(
					"http://rs.v5kf.com/upload/55797/13975812767.jpg",
					keyword + "文化信息",
					"http://lvyou.baidu.com/"
							+ PinyinUtils.getStringPinYin(keyword) + "/wenhua"));

			sb.append(HTMLUtils.getItem(
					"http://rs.v5kf.com/upload/55797/13975812982.jpg",
					keyword + "贴士信息",
					"http://lvyou.baidu.com/"
							+ PinyinUtils.getStringPinYin(keyword) + "/tips"));

			if (StringUtils.isNotEmpty(sb.toString())) {
				result = AllConstants.WEI_XIN_ARTICLES_MESSAGE_START
						+ sb.toString()
						+ AllConstants.WEI_XIN_ARTICLES_MESSAGE_END;
				AckTask.saveRecord("tips", keyword, result);
				return result;
			}
		}
		return notFound();
	}

	private static String getLive(String keyword) throws Exception {
		String result = CacheUtils.getResultFromCache("live", keyword);
		if (StringUtils.isNotEmpty(result)) {
			return result;
		}
		String html = HTMLUtils.getHtml("http://lvyou.baidu.com/"
				+ PinyinUtils.getStringPinYin(keyword));
		if (!html.contains("error-back")) {
			StringBuilder sb = new StringBuilder();

			sb.append(HTMLUtils.getItem(
					"http://rs.v5kf.com/upload/55797/13975813565.jpg",
					keyword + "路线信息",
					"http://lvyou.baidu.com/"
							+ PinyinUtils.getStringPinYin(keyword) + "/luxian"));

			sb.append(HTMLUtils.getItem(
					"http://rs.v5kf.com/upload/55797/13975812632.jpg",
					keyword + "住宿信息",
					"http://lvyou.baidu.com/"
							+ PinyinUtils.getStringPinYin(keyword) + "/zhusu"));

			sb.append(HTMLUtils.getItem(
					"http://rs.v5kf.com/upload/55797/13975813126.jpg",
					keyword + "美食信息",
					"http://lvyou.baidu.com/"
							+ PinyinUtils.getStringPinYin(keyword) + "/meishi"));

			sb.append(HTMLUtils.getItem(
					"http://rs.v5kf.com/upload/55797/139758140110.jpg",
					keyword + "购物信息",
					"http://lvyou.baidu.com/"
							+ PinyinUtils.getStringPinYin(keyword) + "/gouwu"));

			if (StringUtils.isNotEmpty(sb.toString())) {
				result = AllConstants.WEI_XIN_ARTICLES_MESSAGE_START
						+ sb.toString()
						+ AllConstants.WEI_XIN_ARTICLES_MESSAGE_END;
				AckTask.saveRecord("live", keyword, result);
				return result;
			}
		}
		return notFound();
	}

	private static String getImpress(String keyword)
			throws UnsupportedEncodingException, Exception {
		String result = CacheUtils.getResultFromCache("impress", keyword);
		if (StringUtils.isNotEmpty(result)) {
			AckTask.getImpressAck(keyword);
			return result;
		}
		result = HTMLUtils.getImpressFromBD(keyword);
		if (StringUtils.isNotEmpty(result)) {
			return result;
		}
		return notFound();
	}

	private static String notFound() {
		return AllConstants.WEI_XIN_ARTICLES_MESSAGE_START.replace(
				AllConstants.NOODLE_ARTICLE_COUNT, String.valueOf(1))
				+ AllConstants.WEI_XIN_IMAGE_ITEM
						.replace(AllConstants.NOODLE_PIC_URL, "")
						.replace(AllConstants.NOODLE_IMAGE_TITLE,
								"抱歉，亲，没找到该景点。试试别的景点可以吗")
						.replace(AllConstants.NOODLE_IMAGE_CLICK_URL, "")
				+ AllConstants.WEI_XIN_ARTICLES_MESSAGE_END;
	}

	public static void main(String[] args) throws UnsupportedEncodingException,
			Exception {
		Date d = new Date();
		System.out.println(getImpress("吕梁"));
		// System.out.println(getImpress("东方明珠").getBytes().length);
		System.out.println(new Date().getTime() - d.getTime());
	}
}
