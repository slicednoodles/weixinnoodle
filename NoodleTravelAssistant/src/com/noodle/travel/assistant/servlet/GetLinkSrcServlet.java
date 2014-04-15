package com.noodle.travel.assistant.servlet;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.noodle.travel.assistant.util.AllConstants;
import com.noodle.travel.assistant.util.HTMLUtils;

public class GetLinkSrcServlet extends HttpServlet {

	private static final long serialVersionUID = -2387254530332224963L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			String link = req.getParameter("link");
			if (StringUtils.isNotEmpty(link)) {
				String html = HTMLUtils.getHtml(link);
				if (link.contains("baidu")) {
					html = html.replace("百度旅游", "旅行小助手");
					Document doc = Jsoup.parse(html);
					Elements es = doc.getAllElements();
					Set<String> set = new HashSet<String>();
					for (Element e : es) {
						if(e.tagName().equalsIgnoreCase("script")){
							System.out.println(e.html());
						}
						String text = "";
						if (e.hasAttr("href")) {
							text = e.attr("href");

						} else if (e.hasAttr("src")) {
							text = e.attr("src");

						}
						if (!text.startsWith("http") && text.startsWith("/")
								&& !text.equalsIgnoreCase("/")) {
							set.add(text);
						}
					}
				
					Iterator<String> ite = set.iterator();
					while (ite.hasNext()) {
						String prefix = ite.next();
						String end = "";
						if (prefix.length() > 5) {
							end = prefix.substring(prefix.length() - 5,
									prefix.length());
						} else {
							end = prefix;
						}
//						if (end.contains(".")) {
							html = html.replace(prefix,
									"http://lvyou.baidu.com" + prefix);
							
//						} else {
//							System.out.println(prefix);
//							html = html.replace(prefix, "#");
//						}
					}
				}
				html = html.replace("\"/static/", "\"http://lvyou.baidu.com/static/");
				html = html.replace("'/static/", "'http://lvyou.baidu.com/static/");
				html = AllConstants.NOODLE_HEADER + html
						+ AllConstants.NOODLE_FOOTER;
				resp.setContentType("text/html");
				resp.setCharacterEncoding("UTF-8");
				resp.getWriter().println(html);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
