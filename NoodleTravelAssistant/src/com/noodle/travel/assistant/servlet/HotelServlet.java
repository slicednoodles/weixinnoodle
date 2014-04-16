package com.noodle.travel.assistant.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.noodle.travel.assistant.baidu.pojo.PlaceSearchResults;
import com.noodle.travel.assistant.baidu.pojo.Placesearchresponse;
import com.noodle.travel.assistant.util.AllConstants;
import com.noodle.travel.assistant.util.HTMLUtils;
import com.noodle.travel.assistant.util.JaxbUtil;
import com.noodle.travel.assistant.util.MessageUtils;
import com.noodle.travel.assistant.weixin.pojo.Article;
import com.noodle.travel.assistant.weixin.pojo.NewsMessage;

public class HotelServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4593519237025758140L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String city = req.getParameter("query");
		System.out.println(city);
		resp.setContentType("text/plain");
		resp.setCharacterEncoding("UTF-8");
		String result = process(city);
		System.out.println(result);
		resp.getWriter().println(result);
	}

	private static String process(String city) {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append(MessageUtils.getItem("http://rs.v5kf.com/upload/55797/13973886706.jpg", "上海" + "酒店信息", ""));
			if (StringUtils.isNotEmpty(city)) {
				String[] location = city.split(",");
				if (city.contains(",") && location != null && location.length>0) {
					String str = HTMLUtils.getHtml(AllConstants.BAIDU_PLACE_AREA_SEARCH.replace("myquery", "酒店").replace(
							"mylocation", location[1]+","+location[0]));
					if (StringUtils.isNotEmpty(str)) {
						Placesearchresponse response = JaxbUtil.converyToJavaBean(str.toLowerCase(),
								Placesearchresponse.class);
						if (response != null && response.getResults() != null && response.getResults().size() > 0) {
							List<PlaceSearchResults> results = response.getResults();
							if (results != null && results.size() > 0) {
								for (PlaceSearchResults result : results) {
									sb.append(MessageUtils.getItem("", result.getName() + "\n" + result.getAddress()
											+ "\n" + result.getTelephone(), ""));
								}
								if (StringUtils.isNotEmpty(sb.toString())) {
									return AllConstants.WEI_XIN_ARTICLES_MESSAGE_START.replace(
											AllConstants.NOODLE_ARTICLE_COUNT, String.valueOf(5))
											+ sb.toString()
											+ AllConstants.WEI_XIN_ARTICLES_MESSAGE_END;
								}
							}
						}
					}
				} else {
					String str = HTMLUtils.getHtml(AllConstants.BAIDU_PLACE_SEARCH.replace("query", "酒店").replace(
							"city", city));
					if (StringUtils.isNotEmpty(str)) {
						Placesearchresponse response = JaxbUtil.converyToJavaBean(str.toLowerCase(),
								Placesearchresponse.class);
						
						if (response != null && response.getResults() != null && response.getResults().size() > 0) {
							List<PlaceSearchResults> results = response.getResults();
							if (results != null && results.size() > 0) {
								for (PlaceSearchResults result : results) {
									sb.append(MessageUtils.getItem("", result.getName() + "\n" + result.getAddress()
											+ "\n" + result.getTelephone(), ""));
								}
								if (StringUtils.isNotEmpty(sb.toString())) {
									return AllConstants.WEI_XIN_ARTICLES_MESSAGE_START.replace(
											AllConstants.NOODLE_ARTICLE_COUNT, String.valueOf(5))
											+ sb.toString()
											+ AllConstants.WEI_XIN_ARTICLES_MESSAGE_END;
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return AllConstants.WEI_XIN_ARTICLES_MESSAGE_START
				.replace(AllConstants.NOODLE_ARTICLE_COUNT, String.valueOf(1))
				+ AllConstants.WEI_XIN_IMAGE_ITEM.replace(AllConstants.NOODLE_PIC_URL, "")
						.replace(AllConstants.NOODLE_IMAGE_TITLE, "抱歉，亲，没找到该酒店。试试别的酒店可以吗")
						.replace(AllConstants.NOODLE_IMAGE_CLICK_URL, "") + AllConstants.WEI_XIN_ARTICLES_MESSAGE_END;
	}

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		process("上海");
		long end = System.currentTimeMillis();
		System.out.println(end-start);
	}
}
