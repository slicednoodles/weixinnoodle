package com.noodle.v5.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.noodle.common.baidu.pojo.PlaceSearchResults;
import com.noodle.common.baidu.pojo.Placesearchresponse;
import com.noodle.common.cache.Cache;
import com.noodle.common.utils.AllConstants;
import com.noodle.common.utils.HTMLUtils;
import com.noodle.common.utils.JaxbUtil;
import com.noodle.common.utils.MessageUtils;
import com.noodle.v5.task.AckTask;

public class HotelService {

	public String process(HttpServletRequest req) {
		String city = req.getParameter("query");
		System.out.println(city);
		String result = Cache.getResultFromCache("hotel", city);
		if (StringUtils.isEmpty(result)) {
			result = process(city);
		} else {
			try {
				refresh(city);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	private static String process(String city) {
		try {
			StringBuilder sb = new StringBuilder();
			if (StringUtils.isNotEmpty(city)) {
				String urlResponse = "";
				String[] location = city.split(",");

				if (city.contains(",")) {
					sb.append(MessageUtils.getItem(
							"http://rs.v5kf.com/upload/55797/13973886706.jpg",
							"附近酒店信息", ""));
					urlResponse = HTMLUtils
							.getHtml(AllConstants.BAIDU_PLACE_AREA_SEARCH
									.replace("myquery", "酒店").replace(
											"mylocation",
											location[1] + "," + location[0]));
				} else {
					sb.append(MessageUtils.getItem(
							"http://rs.v5kf.com/upload/55797/13973886706.jpg",
							city + "酒店信息", ""));
					urlResponse = HTMLUtils
							.getHtml(AllConstants.BAIDU_PLACE_SEARCH.replace(
									"query", "酒店").replace("city", city));
				}
				if (StringUtils.isNotEmpty(urlResponse)) {
					Placesearchresponse response = JaxbUtil.converyToJavaBean(
							urlResponse.toLowerCase(),
							Placesearchresponse.class);

					if (response != null && response.getResults() != null
							&& response.getResults().size() > 0) {
						List<PlaceSearchResults> results = response
								.getResults();
						if (results != null && results.size() > 0) {
							String tel = "";
							boolean flag = false;
							for (PlaceSearchResults result : results) {
								if (StringUtils.isNotEmpty(result
										.getTelephone())) {
									tel = "\n" + result.getTelephone();
								}
								if (result.getUid() != null
										&& sb.length() < 1900) {
									sb.append(MessageUtils.getItem(
											"http://api.map.baidu.com/place/detail?output=html&uid="
													+ result.getUid(),
											result.getName() + "\n"
													+ result.getAddress() + tel,
											"http://api.map.baidu.com/place/detail?output=html&uid="
													+ result.getUid()));
									flag = true;
								}
							}
							if (flag) {
								String result = AllConstants.WEI_XIN_ARTICLES_MESSAGE_START
										.replace(
												AllConstants.NOODLE_ARTICLE_COUNT,
												String.valueOf(5))
										+ sb.toString()
										+ AllConstants.WEI_XIN_ARTICLES_MESSAGE_END;
								AckTask.saveRecord("hotel", city, result);
								return result;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MessageUtils.notFound("抱歉，亲，没找到该酒店。试试别的酒店可以吗");
	}

	public static void refresh(final String keyword) throws Exception {
		new Thread(new Runnable() {
			@Override
			public void run() {
				String result = null;
				try {
					result = process(keyword);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (StringUtils.isNotEmpty(result)) {
					AckTask.saveRecord("hotel", keyword, result);
				}
			}
		}, "saveRecord").start();
	}

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		System.out.println(process("山西"));
		long end = System.currentTimeMillis();
		System.out.println(end - start);
		System.out.println("------------");

	}
}
