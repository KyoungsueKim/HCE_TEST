package com.example.hce_test.utils;

/* loaded from: classes.dex */
public class ReqAttendCertificateForPro {
    public static final String VERIFY_TYPE_BEACON = "B";
    public static final String VERIFY_TYPE_NUMBER = "N";
    private String cancelSeq;
    private String classSort;
    private String day;
    private String failProc;
    private String lectureId;
    private String makeupYN;
    private String verifyNum;
    private String verifyTime;
    private String verifyType;

    public String getCancelSeq() {
        return this.cancelSeq;
    }

    public String getClassSort() {
        return this.classSort;
    }

    public String getDay() {
        return this.day;
    }

    public String getFailProc() {
        return this.failProc;
    }

    public String getLectureId() {
        return this.lectureId;
    }

    public String getMakeupYN() {
        return this.makeupYN;
    }

    public String getVerifyNum() {
        return this.verifyNum;
    }

    public String getVerifyTime() {
        return this.verifyTime;
    }

    public String getVerifyType() {
        return this.verifyType;
    }

    public void setCancelSeq(String str) {
        this.cancelSeq = str;
    }

    public void setClassSort(String str) {
        this.classSort = str;
    }

    public void setDay(String str) {
        this.day = str;
    }

    public void setFailProc(String str) {
        this.failProc = str;
    }

    public void setLectureId(String str) {
        this.lectureId = str;
    }

    public void setMakeupYN(String str) {
        this.makeupYN = str;
    }

    public void setVerifyNum(String str) {
        this.verifyNum = str;
    }

    public void setVerifyTime(String str) {
        this.verifyTime = str;
    }

    public void setVerifyType(String str) {
        this.verifyType = str;
    }

    public String toString() {
        return "ReqAttendCertificateForPro(lectureId=" + getLectureId() + ", day=" + getDay() + ", verifyTime=" + getVerifyTime() + ", verifyNum=" + getVerifyNum() + ", verifyType=" + getVerifyType() + ", failProc=" + getFailProc() + ", classSort=" + getClassSort() + ", makeupYN=" + getMakeupYN() + ", cancelSeq=" + getCancelSeq() + ")";
    }
}