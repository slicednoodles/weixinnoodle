package org.liufeng.weixin.pojo;

import org.liufeng.weixin.util.CoreUtil;

/**
 * 普通按钮（子按钮）
 * 
 * @author liufeng
 * @date 2013-08-08
 */
public class CommonButton extends Button {
	private String type;
	private String key;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	public static CommonButton valueOf(final String str) {
		CommonButton result = CoreUtil.valueOf(CommonButton.class, str);
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String result = CoreUtil.toStringJSON(CommonButton.class, this);
		return result;
	}
}