package org.liufeng.course.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.liufeng.course.message.req.Article;
import org.liufeng.course.message.req.NewsMessage;
import org.liufeng.course.message.req.TextMessage;
import org.liufeng.weixin.util.MessageUtil;

/**
 * 核心服务类
 * 
 * @author liufeng
 * @date 2013-05-20
 */
public class CoreService {
	/**
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return
	 */
	public static String processRequest(HttpServletRequest request, String str) {
		String respMessage = null;
		try {
			// xml请求解析
			Map<String, String> requestMap = MessageUtil.parseXml(request);

			// 发送方帐号（open_id）
			String fromUserName = requestMap.get("FromUserName");
			// 公众帐号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");

			// 默认回复此文本消息
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			textMessage.setFuncFlag(0);
			// 由于href属性值必须用双引号引起，这与字符串本身的双引号冲突，所以要转义
			textMessage.setContent("欢迎访问<a href=\"http://blog.csdn.net/lyq8479\">柳峰的博客</a>!");
			// 将文本消息对象转换成xml字符串
			respMessage = MessageUtil.textMessageToXml(textMessage);

			// 文本消息
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				// 接收用户发送的文本消息内容
				String content = requestMap.get("Content");

				// 创建图文消息
				NewsMessage newsMessage = new NewsMessage();
				newsMessage.setToUserName(fromUserName);
				newsMessage.setFromUserName(toUserName);
				newsMessage.setCreateTime(new Date().getTime());
				newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
				newsMessage.setFuncFlag(0);

				List<Article> articleList = new ArrayList<Article>();
				// 单图文消息
				if ("1".equals(content)) {
					Article article = new Article();
					article.setTitle("旅行小助手");
					article.setDescription("打造出行全新智能平台，让您体会出行便利。发送以下关键字触发对应功能：聊天 天气 笑话 糗事 故事 新闻 翻译 美食 音乐 游戏 生活查询");
					article.setPicUrl("http://noodlenote1.sinaapp.com/images/hotel.jpg");
					article.setUrl("");
					articleList.add(article);
					// 设置图文消息个数
					newsMessage.setArticleCount(articleList.size());
					// 设置图文消息包含的图文集合
					newsMessage.setArticles(articleList);
					// 将图文消息对象转换成xml字符串
					respMessage = MessageUtil.newsMessageToXml(newsMessage);
				}
				// 多图文消息
				else if ("3".equals(content)) {
					Article article1 = new Article();
					article1.setTitle("微信公众帐号开发教程\n引言");
					article1.setDescription("");
					article1.setPicUrl("http://0.xiaoqrobot.duapp.com/images/avatar_liufeng.jpg");
					article1.setUrl("http://blog.csdn.net/lyq8479/article/details/8937622");

					Article article2 = new Article();
					article2.setTitle("第2篇\n微信公众帐号的类型");
					article2.setDescription("");
					article2.setPicUrl("http://avatar.csdn.net/1/4/A/1_lyq8479.jpg");
					article2.setUrl("http://blog.csdn.net/lyq8479/article/details/8941577");

					Article article3 = new Article();
					article3.setTitle("第3篇\n开发模式启用及接口配置");
					article3.setDescription("");
					article3.setPicUrl("http://avatar.csdn.net/1/4/A/1_lyq8479.jpg");
					article3.setUrl("http://blog.csdn.net/lyq8479/article/details/8944988");

					articleList.add(article1);
					articleList.add(article2);
					articleList.add(article3);
					newsMessage.setArticleCount(articleList.size());
					newsMessage.setArticles(articleList);
					respMessage = MessageUtil.newsMessageToXml(newsMessage);
				}
				// 多图文消息---首条消息不含图片
				else if ("4".equals(content)) {
					Article article1 = new Article();
					article1.setTitle("微信公众帐号开发教程Java版");
					article1.setDescription("");
					// 将图片置为空
					article1.setPicUrl("");
					article1.setUrl("http://blog.csdn.net/lyq8479");

					Article article2 = new Article();
					article2.setTitle("第4篇\n消息及消息处理工具的封装");
					article2.setDescription("");
					article2.setPicUrl("http://avatar.csdn.net/1/4/A/1_lyq8479.jpg");
					article2.setUrl("http://blog.csdn.net/lyq8479/article/details/8949088");

					Article article3 = new Article();
					article3.setTitle("第5篇\n各种消息的接收与响应");
					article3.setDescription("");
					article3.setPicUrl("http://avatar.csdn.net/1/4/A/1_lyq8479.jpg");
					article3.setUrl("http://blog.csdn.net/lyq8479/article/details/8952173");

					Article article4 = new Article();
					article4.setTitle("第6篇\n文本消息的内容长度限制揭秘");
					article4.setDescription("");
					article4.setPicUrl("http://avatar.csdn.net/1/4/A/1_lyq8479.jpg");
					article4.setUrl("http://blog.csdn.net/lyq8479/article/details/8967824");

					articleList.add(article1);
					articleList.add(article2);
					articleList.add(article3);
					articleList.add(article4);
					newsMessage.setArticleCount(articleList.size());
					newsMessage.setArticles(articleList);
					respMessage = MessageUtil.newsMessageToXml(newsMessage);
				}
				// 多图文消息---最后一条消息不含图片
				else if ("5".equals(content)) {
					Article article1 = new Article();
					article1.setTitle("第7篇\n文本消息中换行符的使用");
					article1.setDescription("");
					article1.setPicUrl("http://0.xiaoqrobot.duapp.com/images/avatar_liufeng.jpg");
					article1.setUrl("http://blog.csdn.net/lyq8479/article/details/9141467");

					Article article2 = new Article();
					article2.setTitle("第8篇\n文本消息中使用网页超链接");
					article2.setDescription("");
					article2.setPicUrl("http://avatar.csdn.net/1/4/A/1_lyq8479.jpg");
					article2.setUrl("http://blog.csdn.net/lyq8479/article/details/9157455");

					Article article3 = new Article();
					article3.setTitle("如果觉得文章对你有所帮助，请通过博客留言或关注微信公众帐号xiaoqrobot来支持柳峰！");
					article3.setDescription("");
					// 将图片置为空
					article3.setPicUrl("");
					article3.setUrl("http://blog.csdn.net/lyq8479");

					articleList.add(article1);
					articleList.add(article2);
					articleList.add(article3);
					newsMessage.setArticleCount(articleList.size());
					newsMessage.setArticles(articleList);
					respMessage = MessageUtil.newsMessageToXml(newsMessage);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respMessage;
	}

	/**
	 * emoji表情转换(hex -> utf-16)
	 * 
	 * @param hexEmoji
	 * @return
	 */
	public static String emoji(int hexEmoji) {
		return String.valueOf(Character.toChars(hexEmoji));
	}
	
	/**
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return
	 */
	public static String processRequest(HttpServletRequest request) {
		String respMessage = null;
		try {
			// 默认返回的文本消息内容
			String respContent = "请求处理异常，请稍候尝试！";

			// xml请求解析
			Map<String, String> requestMap = MessageUtil.parseXml(request);

			// 发送方帐号（open_id）
			String fromUserName = requestMap.get("FromUserName");
			// 公众帐号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");

			// 回复文本消息
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			textMessage.setFuncFlag(0);

			// 文本消息
			if (msgType.equalsIgnoreCase(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				respContent = "您发送的是文本消息！";
			}
			// 图片消息
			else if (msgType.equalsIgnoreCase(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
				respContent = "您发送的是图片消息！";
			}
			// 地理位置消息
			else if (msgType.equalsIgnoreCase(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
				respContent = "您发送的是地理位置消息！";
			}
			// 链接消息
			else if (msgType.equalsIgnoreCase(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
				respContent = "您发送的是链接消息！";
			}
			// 音频消息
			else if (msgType.equalsIgnoreCase(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
				respContent = "您发送的是音频消息！";
			}
			// 事件推送
			else if (msgType.equalsIgnoreCase(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				// 事件类型
				String eventType = requestMap.get("Event");

				// 订阅
				if (eventType.equalsIgnoreCase(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					respContent = "谢谢您的关注！";
				}
				// 取消订阅
				else if (eventType.equalsIgnoreCase(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
					// TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
				}
				// 自定义菜单点击事件
				else if (eventType.equalsIgnoreCase(MessageUtil.EVENT_TYPE_CLICK)) {
					String eventKey = requestMap.get("EventKey");
					// TODO 自定义菜单权没有开放，暂不处理该类消息
					if (eventKey.equals("11")) {  
                        respContent = "天气预报菜单项被点击！";  
                    } else if (eventKey.equalsIgnoreCase("12")) {
                        respContent = "公交查询菜单项被点击！";  
                    } else if (eventKey.equalsIgnoreCase("13")) {  
                        respContent = "周边搜索菜单项被点击！";  
                    } else if (eventKey.equalsIgnoreCase("14")) {  
                        respContent = "历史上的今天菜单项被点击！";  
                    } else if (eventKey.equalsIgnoreCase("21")) {  
                        respContent = "歌曲点播菜单项被点击！";  
                    } else if (eventKey.equalsIgnoreCase("22")) {  
                        respContent = "经典游戏菜单项被点击！";  
                    } else if (eventKey.equalsIgnoreCase("23")) {  
                        respContent = "美女电台菜单项被点击！";  
                    } else if (eventKey.equals("24")) {  
                        respContent = "人脸识别菜单项被点击！";  
                    } else if (eventKey.equalsIgnoreCase("25")) {  
                        respContent = "聊天唠嗑菜单项被点击！";  
                    } else if (eventKey.equalsIgnoreCase("31")) {  
                        respContent = "Q友圈菜单项被点击！";  
                    } else if (eventKey.equalsIgnoreCase("32")) {  
                        respContent = "电影排行榜菜单项被点击！";  
                    } else if (eventKey.equalsIgnoreCase("33")) {  
                        respContent = "幽默笑话菜单项被点击！";  
                    }  
				}
			}
			textMessage.setContent(respContent);
			respMessage = MessageUtil.textMessageToXml(textMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return respMessage;
	}
}