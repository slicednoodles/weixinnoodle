package com.noodle.common.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.noodle.common.cache.Cache;

public class InitServlet extends HttpServlet {

	private static final long serialVersionUID = 225721318109367866L;
	private static String contextPath;

	@Override
	public void init() throws ServletException {
		try {
			contextPath = getServletContext().getRealPath("/");
			Cache.init(contextPath);
			Cache.getDataToCache();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
