package com.example.hce_test;


import android.util.Log;
import java.util.Arrays;

public class CommandProcessor {

    private static final String TAG = "CommandProcessor";
    // 응답 SW 코드들
    public static final byte[] SW_SUCCESS = {(byte)0x90, 0x00};
    public static final byte[] SW_INS_NOT_SUPPORTED = {(byte)0x6D, 0x00};
    public static final byte[] SW_UNKNOWN = {(byte)0x6F, 0x00};

    // INS 값 (각 명령의 INS 바이트; 실제 프로토콜에 맞게 조정)
    private static final byte INS_SELECT   = (byte)0xA4; // SELECT
    private static final byte INS_READ     = (byte)0xB0; // READ (예제에서는 0xB0 사용)
    private static final byte INS_UPDATE   = (byte)0x82; // UPDATE (원본에서는 -126, 즉 0x82)
    private static final byte INS_DELETE   = (byte)0x50; // DELETE
    private static final byte INS_GET_DATA = (byte)0xCA; // GET DATA

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
            default:
                return SW_INS_NOT_SUPPORTED;
        }
    }

    // SELECT 명령 처리: 예제에서는 AID 선택 후 TLV 형식의 데이터를 리턴
    private static byte[] processSelect(byte[] apdu) {
        Log.d(TAG, "Processing SELECT command");
        // 예시 TLV 데이터 (실제 AID 등 원하는 데이터를 구성)
        String tlvResponse = "6F208407A0000002471001A520500A564953412043415244";
        byte[] responseData = hexStringToByteArray(tlvResponse);
        return concatenateArrays(responseData, SW_SUCCESS);
    }

    // READ 명령 처리: 예제에서는 "READ_DATA" 문자열을 리턴
    private static byte[] processRead(byte[] apdu) {
        Log.d(TAG, "Processing READ command");
        String data = "READ_DATA";
        byte[] dataBytes = data.getBytes();
        return concatenateArrays(dataBytes, SW_SUCCESS);
    }

    // UPDATE 명령 처리: 예제에서는 업데이트 성공 시 SW_SUCCESS만 리턴
    private static byte[] processUpdate(byte[] apdu) {
        Log.d(TAG, "Processing UPDATE command");
        // 업데이트 처리 로직 (예: 입력 데이터 검증 등) 후 성공 시 SW_SUCCESS 리턴
        return SW_SUCCESS;
    }

    // DELETE 명령 처리: 예제에서는 "DELETED" 문자열과 함께 SW_SUCCESS를 리턴
    private static byte[] processDelete(byte[] apdu) {
        Log.d(TAG, "Processing DELETE command");
        String data = "DELETED";
        byte[] dataBytes = data.getBytes();
        return concatenateArrays(dataBytes, SW_SUCCESS);
    }

    // GET DATA 명령 처리: 예제에서는 "GET_DATA_RESPONSE" 문자열을 리턴
    private static byte[] processGetData(byte[] apdu) {
        Log.d(TAG, "Processing GET DATA command");
        String data = "GET_DATA_RESPONSE";
        byte[] dataBytes = data.getBytes();
        return concatenateArrays(dataBytes, SW_SUCCESS);
    }

    // 헥사 문자열을 바이트 배열로 변환하는 유틸리티 메서드
    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for(int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
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