package com.example.hce_test.utils;

import android.text.TextUtils;

/* renamed from: a.b.a.a.b.h.iSj */
/* loaded from: classes.dex */
public class HexUtilsHelper {
    /* renamed from: CSP */
    public static String subArrayToHexString(byte[] bArr, int i, int i2) {
        if (bArr == null) {
            return "";
        }
        if (bArr.length >= i + i2) {
            StringBuilder sb = new StringBuilder();
            for (int i3 = 0; i3 < i2; i3++) {
                sb.append(Integer.toHexString((bArr[i + i3] & 255) | (-256)).substring(6));
            }
            return sb.toString();
        }
        throw new IllegalArgumentException("startPos(" + i + ")+length(" + i2 + ") > byteArray.length(" + bArr.length + ")");
    }

    /* renamed from: iSj */
    public static String bytesToHexString(byte[] bArr) {
        return bArr == null ? "" : subArrayToHexString(bArr, 0, bArr.length);
    }

    /* renamed from: jSG */
    public static String hexToString(String str) {
        int length = str.length();
        byte[] bArr = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            bArr[i / 2] = (byte) (Character.digit(str.charAt(i + 1), 16) + (Character.digit(str.charAt(i), 16) << 4));
        }
        return new String(bArr);
    }

    /* renamed from: mlC */
    public static byte[] hexStringToByteArray(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (str.startsWith("KISA/")) {
            str = str.substring(5);
        }
        int length = str.length();
        if (length % 2 == 1) {
            throw new IllegalArgumentException("For input string: \"" + str + "\"");
        }
        int i = length / 2;
        byte[] bArr = new byte[i];
        for (int i2 = 0; i2 < i; i2++) {
            int i3 = i2 * 2;
            bArr[i2] = (byte) Short.parseShort(str.substring(i3, i3 + 2), 16);
        }
        return bArr;
    }

    /* renamed from: oAP */
    public static String byteArrayToHexString(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (byte b : bArr) {
            stringBuffer.append(Integer.toString((b & 240) >> 4, 16));
            stringBuffer.append(Integer.toString(b & 15, 16));
        }
        return stringBuffer.toString();
    }
}