package com.provizit.dnp.MVVM.RequestModels;

public class UpdatePwdModelRequest {

    String comp_id;
    String pwd;
    String link;

    public UpdatePwdModelRequest(String comp_id, String pwd, String link) {
        this.comp_id = comp_id;
        this.pwd = pwd;
        this.link = link;
    }

    public String getComp_id() {
        return comp_id;
    }

    public void setComp_id(String comp_id) {
        this.comp_id = comp_id;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
