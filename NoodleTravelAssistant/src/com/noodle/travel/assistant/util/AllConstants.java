package com.noodle.travel.assistant.util;

public class AllConstants {
	
	public static final String BAIDU_PLACE_SEARCH="http://api.map.baidu.com/place/v2/search?page_size=5&output=xml&ak=l0wKGnh4oTlA9DyRstrMy3QG&q=query&region=city";
	public static final String BAIDU_PLACE_AREA_SEARCH="http://api.map.baidu.com/place/v2/search?output=xml&radius=2000&ak=l0wKGnh4oTlA9DyRstrMy3QG&query=myquery&location=mylocation";
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

	public static final String NOODLE_HEADER = "<p style=\"\">点击右上角<img data-src=\"http://rs.v5kf.com/upload/55797/13975664127.jpg\" width=\"100\" height=\"49\" src=\"http://rs.v5kf.com/upload/55797/13975664127.jpg\" style=\"border: 0px; height: 26px; width: 37px; \">或<img title=\"undefined\" src=\"http://rs.v5kf.com/upload/55797/13975663869.jpg\" width=\"74\" height=\"64\" data_ue_src=\"http://rs.v5kf.com/upload/55797/13975663869.jpg\" style=\"border: 0px; height: 25px; width: 36px; \"><span style=\"color: rgb(0, 176, 80); \">查看官方账号</span>、<span style=\"color: rgb(0, 176, 80); \">分享到朋友圈</span><iframe id=\"tmp_downloadhelper_iframe\" style=\"display: none;\"></iframe></p>";
	public static final String NOODLE_FOOTER = "<p style=\"\"><span style=\"color: rgb(255, 0, 0); \">当您读完本篇文章时，你可以两种选择：</span><br  />1．你可以点击文章右上角<img data-src=\"http://rs.v5kf.com/upload/55797/13975664127.jpg\" width=\"100\" height=\"49\" src=\"http://rs.v5kf.com/upload/55797/13975664127.jpg\" style=\"border: 0px; height: 26px; width: 37px; \"  />或<img title=\"undefined\" src=\"http://rs.v5kf.com/upload/55797/13975663869.jpg\" width=\"74\" height=\"64\" data_ue_src=\"http://rs.v5kf.com/upload/55797/13975663869.jpg\" style=\"border: 0px; height: 25px; width: 36px; \"  />查看官方账号加关注。<br  /></p><p style=\"\">2．你可以点击文章右上角<img data-src=\"http://rs.v5kf.com/upload/55797/13975664127.jpg\" width=\"100\" height=\"49\" src=\"http://rs.v5kf.com/upload/55797/13975664127.jpg\" style=\"border: 0px; height: 26px; width: 37px; \"  />或<img title=\"undefined\" src=\"http://rs.v5kf.com/upload/55797/13975663869.jpg\" width=\"74\" height=\"64\" data_ue_src=\"http://rs.v5kf.com/upload/55797/13975663869.jpg\" style=\"border: 0px; height: 25px; width: 36px; \"  />分享到朋友圈。以示对小编的鼓励。</p><p style=\"\"><img data-src=\"http://mmsns.qpic.cn/mmsns/6MIPSCficYH3AMFAb5JulUCSx2F8Po6NOoeIsIdZfNPibwCOopwSRBBg/0\" src=\"http://mmsns.qpic.cn/mmsns/6MIPSCficYH3AMFAb5JulUCSx2F8Po6NOoeIsIdZfNPibwCOopwSRBBg/0\" style=\"border: 0px; height: auto !important; \"  /></p><p style=\"\">&nbsp; &nbsp;</p><p style=\"\"><br  />3.更多精彩分享请关注“旅行小助手”公众号：noodle_travel</p>";
	
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
