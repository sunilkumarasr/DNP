package com.provizit.dnp.MVVM.RequestModels;

public class OtpSendEmaiModelRequest {

    String val;
    String email;
    String sotp;
    String otp;

    public OtpSendEmaiModelRequest(String val,String email, String sotp, String otp) {
        this.val = val;
        this.email = email;
        this.sotp = sotp;
        this.otp = otp;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSotp() {
        return sotp;
    }

    public void setSotp(String sotp) {
        this.sotp = sotp;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

}