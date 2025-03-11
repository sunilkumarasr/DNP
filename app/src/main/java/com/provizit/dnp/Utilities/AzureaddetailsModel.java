package com.provizit.dnp.Utilities;

import java.io.Serializable;

public class AzureaddetailsModel implements Serializable {

    public Integer result;
    public Long total_counts;
    public String incomplete_data;
    public AzureModel items;

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

    public AzureModel getItems() {
        return items;
    }

    public void setItems(AzureModel items) {
        this.items = items;
    }
}
