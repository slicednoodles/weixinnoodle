package com.noodle.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class MessageUtils {

	public static Document getDocumentFromReq(HttpServletRequest req) throws DocumentException, IOException{
		// 从request中取得输入流
		InputStream inputStream = req.getInputStream();
		// 读取输入流
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		inputStream.close();
		inputStream = null;
		return document;
	}

	public static Map<String, String> parseXml(Document document)
			throws IOException, DocumentException {
		// 将解析结果存储在HashMap中
				Map<String, String> map = new HashMap<String, String>();
		// 得到xml根元素
		Element root = document.getRootElement();
		// 得到根元素的所有子节点
		List<Element> elementList = root.elements();
		System.out.println("---------------");
		// 遍历所有子节点
		for (Element e : elementList) {
			map.put(e.getName(), e.getText());
			System.out.println(e.getName() + ":" + e.getText());
		}
		System.out.println("---------------");
		// 释放资源
		return map;
	}

	public static String getBackXMLTypeText(String toName, String fromName,
			String content) {
		String returnStr = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName>"
				+ "<CreateTime><![CDATA[CreateTime]]></CreateTime><MsgType>text</MsgType>"
				+ "<Content><![CDATA[content]]></Content><FuncFlag>0</FuncFlag></xml>";
		returnStr = returnStr.replace("<![CDATA[toUser]]>", toName);
		returnStr = returnStr.replace("<![CDATA[fromUser]]>", fromName);
		returnStr = returnStr.replace("<![CDATA[CreateTime]]>",
				String.valueOf(new Date().getTime()));
		returnStr = returnStr.replace("<![CDATA[content]]>", content);
		return returnStr;

	}

	public static String getBackXMLTypeVoice(String toName, String fromName,
			String mediaId) {
		String returnStr = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName>"
				+ "<CreateTime><![CDATA[CreateTime]]></CreateTime><MsgType>voice</MsgType>"
				+ "<Voice><MediaId><![CDATA[media_id]]></MediaId></Voice></xml>";
		returnStr = returnStr.replace("<![CDATA[toUser]]>", toName);
		returnStr = returnStr.replace("<![CDATA[fromUser]]>", fromName);
		returnStr = returnStr.replace("<![CDATA[CreateTime]]>",
				String.valueOf(new Date().getTime()));
		returnStr = returnStr.replace("<![CDATA[media_id]]>", mediaId);
		return returnStr;

	}

	public static String getItem(String picUrl, String title, String clickUrl) {
		return AllConstants.WEI_XIN_IMAGE_ITEM
				.replace(AllConstants.NOODLE_PIC_URL, picUrl)
				.replace(AllConstants.NOODLE_IMAGE_TITLE, title)
				.replace(AllConstants.NOODLE_IMAGE_CLICK_URL, clickUrl);
	}

	public static String notFound(String message) {
		return AllConstants.WEI_XIN_ARTICLES_MESSAGE_START.replace(
				AllConstants.NOODLE_ARTICLE_COUNT, String.valueOf(1))
				+ AllConstants.WEI_XIN_IMAGE_ITEM
				.replace(AllConstants.NOODLE_PIC_URL, "")
				.replace(AllConstants.NOODLE_IMAGE_TITLE, message)
				.replace(AllConstants.NOODLE_IMAGE_CLICK_URL, "")
				+ AllConstants.WEI_XIN_ARTICLES_MESSAGE_END;
	}
	
}
