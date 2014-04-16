package com.noodle.travel.assistant.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.noodle.travel.assistant.util.CacheUtils;

public class InitServlet extends HttpServlet {

	private static final long serialVersionUID = 225721318109367866L;
	private static String contextPath;

	@Override
	public void init() throws ServletException {
		try {
			contextPath = getServletContext().getRealPath("/");
			CacheUtils.init(contextPath);
			CacheUtils.getDataToCache();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
