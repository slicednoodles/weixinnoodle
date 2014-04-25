package com.noodle.travel.assistant.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.noodle.travel.assistant.util.HTMLUtils;

public class VideoServlet extends HttpServlet {

	private static final long serialVersionUID = -2387254530332224963L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String keyword = req.getParameter("query");
		String result = process(keyword);
	}

	private static String process(String keyword) {
		try {
			String html = HTMLUtils.getHtml("http://www.yunfan.com/s.php?q="
					+ java.net.URLEncoder.encode(keyword, "utf-8"));
			Document doc = Jsoup.parse(html);
			Elements es = doc.getElementsByClass("film_lb movie");
			for (Element e : es) {
				Elements ldiv = e.getElementsByClass("hb_L");
				Elements eps = ldiv.get(0).getElementsByTag("p");
				String imageUrl = eps.get(0).attr("src");
				String link = ldiv.get(0)
						.getElementsByClass("hb_f_links1 jg_p").attr("href");
				Elements rdiv = e.getElementsByClass("hb_R");
				Elements words = rdiv.get(0).getElementsByClass("word");
				StringBuilder sb = new StringBuilder();
				for (Element e1 : words) {
					sb.append(e1.text());
				}
				sb.append(":");
				Elements jj = rdiv.get(0).getElementsByClass("jj_w");
				sb.append(jj.text());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args){
		process("美国队长");
	}
}
