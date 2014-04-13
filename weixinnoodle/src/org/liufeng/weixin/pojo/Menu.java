package org.liufeng.weixin.pojo;

import org.liufeng.weixin.util.CoreUtil;

/**
 * 菜单
 * 
 * @author liufeng
 * @date 2013-08-08
 */
public class Menu {
	private Button[] button;

	public Button[] getButton() {
		return button;
	}

	public void setButton(Button[] button) {
		this.button = button;
	}
	
	public static Menu valueOf(final String str) {
		Menu result = CoreUtil.valueOf(Menu.class, str);
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String result = CoreUtil.toStringJSON(Menu.class, this);
		return result;
	}
}