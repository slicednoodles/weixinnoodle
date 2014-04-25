package com.noodle.v5.service;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.noodle.common.cache.Cache;
import com.noodle.common.utils.AllConstants;
import com.noodle.common.utils.HTMLUtils;
import com.noodle.common.utils.MessageUtils;
import com.noodle.common.utils.PinyinUtils;
import com.noodle.v5.task.AckTask;

public class TourService {

	public String process(HttpServletRequest req) {
		String result = "";
		try {
			String keyword = req.getParameter("query");
			String subType = req.getParameter("subType");
			System.out.println("input keyword = " + keyword);
			System.out.println("input keyword = " + subType);
			if ("random".equalsIgnoreCase(subType)) {
				result = getRandom("impress");
			} else if (StringUtils.isNotEmpty(keyword)
					&& StringUtils.isNotEmpty(subType)) {
				keyword = keyword.replace(" ", "");
				if ("impress".equalsIgnoreCase(subType)) {
					result = getImpress(keyword);
				} else if ("live".equalsIgnoreCase(subType)) {
					result = getLive(keyword);
				} else if ("tips".equalsIgnoreCase(subType)) {
					result = getTips(keyword);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private String getRandom(String type) {
		String result = Cache.getRandom(type);
		if (StringUtils.isNotEmpty(result)) {
			return result;
		}
		return MessageUtils.notFound("抱歉，亲，没找到景点。试试别的功能吧");
	}

	private static String getTips(String keyword) throws Exception {
		String result = Cache.getResultFromCache("tips", keyword);
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
		return MessageUtils.notFound("抱歉，亲，没找到该景点。试试别的景点可以吗");
	}

	private static String getLive(String keyword) throws Exception {
		String result = Cache.getResultFromCache("live", keyword);
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
		return MessageUtils.notFound("抱歉，亲，没找到该景点。试试别的景点可以吗");
	}

	private static String getImpress(String keyword)
			throws UnsupportedEncodingException, Exception {
		String result = Cache.getResultFromCache("impress", keyword);
		if (StringUtils.isNotEmpty(result)) {
			AckTask.getImpressAck(keyword);
			return result;
		}
		result = HTMLUtils.getImpressFromBD(keyword);
		if (StringUtils.isNotEmpty(result)) {
			return result;
		}
		return MessageUtils.notFound("抱歉，亲，没找到该景点。试试别的景点可以吗");
	}


	public static void main(String[] args) throws UnsupportedEncodingException,
			Exception {
		Date d = new Date();
		System.out.println(getImpress("吕梁"));
		// System.out.println(getImpress("东方明珠").getBytes().length);
		System.out.println(new Date().getTime() - d.getTime());
	}
}
