package com.noodle.travel.assistant.util;

public class AllConstants {

	public static final String WEIXIN_IMAGE_MESSAGE = "<xml><MsgType>image</MsgType> <PicUrl>noodlePicUrl</PicUrl></xml>";
	
	public static final String WEI_XIN_IMAGE_ITEM = "<item><Title></Title><Description>图片number</Description><PicUrl>noodlePicUrl</PicUrl><Url>noodleClickUrl</Url></item>";
	public static final String WEI_XIN_ARTICLES_MESSAGE_START = "<xml><MsgType>news</MsgType><ArticleCount>noodleArticleCount</ArticleCount><Articles>";
	public static final String WEI_XIN_ARTICLES_MESSAGE_END = "</Articles><FuncFlag>1</FuncFlag></xml> ";
	
	public static final String NOODLE_PIC_URL = "noodlePicUrl";
	public static final String NOODLE_ARTICLE_COUNT = "noodleArticleCount";
	public static final String NOODLE_IMAGE_NUMBER = "图片number";
	public static final String NOODLE_IMAGE_CLICK_URL = "noodleClickUrl";
	
}
