package com.example.hce_test.utils;

public class CardConstants {

    /* renamed from: iSj */
    public static final byte[] IV = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    /* renamed from: a.b.a.a.b.b.a$a */
    public enum ResultField {
        resultCd,
        resultMsg,
        resultCode,
        resultMessage
    }

    /* renamed from: a.b.a.a.b.b.a$b */
    public enum CardKind {
        HCE("3"),
        MOBILE_QR("4");


        /* renamed from: cd */
        public String code;

        CardKind(String code) {
            this.code = code;
        }

        /* renamed from: a */
        public String getCode() {
            return this.code;
        }

        @Override // java.lang.Enum
        public String toString() {
            StringBuilder build = StringBuilderUtils.build("CardKindCd{cd='");
            build.append(this.code);
            build.append('\'');
            build.append('}');
            return build.toString();
        }
    }
}