package com.example.hce_test.utils;

public class TLVBuilder {
    /* renamed from: iSj */
    public static String buildTLV(String tag, String value) {
        String lengthField;
        StringBuilder build;
        String format;
        int length = value.length() / 2;
        if (length < 128) {
            build = StringBuilderUtils.build("");
            format = String.format("%02X", Integer.valueOf(length));
        } else {
            if (length >= 256) {
                if (length >= 65536) {
                    return "";
                }
                lengthField = "82" + String.format("%02X", Integer.valueOf(length >> 8)) + String.format("%02X", Integer.valueOf(length));
                return tag + lengthField + value;
            }
            build = StringBuilderUtils.build("81");
            format = String.format("%02X", Integer.valueOf(length));
        }
        build.append(format);
        lengthField = build.toString();
        return tag + lengthField + value;
    }
}