package com.noodle.travel.assistant.util;

public class AllConstants {

	public static final String WEIXIN_IMAGE_MESSAGE = "<xml><MsgType>image</MsgType> <PicUrl>noodlePicUrl</PicUrl></xml>";
	public static final String WEIXIN_TEXT_MESSAGE = "<xml><MsgType>text</MsgType><Content>noodleTextContext</Content></xml>";
	public static final String WEI_XIN_IMAGE_ITEM = "<item><Title>noodelImageTitle</Title><Description></Description><PicUrl>noodlePicUrl</PicUrl><Url>noodleClickUrl</Url></item>";
	public static final String WEI_XIN_ARTICLES_MESSAGE_START = "<xml><MsgType>news</MsgType><Articles>";
	public static final String WEI_XIN_ARTICLES_MESSAGE_END = "</Articles></xml> ";
	
	public static final String NOODLE_PIC_URL = "noodlePicUrl";
	public static final String NOODLE_ARTICLE_COUNT = "noodleArticleCount";
	public static final String NOODLE_IMAGE_TITLE = "noodelImageTitle";
	public static final String NOODLE_IMAGE_DESC = "noodelImageNumber";
	public static final String NOODLE_IMAGE_CLICK_URL = "noodleClickUrl";
	public static final String NOODLE_TEXT_CONTEXT = "noodleTextContext";
}
