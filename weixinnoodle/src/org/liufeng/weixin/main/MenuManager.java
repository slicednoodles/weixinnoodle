package org.liufeng.weixin.main;

import org.liufeng.weixin.pojo.AccessToken;
import org.liufeng.weixin.pojo.Button;
import org.liufeng.weixin.pojo.CommonButton;
import org.liufeng.weixin.pojo.ComplexButton;
import org.liufeng.weixin.pojo.Menu;
import org.liufeng.weixin.util.WeixinUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 菜单管理器类
 * 
 * @author liufeng
 * @date 2013-08-08
 */
public class MenuManager {
	private static Logger log = LoggerFactory.getLogger(MenuManager.class);

	public static void main(String[] args) {
		// 第三方用户唯一凭证
		String appId = "wx4e9b7ac9a9f9e4ca";
		// 第三方用户唯一凭证密钥
		String appSecret = "4ef779b553d8a5d77b855cb4bbcb2abf";

		// 调用接口获取access_token
		AccessToken at = WeixinUtil.getAccessToken(appId, appSecret);

		if (null != at) {
			// 调用接口创建菜单
			int result = WeixinUtil.createMenu(getMenu(), at.getToken());
			// 判断菜单创建结果
			if (0 == result)
				System.out.println("菜单创建成功！");
			else
				System.out.println("菜单创建失败，错误码：" + result);
		}
	}

	/**
	 * 组装菜单数据
	 * 
	 * @return
	 */
	public static Menu getMenu() {
		CommonButton btn11 = new CommonButton();
		btn11.setName("关于我们");
		btn11.setType("click");
		btn11.setKey("11");

		CommonButton btn21 = new CommonButton();
		btn21.setName("附近酒店");
		btn21.setType("click");
		btn21.setKey("21");

		CommonButton btn31 = new CommonButton();
		btn31.setName("天气信息");
		btn31.setType("click");
		btn31.setKey("31");

		CommonButton btn32 = new CommonButton();
		btn32.setName("联系客服");
		btn32.setType("click");
		btn32.setKey("32");

		CommonButton btn33 = new CommonButton();
		btn33.setName("公司主页");
		btn33.setType("click");
		btn33.setKey("33");

		ComplexButton mainBtn3 = new ComplexButton();
		mainBtn3.setName("其他");
		mainBtn3.setSub_button(new CommonButton[] { btn31, btn32, btn33 });

		/**
		 * 这是公众号xiaoqrobot目前的菜单结构，每个一级菜单都有二级菜单项<br>
		 * 
		 * 在某个一级菜单下没有二级菜单的情况，menu该如何定义呢？<br>
		 * 比如，第三个一级菜单项不是“更多体验”，而直接是“幽默笑话”，那么menu应该这样定义：<br>
		 * menu.setButton(new Button[] { mainBtn1, mainBtn2, btn33 });
		 */
		Menu menu = new Menu();
		menu.setButton(new Button[] {btn11, btn21, mainBtn3});

		return menu;
	}
}