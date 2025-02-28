package com.example.hce_test;


import android.util.Log;

import com.example.hce_test.utils.HexPaddingUtil;
import com.example.hce_test.utils.HexUtils;
import com.example.hce_test.utils.HexUtilsHelper;
import com.example.hce_test.utils.SeedEncryptionUtil;
import com.example.hce_test.utils.StringBuilderUtils;
import com.example.hce_test.utils.TLVBuilder;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;

public class CommandProcessor {

    private static final String TAG = "CommandProcessor";
    // 응답 SW 코드들
    public static final byte[] SW_SUCCESS = {(byte) 0x90, 0x00};
    public static final byte[] SW_INS_NOT_SUPPORTED = {(byte) 0x6D, 0x00};
    public static final byte[] SW_UNKNOWN = {(byte) 0x6F, 0x00};

    // INS 값 (각 명령의 INS 바이트; 실제 프로토콜에 맞게 조정)
    private static final byte INS_SELECT = (byte) 0xA4; // SELECT
    private static final byte INS_READ = (byte) 0xB0; // READ (예제에서는 0xB0 사용)
    private static final byte INS_UPDATE = (byte) 0x82; // UPDATE (원본에서는 -126, 즉 0x82)
    private static final byte INS_DELETE = (byte) 0x50; // DELETE
    private static final byte INS_GET_DATA = (byte) 0xCA; // GET DATA
    private static final byte INS_PROPRIETARY = (byte) 0xA5; // PROPRIETARY

    public static byte[] processApdu(byte[] commandApdu) {
        // 간단히 APDU의 INS 바이트(두 번째 바이트)를 확인
        if (commandApdu == null || commandApdu.length < 4) {
            return SW_UNKNOWN;
        }
        byte ins = commandApdu[1];
        Log.d(TAG, "Processing INS: " + String.format("%02X", ins));
        switch (ins) {
            case INS_SELECT:
                return processSelect(commandApdu);
            case INS_READ:
                return processRead(commandApdu);
            case INS_UPDATE:
                return processUpdate(commandApdu);
            case INS_DELETE:
                return processDelete(commandApdu);
            case INS_GET_DATA:
                return processGetData(commandApdu);
            case INS_PROPRIETARY:
                return processProprietary(commandApdu);
            default:
                return SW_INS_NOT_SUPPORTED;
        }
    }

    private static String generateTLVData(String cardId, String userId, String cardIssueNo) {
        String hexUserId = HexUtils.bytesArrayToHexString(userId.getBytes());
        if (hexUserId.length() < 20) {
            hexUserId = HexPaddingUtil.padRight(hexUserId, 20);
        }
        String hexCardIssueNo = HexUtils.bytesArrayToHexString(cardIssueNo.getBytes());
        String tag = "[generateTLVData]";
        Log.i(tag, "hexUserId + hexCardIssueNo -> " + hexUserId + hexCardIssueNo);
        String sb = hexUserId + hexCardIssueNo;
        String tlvA5 = TLVBuilder.buildTLV("A5", TLVBuilder.buildTLV("50", HexPaddingUtil.padRight(HexUtils.xorHexStrings(sb, "BEC6C1D6B4EBC7D0B1B320"), 40)) + TLVBuilder.buildTLV("BF0C", "0100000000000000"));
        Log.i(tag, "tlv_a5 -> " + tlvA5);
        String tlv6F = TLVBuilder.buildTLV("6F", TLVBuilder.buildTLV("84", HexPaddingUtil.padRight(HexUtilsHelper.bytesToHexString(cardId.getBytes()), 16)) + tlvA5);
        Log.i(tag, "tlv_6f -> " + tlv6F);
        StringBuilderUtils.logInfo("result -> ", tlv6F + "9000", tag);
        return tlv6F + "9000";
    }

    private static byte[] extractLcData(byte[] apdu) {
        // APDU가 null이거나 최소 헤더 길이(5바이트) 미만이면 빈 배열 반환
        if (apdu == null || apdu.length < 5) {
            return new byte[0];
        }

        // LC 값은 5번째 바이트(index 4)에 위치하며, unsigned 값으로 처리
        int lc = apdu[4] & 0xFF;

        // APDU 전체 길이가 헤더(5바이트) + LC 값에 명시된 데이터 길이보다 작으면 빈 배열 반환
        if (apdu.length < 5 + lc) {
            return new byte[0];
        }

        // LC 길이만큼 데이터를 잘라내어 반환
        byte[] data = new byte[lc];
        System.arraycopy(apdu, 5, data, 0, lc);
        return data;
    }


