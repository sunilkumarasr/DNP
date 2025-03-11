package com.provizit.dnp.Utilities;

import java.io.Serializable;
import java.util.ArrayList;

public class ScheduledetailsModel implements Serializable {
    CommonObject _id;
    String comp_id;
    String emp_id;
    String start;
    String end;
    String supertype;
    Long from_time;
    Long to_time;
    Long from_date;
    Long to_date;
    ArrayList<DateDetails> dates;
    EmpData employee;
    int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public CommonObject get_id() {
        return _id;
    }

    public void set_id(CommonObject _id) {
        this._id = _id;
    }

    public String getComp_id() {
        return comp_id;
    }

    public void setComp_id(String comp_id) {
        this.comp_id = comp_id;
    }

    public String getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getSupertype() {
        return supertype;
    }

    public void setSupertype(String supertype) {
        this.supertype = supertype;
    }

    public Long getFrom_date() {
        return from_date;
    }

    public void setFrom_date(Long from_date) {
        this.from_date = from_date;
    }

    public Long getTo_date() {
        return to_date;
    }

    public void setTo_date(Long to_date) {
        this.to_date = to_date;
    }

    public Long getFrom_time() {
        return from_time;
    }

    public void setFrom_time(Long from_time) {
        this.from_time = from_time;
    }

    public Long getTo_time() {
        return to_time;
    }

    public void setTo_time(Long to_time) {
        this.to_time = to_time;
    }

    //    public ArrayList<Long> getDates() {
//        return dates;
//    }
//
//    public void setDates(ArrayList<Long> dates) {
//        this.dates = dates;
//    }

    public EmpData getEmployee() {
        return employee;
    }

    public void setEmployee(EmpData employee) {
        this.employee = employee;
    }
    public ArrayList<DateDetails> getDates() {
        return dates;
    }

    public void setDates(ArrayList<DateDetails> dates) {
        this.dates = dates;
    }

}
