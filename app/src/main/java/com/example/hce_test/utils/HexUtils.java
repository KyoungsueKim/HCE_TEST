package com.example.hce_test.utils;

/* renamed from: a.b.a.a.b.h.XJG */
/* loaded from: classes.dex */
public class HexUtils {

    /* renamed from: iSj */
    public static String[] hexArray1 = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", ApiConstants.DEVICE_TYPE, ReqAttendCertificateForPro.VERIFY_TYPE_BEACON, "C", "D", AttendApiConstants.CODE_LAST_PAGE, "F"};

    /* renamed from: CSP */
    public static final char[] hexArray2 = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /* renamed from: CSP */
    public static String byteToHexString(byte b) {
        StringBuffer stringBuffer = new StringBuffer();
        char[] cArr = hexArray2;
        stringBuffer.append(cArr[(b >>> 4) & 15]);
        stringBuffer.append(cArr[b & 15]);
        return stringBuffer.toString();
    }

    /* renamed from: Hup */
    public static byte[] hexStringToBytes(String str) {
        return hexStringToByteArray(str, str.length());
    }

    /* renamed from: TPT */
    public static byte[] hexStringToByteArray(String str, int i) {
        byte[] bArr = new byte[i / 2];
        for (int i2 = 0; i2 < i; i2 += 2) {
            bArr[i2 / 2] = (byte) (Character.digit(str.charAt(i2 + 1), 16) + (Character.digit(str.charAt(i2), 16) << 4));
        }
        return bArr;
    }

    /* renamed from: XJG */
    public static char intToHexChar(int i) {
        if (i < 0 || i > 15) {
            throw new IllegalArgumentException();
        }
        return "0123456789ABCDEF".charAt(i);
    }

    /* renamed from: iSj */
    public static int hexCharToInt(char c) {
        if (c >= '0' && c <= '9') {
            return c - '0';
        }
        char c2 = 'A';
        if (c < 'A' || c > 'F') {
            c2 = 'a';
            if (c < 'a' || c > 'f') {
                throw new IllegalArgumentException();
            }
        }
        return (c - c2) + 10;
    }

    /* renamed from: jSG */
    public static String xorHexStrings(String str, String str2) {
        int length = str.length();
        char[] cArr = new char[length];
        for (int i = 0; i < length; i++) {
            cArr[i] = intToHexChar(hexCharToInt(str.charAt(i)) ^ hexCharToInt(str2.charAt(i)));
        }
        return new String(cArr);
    }

    /* renamed from: mlC */
    public static String bytesToHex(byte[] bArr, int i) {
        if (bArr == null || i <= 0) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer(i * 2);
        for (int i2 = 0; i2 < i; i2++) {
            stringBuffer.append(hexArray1[(byte) (((byte) (((byte) (bArr[i2] & 240)) >>> 4)) & 15)]);
            stringBuffer.append(hexArray1[(byte) (bArr[i2] & 15)]);
        }
        return new String(stringBuffer);
    }

    /* renamed from: oAP */
    public static String bytesArrayToHexString(byte[] bArr) {
        return bytesToHex(bArr, bArr.length);
    }
}