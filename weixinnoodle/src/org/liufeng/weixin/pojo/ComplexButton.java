package org.liufeng.weixin.pojo;

import org.liufeng.weixin.util.CoreUtil;

/**
 * 复杂按钮（父按钮）
 * 
 * @author liufeng
 * @date 2013-08-08
 */
public class ComplexButton extends Button {
	private Button[] sub_button;

	public Button[] getSub_button() {
		return sub_button;
	}

	public void setSub_button(Button[] sub_button) {
		this.sub_button = sub_button;
	}
	

	public static ComplexButton valueOf(final String str) {
		ComplexButton result = CoreUtil.valueOf(ComplexButton.class, str);
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public final String toString() {
		String result = CoreUtil.toStringJSON(ComplexButton.class, this);
		return result;
	}
}