package com.xiechur.spider.util;

import org.springframework.util.Base64Utils;

import java.io.UnsupportedEncodingException;

public class Base64EncryptUtil {

	public static String encrypt(String strOld, String strKey) {
		byte[] data;
		try {
			data = strOld.getBytes("utf-8");
			byte[] newByte = new byte[data.length];
			byte[] keyData = strKey.getBytes();
			int keyIndex = 0;
			for (int x = data.length - 1; x >= 0; x--) {
				newByte[x] = (byte) (data[x] ^ keyData[keyIndex]);
				if (++keyIndex == keyData.length) {
					keyIndex = 0;
				}
			}
			String strOut = Base64Utils.encodeToString(newByte);
			return strOut;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String decrypt(String strOld, String strKey) {
		try {
			byte[] data = Base64Utils.decode(strOld.getBytes());
			byte[] newByte = new byte[data.length];
			byte[] keyData = strKey.getBytes();
			int keyIndex = 0;
			for (int x = data.length - 1; x >= 0; x--) {
				newByte[x] = (byte) (data[x] ^ keyData[keyIndex]);
				if (++keyIndex == keyData.length) {
					keyIndex = 0;
				}
			}
			return new String(newByte, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

}
