package com.noodle.travel.assistant.util;

public class AllConstants {

	public static final String WEIXIN_IMAGE_MESSAGE = "<xml><MsgType>image</MsgType> <PicUrl>noodlePicUrl</PicUrl></xml>";
	
	public static final String WEI_XIN_IMAGE_ITEM = "<item><Title></Title><Description></Description><PicUrl>noodlePicUrl</PicUrl><Url></Url></item>";
	public static final String WEI_XIN_ARTICLES_MESSAGE_START = "<xml><MsgType>news</MsgType><ArticleCount>noodleArticleCount</ArticleCount><Articles>";
	public static final String WEI_XIN_ARTICLES_MESSAGE_END = "</Articles><FuncFlag>1</FuncFlag></xml> ";
	
	public static final String NOODLE_PIC_URL = "noodlePicUrl";
	public static final String NOODLE_ARTICLE_COUNT = "noodleArticleCount";

	
}
