package com.provizit.dnp.Services;



import java.io.Serializable;
import java.util.ArrayList;

public class OutlookModel implements Serializable {
    public Integer result;
    public ArrayList<OutlookItems> items;
    public String incomplete_data;

    public String getIncomplete_data() {
        return incomplete_data;
    }

    public void setIncomplete_data(String incomplete_data) {
        this.incomplete_data = incomplete_data;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public ArrayList<OutlookItems> getItems() {
        return items;
    }

    public void setItems(ArrayList<OutlookItems> items) {
        this.items = items;
    }
}
