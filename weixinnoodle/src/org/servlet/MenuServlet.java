package org.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.liufeng.weixin.main.MenuManager;
import org.liufeng.weixin.pojo.AccessToken;
import org.liufeng.weixin.util.SignUtil;
import org.liufeng.weixin.util.WeixinUtil;

/**
 * Servlet implementation class MenuServlet
 */
public class MenuServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MenuServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 微信加密签名
		String signature = request.getParameter("signature");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");
		// 随机字符串
		String echostr = request.getParameter("echostr");

		PrintWriter out = response.getWriter();
		// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
		try {
			String mySignature = SignUtil.encryptBySHA1(SignUtil.appendParams(timestamp, nonce));

			if (mySignature.equals(signature)) {
				response.getWriter().print(echostr);
				response.getWriter().flush();
				response.getWriter().close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 第三方用户唯一凭证
		String appId = "wx4e9b7ac9a9f9e4ca";
		// 第三方用户唯一凭证密钥
		String appSecret = "4ef779b553d8a5d77b855cb4bbcb2abf";

		// 调用接口获取access_token
		AccessToken at = WeixinUtil.getAccessToken(appId, appSecret);

		String menuList = request.getParameter("menuList");
		
		if (null != at) {
			// 调用接口创建菜单
			//int result = WeixinUtil.createMenu(getMenu(), at.getToken());
			int result = WeixinUtil.createMenu(menuList, at.getToken());
			PrintWriter writer = response.getWriter();
			writer.println("<html>");
			writer.println("<head><title>create menu</title></head>");
			writer.println("<body>");
			// 判断菜单创建结果
			if (0 == result)
				response.getWriter().append("菜单创建成功！");
			else
				response.getWriter().append("菜单创建失败，错误码：" + result);
			writer.println("</body>");
			writer.println("</html>");
			writer.close();
		}
	}

}
