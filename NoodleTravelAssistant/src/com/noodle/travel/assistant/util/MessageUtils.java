package com.noodle.travel.assistant.util;


public class MessageUtils {
	public static String getItem(String picUrl, String title, String clickUrl) {
		return AllConstants.WEI_XIN_IMAGE_ITEM
				.replace(AllConstants.NOODLE_PIC_URL, picUrl)
				.replace(AllConstants.NOODLE_IMAGE_TITLE, title)
				.replace(AllConstants.NOODLE_IMAGE_CLICK_URL, clickUrl);
	}
}