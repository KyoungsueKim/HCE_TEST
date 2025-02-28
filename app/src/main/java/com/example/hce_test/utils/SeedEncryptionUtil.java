package com.example.hce_test.utils;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.spongycastle.jce.provider.BouncyCastleProvider;

/* renamed from: a.b.a.a.b.h.TPT */
/* loaded from: classes.dex */
public class SeedEncryptionUtil {
    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    /* renamed from: CSP */
    public static byte[] convertHexStringToBytes(String hexString) {
        int length = hexString.length();
        byte[] bArr = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            bArr[i / 2] = (byte) (Character.digit(hexString.charAt(i + 1), 16) + (Character.digit(hexString.charAt(i), 16) << 4));
        }
        return bArr;
    }

    /* renamed from: iSj */
    public static String padHexString(String hexString, int targetLength) {
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
            int currentLength = padded.length();
            for (int i2 = 0; i2 < (targetLength - currentLength) / 2; i2++) {
                padded = StringBuilderUtils.concat(padded, "00");
            }
        }
        return padded.substring(0, targetLength);
    }

    /* renamed from: jSG */
    public static byte[] encryptData(String keyHex, byte[] data) throws InvalidAlgorithmParameterException, NoSuchPaddingException, ShortBufferException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, NoSuchProviderException, InvalidKeyException {
        return encryptData(convertHexStringToBytes(keyHex), data);
    }

    /* renamed from: mlC, reason: collision with other method in class */
    public static byte[] m10mlC(String str, byte[] bArr) throws InvalidAlgorithmParameterException, NoSuchPaddingException, ShortBufferException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, NoSuchProviderException, InvalidKeyException {
        return encryptData(str, bArr);
    }

    /* renamed from: oAP */
    public static byte[] encryptData(byte[] keyBytes, byte[] data) throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, InvalidKeyException, ShortBufferException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("SEED/CBC/NoPadding");
        cipher.init(1, new SecretKeySpec(keyBytes, "SEED"), new IvParameterSpec(CardConstants.IV));
        int length = data.length;
        int i = 16 - (length % 16);
        byte[] bArr = i == 16 ? new byte[length] : new byte[i + length];
        System.arraycopy(data, 0, bArr, 0, length);
        for (int i2 = length; i2 < bArr.length; i2++) {
            if (i2 == length) {
                bArr[i2] = RevocationKeyTags.CLASS_DEFAULT;
            } else {
                bArr[i2] = 0;
            }
        }
        int totalLength = bArr.length;
        byte[] encryptedOutput = new byte[totalLength];
        cipher.doFinal(encryptedOutput, cipher.update(bArr, 0, totalLength, encryptedOutput, 0));
        return encryptedOutput;
    }
}