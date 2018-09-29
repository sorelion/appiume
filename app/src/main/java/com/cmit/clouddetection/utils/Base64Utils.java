package com.cmit.clouddetection.utils;

import android.util.Base64;

import java.io.UnsupportedEncodingException;

/**
 * 
 * @author wxj
 * 
 */

public class Base64Utils {
	// 加密
	public static String encode(String str) {
		String result = "";
		if (str != null) {
			try {
				/**使用android.util.Base64工具类
				 * Base64.NO_WRAP：base64转化是，如果字符串比较长会在中间及最后加换行符，使用这个参数会删除掉换行符
				 */
				result = new String(Base64.encode(str.getBytes("utf-8"),
						Base64.NO_WRAP), "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static String encode(byte[] pArray) {
		String result = "";
		if (pArray != null) {
			try {
				result = new String(Base64.encode(pArray, Base64.NO_WRAP),
						"utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	// 解密
	public static String decode(String str) {
		String result = "";
		if (str != null) {
			try {
				result = new String(Base64.decode(str, Base64.NO_WRAP), "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	// 解密
	public static byte[] decodeToBytes(String str) {
		byte[] result = null;
		if (str != null) {
			Base64.decode(str, Base64.NO_WRAP);
		}
		return result;
	}
}
