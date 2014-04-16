package com.noodle.travel.assistant.servlet;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.noodle.travel.assistant.util.CacheUtils;

public class GetStatusServlet extends HttpServlet {

	private static final long serialVersionUID = -2387254530332224963L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		StringBuilder sb = new StringBuilder();
		for (Entry<String, Map<String, String>> e : CacheUtils.map.entrySet()) {
			for (Entry<String, String> e1 : e.getValue().entrySet()) {
				sb.append(e.getKey() + " ");
				sb.append(e1.getKey() + " ");
				sb.append(e1.getValue() + " ");
				sb.append("\r\n");
			}
		}
		resp.setContentType("text/plain");
		resp.setCharacterEncoding("UTF-8");
		resp.getWriter().println(sb.toString());
	}

}
