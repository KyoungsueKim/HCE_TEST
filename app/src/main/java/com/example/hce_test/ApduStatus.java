package com.example.hce_test;

public enum ApduStatus {
    RESP_SUCCESS((byte) -112, (byte) 0),
    ERROR_6A82((byte) 106, (byte) -126),
    ERROR_6283((byte) 98, (byte) -125),
    ERROR_6982((byte) 105, (byte) -126),
    ERROR_6985((byte) 105, (byte) -123),
    ERROR_6A86((byte) 106, (byte) -122),
    ERROR_6A88((byte) 106, (byte) -120),
    ERROR_6300((byte) 99, (byte) 0),
    ERROR_6400((byte) 100, (byte) 0),
    IV((byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0);

    public byte[] value;

    ApduStatus(byte... bArr) {
        this.value = bArr;
    }

    /* renamed from: a */
    public byte[] getValue() {
        return this.value;
    }
}