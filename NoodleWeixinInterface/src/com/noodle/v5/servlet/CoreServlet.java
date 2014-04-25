package com.noodle.v5.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.noodle.v5.service.HotelService;
import com.noodle.v5.service.TourService;

public class CoreServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4593519237025758140L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String type = req.getParameter("type");
		System.out.println(type);
		String result = null;
		if("hotel".equalsIgnoreCase(type)){
			HotelService service = new HotelService();
			result = service.process(req);
		}else if("tour".equalsIgnoreCase(type)){
			TourService service = new TourService();
			result = service.process(req);
		}
		if(StringUtils.isNotEmpty(result)){
			System.out.println(result);
			resp.setContentType("text/plain");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().println(result);
		}
	}
}
