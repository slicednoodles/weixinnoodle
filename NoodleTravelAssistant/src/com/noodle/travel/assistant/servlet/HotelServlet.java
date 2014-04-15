package com.noodle.travel.assistant.servlet;

import java.io.IOException;
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

public class HotelServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4593519237025758140L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String city = req.getParameter("query");
		resp.setContentType("text/plain");
		resp.setCharacterEncoding("UTF-8");
		String result = process(city);
		System.out.println(result);
		resp.getWriter().println(result);
	}

	private static String process(String city) {
		try {
			if(StringUtils.isNotEmpty(city)){
				String str = StreamUtil.url2String(AllConstants.BAIDU_PLACE_SEARCH.replace("query", "酒店").replace("city", city));
				if (StringUtils.isNotEmpty(str)) {
					Placesearchresponse response = JaxbUtil.converyToJavaBean(str.toLowerCase(), Placesearchresponse.class);
					if(response != null && response.getResults() != null && response.getResults().size()>0) {
						List<PlaceSearchResults> results = response.getResults();
						if(results != null && results.size()>0) {
							StringBuilder sb = new StringBuilder();
							int i=1;
							for (PlaceSearchResults result : results) {
								sb.append(i).append(":").append(result.getAddress()).append("\r\n").append(result.getTelephone()).append("\r\n");
								i++;
							}
							return sb.toString();
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
		System.out.println(process("上海"));
	}
}
