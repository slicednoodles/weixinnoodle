package com.noodle.weixin.component.tucao;

import java.util.Map;
import java.util.Random;

import com.noodle.common.cache.Cache;
import com.noodle.common.utils.AllConstants;
import com.noodle.common.utils.MessageUtils;
import com.noodle.v5.task.AckTask;
import com.noodle.weixin.pojo.VoiceMessage;

public class TucaoService {

	private String user = "gh_ba0807e1ac17";

	public String process(Map<String, String> requestMap, String message) {
		String fromUser = requestMap.get(AllConstants.FROM_USER_NAME);
		if (requestMap.get(AllConstants.MSG_TYPE).equalsIgnoreCase(
				AllConstants.EVENT)) {
			String event = requestMap.get(AllConstants.EVENT_KEY);
			if (AllConstants.SUBSCRIBE.equalsIgnoreCase(event)) {
				System.out.println("access 订阅");
				return MessageUtils
						.getBackXMLTypeText(fromUser, user,
								"欢迎关注全民吐槽，直接发送语音，把您看不惯的大声吼出来吧，您的吐槽将会让千万槽友听到！ 您也可以点击我要接槽，听听别人的怒吼。");
			} else if (AllConstants.JIE_CAO.equalsIgnoreCase(event)) {
				System.out.println("access 接槽  menu");
				System.out.println("Cache.tucaoCache.size() = "
						+ Cache.tucaoCache.size());
				if (Cache.tucaoCache.size() > 0) {
					int i = new Random().nextInt(Cache.tucaoCache.size());
					if ((i - 1) < 0) {
						i = 1;
					}
					VoiceMessage vm = Cache.tucaoCache.get(i - 1);
					return MessageUtils.getBackXMLTypeVoice(fromUser, user,
							vm.getMediaId());
				}
				return MessageUtils.getBackXMLTypeText(fromUser, user,
						"最近没人吐槽，您来一个吧");
			} else if (AllConstants.TU_CAO.equalsIgnoreCase(event)) {
				System.out.println("access 吐槽  menu");
				return MessageUtils.getBackXMLTypeText(fromUser, user,
						"直接发送语音，把您看不惯的大声吼出来吧!您的吐槽将会让千万槽友听到!");
			}
		} else if (requestMap.get(AllConstants.MSG_TYPE).equalsIgnoreCase(
				AllConstants.VOICE)) {
			System.out.println("access 语音吐槽");
			VoiceMessage vm = new VoiceMessage();
			vm.setCreateTime(Long.valueOf(requestMap
					.get(AllConstants.CREATE_TIME)));
			vm.setFormat(requestMap.get(AllConstants.VOICE_FORMAT));
			vm.setFromUserName(fromUser);
			vm.setMediaId(requestMap.get(AllConstants.MEDIA_ID));
			Cache.tucaoCache.add(vm);
			AckTask.saveMessage(AllConstants.TU_CAO, AllConstants.TU_CAO,
					message);
			return MessageUtils.getBackXMLTypeText(fromUser, user, "收到了您的吐槽");
		}
		return null;
	}
}
