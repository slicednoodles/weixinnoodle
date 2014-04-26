package com.noodle.common.utils;

public class AllConstants {

	// ///////初始化//////////////////
	public static final String PROPERTIES_FILE = "conf/conf.properties";
	public static final String WEB_INF = "WEB-INF";
	// /////////////////////////////

	public static final String AK = "ak=l0wKGnh4oTlA9DyRstrMy3QG";
	public static final String BAIDU_PLACE_SEARCH = "http://api.map.baidu.com/place/v2/search?page_size=5&output=xml&q=query&region=city&"
			+ AK;
	public static final String BAIDU_PLACE_AREA_SEARCH = "http://api.map.baidu.com/place/v2/search?page_size=5&output=xml&radius=2000&query=myquery&location=mylocation&"
			+ AK;
	public static final String BAIDU_PLACE_DETAIL = "http://api.map.baidu.com/place/v2/detail?page_size=5&output=xml&uid=myuid&"
			+ AK;

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

	public static final String SELECT_ALL_FROM_TRAVEL_RECORD = "SELECT * FROM NOODLE_TRAVEL_RECORD";
	public static final String INSERT_TO_TRAVEL_RECORD = "INSERT INTO NOODLE_TRAVEL_RECORD (TYPE, KEYWORD, XML_CONTENT) VALUES(?,?,?)";
	public static final String UPDATE_TO_TRAVEL_RECORD = "UPDATE NOODLE_TRAVEL_RECORD SET XML_CONTENT=? WHERE TYPE = ? AND KEYWORD=?";
	
	public static final String SELECT_ALL_FROM_WEIXIN_MESSAGE_RECORD_WITH_TYPE = "SELECT * FROM NOODLE_WEIXIN_MESSAGE_RECORD WHERE TYPE =?";
	public static final String INSERT_TO_WEIXIN_MESSAGE_RECORD = "INSERT INTO NOODLE_WEIXIN_MESSAGE_RECORD (TYPE, SUB_TYPE, MESSAGE) VALUES(?,?,?)";

	public static final String TO_USER_NAME = "ToUserName";
	public static final String FROM_USER_NAME = "FromUserName";
	public static final String EVENT = "Event";
	public static final String SUBSCRIBE = "subscribe";
	public static final String MSG_TYPE = "MsgType";
	public static final String JIE_CAO = "jiecao";
	public static final String TU_CAO = "tucao";
	public static final String VOICE = "voice";
	public static final String MEDIA_ID = "MediaId";
	public static final String CREATE_TIME = "CreateTime";
	public static final String VOICE_FORMAT = "Format";
	public static final String EVENT_KEY = "EventKey";

}
