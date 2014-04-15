package com.noodle.travel.assistant.util;

public class AllConstants {
	
	public static final String BAIDU_PLACE_SEARCH="http://api.map.baidu.com/place/v2/search?page_size=5&output=xml&ak=l0wKGnh4oTlA9DyRstrMy3QG&q=query&region=city";
	public static final String BAIDU_PLACE_DETAIL="http://api.map.baidu.com/place/v2/detail?output=xml&uid=myuid&ak=l0wKGnh4oTlA9DyRstrMy3QG";
	
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
	
	//微信消息常量-------------start
	/**
	 * 返回消息类型：文本
	 */
	public static final String RESP_MESSAGE_TYPE_TEXT = "text";

	/**
	 * 返回消息类型：音乐
	 */
	public static final String RESP_MESSAGE_TYPE_MUSIC = "music";

	/**
	 * 返回消息类型：图文
	 */
	public static final String RESP_MESSAGE_TYPE_NEWS = "news";

	/**
	 * 请求消息类型：文本
	 */
	public static final String REQ_MESSAGE_TYPE_TEXT = "text";

	/**
	 * 请求消息类型：图片
	 */
	public static final String REQ_MESSAGE_TYPE_IMAGE = "image";

	/**
	 * 请求消息类型：链接
	 */
	public static final String REQ_MESSAGE_TYPE_LINK = "link";

	/**
	 * 请求消息类型：地理位置
	 */
	public static final String REQ_MESSAGE_TYPE_LOCATION = "location";

	/**
	 * 请求消息类型：音频
	 */
	public static final String REQ_MESSAGE_TYPE_VOICE = "voice";

	/**
	 * 请求消息类型：推送
	 */
	public static final String REQ_MESSAGE_TYPE_EVENT = "event";

	/**
	 * 事件类型：subscribe(订阅)
	 */
	public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";

	/**
	 * 事件类型：unsubscribe(取消订阅)
	 */
	public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";

	/**
	 * 事件类型：CLICK(自定义菜单点击事件)
	 */
	public static final String EVENT_TYPE_CLICK = "CLICK";
	//微信消息常量-------------end
}