    // SELECT 명령 처리: AID 선택 후 TLV 형식의 데이터를 생성하여 "9000" SW를 추가한 응답을 반환
    private static byte[] processSelect(byte[] apdu) {
        final String TAG = "[APDU SELECT]";
        // apdu가 없으면 에러 응답
        if (apdu == null || apdu.length == 0) {
            return ApduStatus.ERROR_6400.getValue();
        }

        // 원본에서는 commandData.getData()의 값을 사용하므로 여기서는 전달된 apdu를 그대로 사용
        String dataHex = HexUtilsHelper.byteArrayToHexString(apdu);
        Log.i(TAG, "data ->" + dataHex);

        // TLV 빌드를 통해 응답 APDU 구성
        String tlv50 = TLVBuilder.buildTLV("50", HexUtilsHelper.bytesToHexString("SECURE_ID".getBytes()));
        String tlvBF0C = TLVBuilder.buildTLV("BF0C", "0000000000000000");
        String tlv84 = TLVBuilder.buildTLV("84", "D41000000702004944");
        String tlvA5 = TLVBuilder.buildTLV("A5", tlv50 + tlvBF0C);
        String tlv6F = TLVBuilder.buildTLV("6F", tlv84 + tlvA5);

        Log.i(TAG, "resultApdu -> " + tlv6F);

        // TLV6F에 "9000" 상태 단어를 추가하여 최종 APDU 응답 생성
        return HexUtilsHelper.hexStringToByteArray(tlv6F + "9000");
    }

    // READ 명령 처리:
    private static byte[] processRead(byte[] apdu) {
        String tag = "[APDU READ]";
        String orgName = "AJOU";
        String orgNameHex = HexUtilsHelper.bytesToHexString(orgName.getBytes());
        StringBuilder sb = StringBuilderUtils.build("50, ");
        sb.append(orgName);
        sb.append(", ");
        sb.append(orgNameHex);
        Log.i(tag, sb.toString());
        String tlv50 = TLVBuilder.buildTLV("50", HexPaddingUtil.padRight(orgNameHex, 40));
        String tlvBF0C = TLVBuilder.buildTLV("BF0C", "0100000000000000");
        String tlv6F = TLVBuilder.buildTLV("6F", TLVBuilder.buildTLV("84", HexPaddingUtil.padRight(HexUtilsHelper.bytesToHexString(extractLcData(apdu)), 16)) + TLVBuilder.buildTLV("A5", tlv50 + tlvBF0C));
        Log.i(tag, "resultApdu -> " + tlv6F);
        return HexUtilsHelper.hexStringToByteArray(tlv6F + "9000");
    }

    // UPDATE 명령 처리: 예제에서는 업데이트 성공 시 SW_SUCCESS만 리턴
    private static byte[] processUpdate(byte[] apdu) {
        final String TAG = "[APDU UPDATE]";
        Log.d(TAG, "Processing UPDATE command");
        // 업데이트 처리 로직 (예: 입력 데이터 검증 등) 후 성공 시 SW_SUCCESS 리턴
        return SW_SUCCESS;
    }

    // DELETE 명령 처리: 예제에서는 "DELETED" 문자열과 함께 SW_SUCCESS를 리턴
    private static byte[] processDelete(byte[] apdu) {
        final String TAG = "[APDU DELETE]";
        Log.d(TAG, "Processing DELETE command");
        String data = "DELETED";
        byte[] dataBytes = data.getBytes();
        return concatenateArrays(dataBytes, SW_SUCCESS);
    }

    // GET DATA 명령 처리: 예제에서는 "GET_DATA_RESPONSE" 문자열을 리턴
    private static byte[] processGetData(byte[] apdu) {
        final String TAG = "[APDU GET DATA]";
        Log.d(TAG, "Processing GET DATA command");
        String data = "GET_DATA_RESPONSE";
        byte[] dataBytes = data.getBytes();
        return concatenateArrays(dataBytes, SW_SUCCESS);
    }

    private static byte[] processProprietary(byte[] apdu) {
        final String TAG = "[APDU PROPRIETARY]";
        Log.d(TAG, "Processing PROPRIETARY command");
        final String userId = "202126869";
        final String cardIssueNo = "2";
        final String cardId = "115915";

        String data = generateTLVData(cardId, userId, cardIssueNo);
        return HexUtilsHelper.hexStringToByteArray(data);
    }

    // 헥사 문자열을 바이트 배열로 변환하는 유틸리티 메서드
    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    // 두 바이트 배열을 연결하는 유틸리티 메서드
    public static byte[] concatenateArrays(byte[] a, byte[] b) {
        byte[] result = new byte[a.length + b.length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }
}