package com.noodle.common.utils;

import org.apache.commons.lang.StringUtils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinyinUtils {
	private static HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
	private static String[] pinyin;

	// 转换单个字符

	public static String getCharacterPinYin(char c) {
		try {
			pinyin = PinyinHelper.toHanyuPinyinStringArray(c, format);
		} catch (BadHanyuPinyinOutputFormatCombination e) {

			e.printStackTrace();

		}

		// 如果c不是汉字，toHanyuPinyinStringArray会返回null

		if (pinyin == null)
			return null;

		// 只取一个发音，如果是多音字，仅取第一个发音

		return pinyin[0];

	}

	// 转换一个字符串

	public static String getStringPinYin(String str) {
		String duoyinziReturn = checkDuoyinzi(str);
		if (StringUtils.isNotEmpty(duoyinziReturn)) {
			return duoyinziReturn;
		}
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		format.setVCharType(HanyuPinyinVCharType.WITH_V);
		pinyin = null;
		StringBuilder sb = new StringBuilder();
		String tempPinyin = null;
		for (int i = 0; i < str.length(); ++i) {
			tempPinyin = getCharacterPinYin(str.charAt(i));
			if (tempPinyin == null) {
				// 如果str.charAt(i)非汉字，则保持原样
				sb.append(str.charAt(i));
			} else {
				sb.append(tempPinyin);
			}
		}
		return sb.toString();
	}

	private static String checkDuoyinzi(String str) {
		if ("秘鲁".equalsIgnoreCase(str)) {
			return "bilu";
		} else if ("东阿".equalsIgnoreCase(str)) {
			return "donge";
		} else if ("东莞".equalsIgnoreCase(str)) {
			return "dongguan";
		} else if ("会稽".equalsIgnoreCase(str)) {
			return "kuaiji";
		} else if ("厦门".equalsIgnoreCase(str)) {
			return "xiamen";
		}
		return null;
	}
}
