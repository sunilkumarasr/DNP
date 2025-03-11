package com.provizit.dnp.Utilities;

import java.io.Serializable;

public class Inviteemodelclass implements Serializable {
    public Integer result;
    private Inviteeitem items;
    private Inviteeitem item;

    public Inviteeitem getItem() {
        return item;
    }

    public void setItem(Inviteeitem item) {
        this.item = item;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public Inviteeitem getItems() {
        return items;
    }

    public void setItems(Inviteeitem items) {
        this.items = items;
    }
}
