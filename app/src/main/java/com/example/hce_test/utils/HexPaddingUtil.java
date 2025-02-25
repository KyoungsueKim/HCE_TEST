package com.example.hce_test.utils;

import android.text.TextUtils;

public class HexPaddingUtil {
    /* renamed from: CSP */
    public static String padRight(String hexString, int targetLength) {
        int length = !TextUtils.isEmpty(hexString) ? targetLength - hexString.length() : targetLength;
        if (length <= 0) {
            return hexString.substring(0, targetLength);
        }
        String str = "";
        for (int i = 0; i < length / 2; i++) {
            str = StringBuilderUtils.concat(str, "20");
        }
        return StringBuilderUtils.concat(hexString, str);
    }

    /* renamed from: iSj */
    public static String iso7816PadHexString(String hexString, int targetLength) {
        String padded;
        int length = hexString.length() % 32;
        if (length % 32 != 0) {
            padded = StringBuilderUtils.concat(hexString, "80");
            for (int i = 1; i < (32 - length) / 2; i++) {
                padded = StringBuilderUtils.concat(padded, "00");
            }
        } else {
            padded = StringBuilderUtils.concat(hexString, "80000000000000000000000000000000");
        }
        if (padded.length() < targetLength) {
            int length2 = padded.length();
            for (int i2 = 0; i2 < (targetLength - length2) / 2; i2++) {
                padded = StringBuilderUtils.concat(padded, "00");
            }
        }
        return padded.substring(0, targetLength);
    }
}