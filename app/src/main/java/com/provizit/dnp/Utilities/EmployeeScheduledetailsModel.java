package com.provizit.dnp.Utilities;

import com.provizit.dnp.Services.OutlookItems;

import java.io.Serializable;
import java.util.ArrayList;

public class EmployeeScheduledetailsModel implements Serializable {
    public Integer result;
    public Long total_counts;
    public String incomplete_data;
    public ArrayList<ScheduledetailsModel> items;

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public Long getTotal_counts() {
        return total_counts;
    }

    public void setTotal_counts(Long total_counts) {
        this.total_counts = total_counts;
    }

    public String getIncomplete_data() {
        return incomplete_data;
    }

    public void setIncomplete_data(String incomplete_data) {
        this.incomplete_data = incomplete_data;
    }

    public ArrayList<ScheduledetailsModel> getItems() {
        return items;
    }

    public void setItems(ArrayList<ScheduledetailsModel> items) {
        this.items = items;
    }
}
