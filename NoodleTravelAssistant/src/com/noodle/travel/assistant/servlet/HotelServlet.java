package com.noodle.travel.assistant.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.noodle.travel.assistant.baidu.pojo.PlaceSearchResults;
import com.noodle.travel.assistant.baidu.pojo.Placesearchresponse;
import com.noodle.travel.assistant.util.AllConstants;
import com.noodle.travel.assistant.util.JaxbUtil;
import com.noodle.travel.assistant.util.StreamUtil;
import com.noodle.travel.assistant.weixin.pojo.Article;
import com.noodle.travel.assistant.weixin.pojo.NewsMessage;

public class HotelServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4593519237025758140L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String city = req.getParameter("query");
		String userid = req.getParameter("userid");
		resp.setContentType("text/plain");
		resp.setCharacterEncoding("UTF-8");
		String result = process(city, userid);
		System.out.println(result);
		resp.getWriter().println(result);
	}

	private static String process(String city, String userid) {
		try {
			if(StringUtils.isNotEmpty(city)){
				String str = StreamUtil.url2String(AllConstants.BAIDU_PLACE_SEARCH.replace("query", "酒店").replace("city", city));
				if (StringUtils.isNotEmpty(str)) {
					Placesearchresponse response = JaxbUtil.converyToJavaBean(str.toLowerCase(), Placesearchresponse.class);
					if(response != null && response.getResults() != null && response.getResults().size()>0) {
						List<PlaceSearchResults> results = response.getResults();
						if(results != null && results.size()>0) {
							String messages = "";
							NewsMessage newsMessage = new NewsMessage();
							newsMessage.setToUserName(userid);
							newsMessage.setFromUserName("noodle_travel");
							newsMessage.setCreateTime(new Date().getTime());
							newsMessage.setMsgType(AllConstants.RESP_MESSAGE_TYPE_NEWS);
							newsMessage.setFuncFlag(0);
							
							List<Article> articleList = new ArrayList<Article>();
							Article article1 = new Article();
							article1.setTitle("酒店信息");
							article1.setDescription("");
							article1.setPicUrl("");
							article1.setUrl("");
							articleList.add(article1);
							for (PlaceSearchResults result : results) {
								Article article2 = new Article();
								article2.setTitle(result.getName()+"\n"+result.getAddress()+"\n"+result.getTelephone());
								article2.setDescription("");
								article2.setPicUrl("");
								article2.setUrl(AllConstants.BAIDU_PLACE_DETAIL.replace("myuid", result.getUid()));
								articleList.add(article2);
								newsMessage.setArticleCount(articleList.size());
								newsMessage.setArticles(articleList);
								messages = JaxbUtil.newsMessageToXml(newsMessage);
							}
							return messages;
						}
					}
				}
			}
			return "很遗憾，没能找到酒店信息";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "很遗憾，没能找到酒店信息";
	}

	public static void main(String[] args) {
		System.out.println(process("上海", "noodle_travel"));
	}
}
