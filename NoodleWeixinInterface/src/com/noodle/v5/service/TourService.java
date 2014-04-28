package com.noodle.v5.service;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.noodle.common.cache.Cache;
import com.noodle.common.utils.AllConstants;
import com.noodle.common.utils.HTMLUtils;
import com.noodle.common.utils.MessageUtils;
import com.noodle.common.utils.PinyinUtils;
import com.noodle.v5.task.AckTask;

public class TourService {

	private static String baiduSearchUrl = "http://lvyou.baidu.com/search?word=";
	private static String baiduLinkUrl = "http://lvyou.baidu.com/";

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
				String url = "";
				String pinyin = PinyinUtils.getStringPinYin(keyword);
				boolean isPinyin = false;
				if (StringUtils.isNotEmpty(pinyin)
						&& pinyin.equalsIgnoreCase(keyword)) {
					url = baiduLinkUrl + PinyinUtils.getStringPinYin(keyword);
					isPinyin = true;
				} else {
					url = baiduSearchUrl
							+ java.net.URLEncoder.encode(keyword, "utf-8");
				}

				if ("impress".equalsIgnoreCase(subType)) {
					result = getImpress(url, keyword);
					if (StringUtils.isEmpty(result) && !isPinyin) {
						url = baiduLinkUrl
								+ PinyinUtils.getStringPinYin(keyword);
						result = getImpress(url, keyword);
					}
				} else if ("live".equalsIgnoreCase(subType)) {
					result = getLive(url, keyword);
					if (StringUtils.isEmpty(result) && !isPinyin) {
						url = baiduLinkUrl
								+ PinyinUtils.getStringPinYin(keyword);
						result = getLive(url, keyword);
					}
				} else if ("tips".equalsIgnoreCase(subType)) {
					result = getTips(url, keyword);
					if (StringUtils.isEmpty(result) && !isPinyin) {
						url = baiduLinkUrl
								+ PinyinUtils.getStringPinYin(keyword);
						result = getTips(url, keyword);
					}
				}
				if (StringUtils.isEmpty(result)) {
					result = MessageUtils.notFound("抱歉，亲，没找到该景点。试试别的景点可以吗");
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

	private static String getTips(String url, String keyword) throws Exception {
		String result = Cache.getResultFromCache("tips", keyword);
		if (StringUtils.isNotEmpty(result)) {
			return result;
		}
		String html = HTMLUtils.getHtml(url);
		if (!html.contains("error-back")) {
			StringBuilder sb = new StringBuilder();

			sb.append(MessageUtils.getItem(
					"http://rs.v5kf.com/upload/55797/13975812481.jpg",
					keyword + "活动信息",
					"http://lvyou.baidu.com/"
							+ PinyinUtils.getStringPinYin(keyword) + "/huodong"));

			sb.append(MessageUtils.getItem(
					"http://rs.v5kf.com/upload/55797/13975812883.jpg",
					keyword + "地图信息",
					"http://lvyou.baidu.com/"
							+ PinyinUtils.getStringPinYin(keyword) + "/ditu"));

			sb.append(MessageUtils.getItem(
					"http://rs.v5kf.com/upload/55797/13975812767.jpg",
					keyword + "文化信息",
					"http://lvyou.baidu.com/"
							+ PinyinUtils.getStringPinYin(keyword) + "/wenhua"));

			sb.append(MessageUtils.getItem(
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

	private static String getLive(String url, String keyword) throws Exception {
		String result = Cache.getResultFromCache("live", keyword);
		if (StringUtils.isNotEmpty(result)) {
			return result;
		}
		String html = HTMLUtils.getHtml(url);
		if (!html.contains("error-back")) {
			StringBuilder sb = new StringBuilder();

			sb.append(MessageUtils.getItem(
					"http://rs.v5kf.com/upload/55797/13975813565.jpg",
					keyword + "路线信息",
					"http://lvyou.baidu.com/"
							+ PinyinUtils.getStringPinYin(keyword) + "/luxian"));

			sb.append(MessageUtils.getItem(
					"http://rs.v5kf.com/upload/55797/13975812632.jpg",
					keyword + "住宿信息",
					"http://lvyou.baidu.com/"
							+ PinyinUtils.getStringPinYin(keyword) + "/zhusu"));

			sb.append(MessageUtils.getItem(
					"http://rs.v5kf.com/upload/55797/13975813126.jpg",
					keyword + "美食信息",
					"http://lvyou.baidu.com/"
							+ PinyinUtils.getStringPinYin(keyword) + "/meishi"));

			sb.append(MessageUtils.getItem(
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
		return null;
	}

	private static String getImpress(String url, String keyword)
			throws UnsupportedEncodingException, Exception {
		String result = Cache.getResultFromCache("impress", keyword);
		if (StringUtils.isNotEmpty(result)) {
			AckTask.getImpressAck(url, keyword);
			return result;
		}
		result = getImpressFromBD(url, keyword);
		if (StringUtils.isNotEmpty(result)) {
			return result;
		}
		return null;
	}

	public static String getImpressFromBD(String url, String keyword)
			throws Exception {
		String result = "";
		String html = HTMLUtils.getHtml(url);
		if (!html.contains("error-back")) {
			Document doc = Jsoup.parse(html);
			StringBuilder sb = new StringBuilder();
			Elements photoes = doc.getElementsByTag("meta");
			String mainTourImageUrl = "";
			if (doc.getElementsByClass("titleheadname").size() == 0
					&& doc.getElementsByClass("header-container").size() == 0) {
				return null;
			}
			String title = "";
			if (doc.getElementsByClass("titleheadname").size() > 0) {
				title = doc.getElementsByClass("titleheadname").get(0)
						.getElementsByTag("p").text();
			} else if (doc.getElementsByClass("header-container").size() == 0) {
				title = doc.getElementsByClass("header-container").get(0)
						.getElementsByTag("h1").text();
			}
			String link = "";
			String subTourImageUrl = "";
			if (photoes != null) {
				for (int i = 0; i < photoes.size(); i++) {
					if (photoes.get(i).hasAttr("itemprop")
							&& (photoes.get(i).attr("itemprop")
									.equalsIgnoreCase("photo") || photoes
									.get(i).attr("itemprop")
									.equalsIgnoreCase("image"))) {
						if (StringUtils.isEmpty(title)) {
							title = keyword;
						}
						mainTourImageUrl = photoes.get(i).attr("content");
						sb.append(MessageUtils.getItem(
								mainTourImageUrl,
								title,
								"http://lvyou.baidu.com/"
										+ PinyinUtils.getStringPinYin(keyword)));
						break;
					}
				}
			}
			if (StringUtils.isEmpty(sb.toString())) {
				photoes = doc.getElementsByTag("figure");
				for (int i = 0; i < photoes.size(); i++) {
					if (photoes.get(i).getElementsByTag("img") != null) {
						mainTourImageUrl = photoes.get(i)
								.getElementsByTag("img").attr("src");
						if (StringUtils.isEmpty(title)) {
							title = keyword;
						}
						sb.append(MessageUtils.getItem(mainTourImageUrl,
								keyword, "http://lvyou.baidu.com/"
										+ PinyinUtils.getStringPinYin(keyword)));
						break;
					}
				}
			}
			if (StringUtils.isEmpty(sb.toString())) {
				photoes = doc.getElementsByClass("photo-cover");
				for (int i = 0; i < photoes.size(); i++) {
					if (photoes.get(i).getElementsByTag("img") != null) {
						mainTourImageUrl = photoes.get(i)
								.getElementsByTag("img").attr("src");
						if (StringUtils.isEmpty(title)) {
							title = keyword;
						}
						sb.append(MessageUtils.getItem(mainTourImageUrl,
								keyword, "http://lvyou.baidu.com/"
										+ PinyinUtils.getStringPinYin(keyword)));
						break;
					}
				}
			}

			int j = 0;
			Elements subPhotoes = doc.getElementsByAttribute("itemprop");
			if (subPhotoes != null) {
				for (int i = 0; i < subPhotoes.size(); i++) {
					if (subPhotoes.get(i).attr("itemprop")
							.equalsIgnoreCase("url")) {
						if (subPhotoes.get(i).children().size() > 0) {
							subTourImageUrl = subPhotoes.get(i).childNode(0)
									.attr("src");
							title = "";
							Elements namees = subPhotoes.get(i)
									.getElementsByClass("unmissable-desc-tit");
							for (org.jsoup.nodes.Element e : namees) {
								if ("name".equalsIgnoreCase(e.attr("itemprop"))) {
									title = e.text();
									break;
								}
							}
							namees = subPhotoes.get(i).getElementsByClass(
									"unmissable-desc-con");
							for (org.jsoup.nodes.Element e : namees) {
								if ("description".equalsIgnoreCase(e
										.attr("itemprop"))) {
									title = title + "：" + e.text();
									break;
								}
							}
							if (title.length() > 30) {
								title = title.substring(0, 28) + "..";
							}
							sb.append(MessageUtils.getItem(subTourImageUrl,
									title, "http://lvyou.baidu.com"
											+ subPhotoes.get(i).attr("href")));
							j++;
						}
					}
					if (j == 5) {
						break;
					}
				}
			}
			if (j == 0) {
				subPhotoes = doc.getElementsByClass("classic-item");
				for (int i = 0; i < subPhotoes.size(); i++) {
					if (subPhotoes.get(i).getElementsByTag("img").size() > 0) {
						subTourImageUrl = subPhotoes.get(i)
								.getElementsByTag("img").attr("src");
						title = "";
						link = "";
						Elements itemTitle = subPhotoes.get(i)
								.getElementsByClass("item-title");
						for (org.jsoup.nodes.Element e : itemTitle) {
							if (e.children().size() > 0) {
								title = e.children().get(0).text();
								link = e.children().get(0).attr("href");
							}

						}
						if (title.length() > 30) {
							title = title.substring(0, 28) + "..";
						}
						sb.append(MessageUtils.getItem(subTourImageUrl, title,
								"http://lvyou.baidu.com" + link));
						j++;
						if (j == 5) {
							break;
						}
					}
				}
			}
			if (j == 0) {
				subPhotoes = doc
						.getElementsByClass("area-unmissable-name-item");
				for (int i = 0; i < subPhotoes.size(); i++) {

					title = subPhotoes.get(i).getElementsByClass("url").text();
					String desc = subPhotoes.get(i).getElementsByClass("name")
							.text();
					if (StringUtils.isNotEmpty(title)
							&& StringUtils.isNotEmpty(desc)) {
						title = title + "：" + desc;
					} else {
						title = title + desc;
					}
					if (title.length() > 30) {
						title = title.substring(0, 28) + "..";
					}
					link = subPhotoes.get(i).getElementsByClass("url")
							.attr("href");
					Elements metaes = subPhotoes.get(i)
							.getElementsByAttributeValue("itemprop", "image");
					for (org.jsoup.nodes.Element e : metaes) {
						subTourImageUrl = e.attr("content");
						sb.append(MessageUtils.getItem(subTourImageUrl, title,
								"http://lvyou.baidu.com" + link));
					}
					j++;
					if (j == 5) {
						break;
					}
				}
			}
			if (StringUtils.isNotEmpty(sb.toString())) {
				result = AllConstants.WEI_XIN_ARTICLES_MESSAGE_START
						+ sb.toString()
						+ AllConstants.WEI_XIN_ARTICLES_MESSAGE_END;
				AckTask.saveRecord("impress", keyword, result);
				return result;
			}
		}
		return null;
	}

	public static void main(String[] args) throws UnsupportedEncodingException,
			Exception {
		Date d = new Date();
		System.out.println(getImpress(
				baiduSearchUrl + java.net.URLEncoder.encode("上海", "utf-8"),
				"上海"));
		// System.out.println(getImpress("东方明珠").getBytes().length);
		System.out.println(new Date().getTime() - d.getTime());
	}
}
