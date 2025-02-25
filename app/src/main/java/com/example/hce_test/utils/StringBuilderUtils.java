package com.example.hce_test.utils;

import android.util.Log;

public class StringBuilderUtils {
    /* renamed from: CSP */
    public static StringBuilder build(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        return sb;
    }

    /* renamed from: iSj */
    public static String concat(String str, String str2) {
        return str + str2;
    }

    /* renamed from: jSG */
    public static StringBuilder appendWithDelimiter(StringBuilder sb, String str, char c, String str2) {
        sb.append(str);
        sb.append(c);
        sb.append(str2);
        return sb;
    }

    /* renamed from: oAP */
    public static void logInfo(String str, String str2, String str3) {
        Log.i(str3, str + str2);
    }
}