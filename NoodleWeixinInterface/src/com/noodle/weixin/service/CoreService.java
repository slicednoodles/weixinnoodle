package com.noodle.weixin.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.noodle.common.utils.MessageUtils;
import com.noodle.weixin.component.tucao.TucaoService;

public class CoreService {
	/**
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return
	 */
	public static String processRequest(HttpServletRequest req) {
		String respMessage = null;
		try {
			org.dom4j.Document document = MessageUtils.getDocumentFromReq(req);
			Map<String, String> requestMap = MessageUtils.parseXml(document);
			String toUserName = requestMap.get("ToUserName");
			System.out.println("toUserName = " + toUserName);
			if (StringUtils.isNotEmpty(toUserName)) {
				if (toUserName.equalsIgnoreCase("gh_ba0807e1ac17")) {// 吐槽号
					TucaoService ts = new TucaoService();
					return ts.process(requestMap,document.asXML());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respMessage;
	}

}