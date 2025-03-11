package com.provizit.dnp.MVVM.RequestModels;

public class SetUpLoginModelRequest {

    String id;
    String type;
    String val;
    String password;
    String mverify;

    public SetUpLoginModelRequest(String id, String type, String val, String password, String mverify) {
        this.id = id;
        this.type = type;
        this.val = val;
        this.password = password;
        this.mverify = mverify;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMverify() {
        return mverify;
    }

    public void setMverify(String mverify) {
        this.mverify = mverify;
    }
}
