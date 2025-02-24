package com.example.hce_test;

import android.nfc.cardemulation.HostApduService;
import android.os.Bundle;
import android.util.Log;
import java.util.Arrays;

public class ApduService extends HostApduService {

    private static final String TAG = "ApduService";
    // 응답 SW 코드 (성공)
    private static final byte[] SW_SUCCESS = {(byte)0x90, 0x00};

    @Override
    public byte[] processCommandApdu(byte[] commandApdu, Bundle extras) {
        Log.d(TAG, "Received APDU: " + toHexString(commandApdu));
        if (commandApdu == null || commandApdu.length < 4) {
            return new byte[]{(byte)0x6F, 0x00}; // 에러 응답
        }
        // APDU 명령 처리를 CommandProcessor에 위임
        byte[] response = CommandProcessor.processApdu(commandApdu);
        Log.d(TAG, "Sending response: " + toHexString(response));
        return response;
    }

    @Override
    public void onDeactivated(int reason) {
        Log.d(TAG, "Deactivated: " + reason);
    }

    // 바이트 배열을 헥사 문자열로 변환 (로그용)
    private String toHexString(byte[] bytes) {
        if (bytes == null) return "null";
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }
}