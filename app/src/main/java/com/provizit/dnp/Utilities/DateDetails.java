package com.provizit.dnp.Utilities;


import java.io.Serializable;

public class DateDetails implements Serializable {
    Long  date;
    Long  start;
    Long  end;

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public Long getStart() {
        return start;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public Long getEnd() {
        return end;
    }

    public void setEnd(Long end) {
        this.end = end;
    }
}