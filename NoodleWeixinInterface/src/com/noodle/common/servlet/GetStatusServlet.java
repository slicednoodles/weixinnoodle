package com.noodle.common.servlet;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.noodle.common.cache.Cache;
import com.noodle.weixin.pojo.VoiceMessage;

public class GetStatusServlet extends HttpServlet {

	private static final long serialVersionUID = -2387254530332224963L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		StringBuilder sb = new StringBuilder();
		String type = req.getParameter("type");
		if (StringUtils.isNotEmpty(type)) {
			if ("tucao".equalsIgnoreCase(type)) {
				for (VoiceMessage v : Cache.tucaoCache) {
					sb.append("user = " + v.getFromUserName() + " ");
					sb.append("mediaId = " + v.getMediaId() + " ");
					sb.append("format = " + v.getFormat() + " ");
					sb.append("\r\n");
				}
			} else {
				Map<String, String> map = Cache.travelCache.get(type);
				for (Entry<String, String> e1 : map.entrySet()) {
					sb.append(e1.getKey() + " ");
					sb.append("\r\n");
				}
			}

		} else {
			for (Entry<String, Map<String, String>> e : Cache.travelCache
					.entrySet()) {
				for (Entry<String, String> e1 : e.getValue().entrySet()) {
					sb.append(e.getKey() + " ");
					sb.append(e1.getKey() + " ");
					sb.append("\r\n");
				}
			}
		}
		resp.setContentType("text/plain");
		resp.setCharacterEncoding("UTF-8");
		resp.getWriter().println(sb.toString());
	}

}
