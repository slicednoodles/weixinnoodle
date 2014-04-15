package com.noodle.travel.assistant.servlet;

import gui.ava.html.image.generator.HtmlImageGenerator;

import java.awt.Dimension;
import java.io.File;
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
import com.noodle.travel.assistant.util.PinyinUtils;

public class TravelNoteServlet extends HttpServlet {

	private static final long serialVersionUID = -2387254530332224963L;
	private static String contextPath = "";
	private static String realPath = "";
	private static HtmlImageGenerator imageGenerator = new HtmlImageGenerator();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			initPara(req);
			String keyword = req.getParameter("query");
			String index = req.getParameter("index");
			System.out.println("input keyword = " + keyword);
			System.out.println("input index = " + index);
			String result = "";
			result = process(keyword);
			System.out.println(result);
			resp.setContentType("text/plain");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initPara(HttpServletRequest req) {
		if (StringUtils.isEmpty(realPath)) {
			realPath = getServletContext().getRealPath("/");
		}
		if (StringUtils.isEmpty(contextPath)) {
			contextPath = req.getRequestURL().toString()
					.replace("/travelNote", "");
		}
		Dimension dimen = new Dimension();
		dimen.setSize(800, 999999999);
		imageGenerator.setSize(dimen);
	}

	private static String process(String keyword)
			throws UnsupportedEncodingException, Exception {
		if (StringUtils.isNotEmpty(keyword)) {
			String html = HTMLUtils
					.getHtml("http://www.mafengwo.cn/group/s.php?q="
							+ java.net.URLEncoder.encode(keyword, "utf-8")
							+ "&t=info");
			if (StringUtils.isNotEmpty(html)) {
				String result = doParse(keyword, html);
				if (StringUtils.isNotEmpty(result)) {
					return result;
				}
			}
		}
		return "很遗憾，没能找到该景点的游记";
	}

	private static String doParse(String keyword, String html) {
		StringBuilder sb = new StringBuilder();
		Document doc = Jsoup.parse(html);
		Elements divs = doc.select("dl");
		for (int i = 0; i < divs.size(); i++) {
			if (divs.get(i).hasAttr("data-ct")) {
				sb.append((i + 1)
						+ ": "
						+ divs.get(i).getElementsByTag("dt").get(0)
								.getElementsByTag("a").text() + "\r\n");
				sb.append("http://www.mafengwo.cn/"
						+ divs.get(i).getElementsByTag("dt").get(0)
								.getElementsByTag("a").attr("href") + "\r\n");
			}
		}
		if (StringUtils.isNotEmpty(sb.toString())) {
			return "您查找的是" + keyword + "游记攻略\r\n" + sb.toString();
			// sb.append("亲，回复" + keyword + "+编号可以看相应攻略全文哦.比如,"+keyword+"1");
		}
		return null;
	}

	private static String getContentPic(String keyword, Integer index)
			throws UnsupportedEncodingException, Exception {
		String html = HTMLUtils.getHtml("http://www.mafengwo.cn/group/s.php?q="
				+ java.net.URLEncoder.encode(keyword, "utf-8") + "&t=info");
		if (StringUtils.isNotEmpty(html)) {
			Document doc = Jsoup.parse(html);
			Elements divs = doc.select("dl");
			int j = 0;
			String link = "";
			String title = "";
			for (int i = 0; i < divs.size(); i++) {
				if (divs.get(i).hasAttr("data-ct")) {
					j++;
					if (j == index) {
						link = divs.get(i).getElementsByTag("dt").get(0)
								.getElementsByTag("a").attr("href");
						title = divs.get(i).getElementsByTag("dt").get(0)
								.getElementsByTag("a").text();
						if (StringUtils.isNotEmpty(title)) {
							title = PinyinUtils.getStringPinYin(title).replace(
									" ", "");
						}
						break;
					}
				}
			}
			String fileName = realPath + "/image/" + title + ".GIF";
			File file = new File(fileName);
			File dir = new File(realPath + "/image/");
			if (!dir.exists()) {
				dir.mkdirs();
			}
			if (file.exists()) {
				if (file.length() > 2000000) {
					return "哎呀，游记太大了,大小在" + (int) (file.length() / 1000000)
							+ "兆，为了保护您的流量，请确保在WIFI环境下访问以下链接:\r\n" + contextPath
							+ "/image/" + file.getName();
				} else {
					String message = AllConstants.WEIXIN_IMAGE_MESSAGE;
					message.replace(AllConstants.NOODLE_PIC_URL, contextPath
							+ "/image/" + file.getName());
					return message;
				}
			} else {
				if (StringUtils.isNotEmpty(link)) {
					html = HTMLUtils.getHtml("http://www.mafengwo.cn/" + link);
					doc = Jsoup.parse(html);
					divs = doc.getElementsByClass("post_info");
					if (divs != null) {
						imageGenerator.loadHtml(divs.get(0).html());
						imageGenerator.saveAsImage(fileName);
					}
					if (file.length() > 2000000) {
						return "游记太大了，为了保护您的流量，请确保在WIFI环境下访问以下链接:/r/n"
								+ contextPath + "/image/" + file.getName();
					} else {
						String message = AllConstants.WEIXIN_IMAGE_MESSAGE;
						message.replace(AllConstants.NOODLE_PIC_URL,
								contextPath + "/image/" + file.getName());
						return message;
					}
				}
			}
		}
		return "好像没找到游记，被万恶的管理员删除了吗？";
	}

	public static void main(String[] args) throws UnsupportedEncodingException,
			Exception {
		Date d = new Date();
		System.out.println(process("北京"));
		System.out.println(new Date().getTime() - d.getTime());
	}
}
