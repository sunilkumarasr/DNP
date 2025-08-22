package com.provizit.dnp.Services;



import java.io.Serializable;

public class OutlookItems implements Serializable {
    public String supertype;
    public String type;
    public String host;
    public String o_start;
    public String o_end;
    public String mid;
    public String rmid;
    public String invitees;
    public String c_date;
    public String duration;
    public String m_status;
    public String timezone;
    public String ml_status;
    public String name;
    public String rmname;
    public long start;
    public long end;
    public long t_start;
    public long t_end;
    public Boolean isOnlineMeeting;

    public Boolean getOnlineMeeting() {
        return isOnlineMeeting;
    }

    public void setOnlineMeeting(Boolean onlineMeeting) {
        isOnlineMeeting = onlineMeeting;
    }

    public String getRmname() {
        return rmname;
    }

    public void setRmname(String rmname) {
        this.rmname = rmname;
    }

    public String getName() {
        return name;
    }

    public String getSupertype() {
        return supertype;
    }

    public String getType() {
        return type;
    }

    public String getHost() {
        return host;
    }

    public String getO_start() {
        return o_start;
    }

    public String getO_end() {
        return o_end;
    }

    public String getMid() {
        return mid;
    }

    public String getRmid() {
        return rmid;
    }

    public String getInvitees() {
        return invitees;
    }

    public String getC_date() {
        return c_date;
    }

    public String getDuration() {
        return duration;
    }

    public String getM_status() {
        return m_status;
    }

    public String getTimezone() {
        return timezone;
    }

    public String getMl_status() {
        return ml_status;
    }

    public long getStart() {
        return start;
    }

    public long getEnd() {
        return end;
    }

    public long getT_start() {
        return t_start;
    }

    public long getT_end() {
        return t_end;
    }


}
