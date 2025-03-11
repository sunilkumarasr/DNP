package com.provizit.dnp.Calendar;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MyCalendar {
    // storing day - like Sun, Wed etc..,
    // date like 1, 2 etc..
    //Month 1..12
    // year .. 2019.. beyond

    private String day;
    private String date, month, year;
    private int pos;
    private long timemilli;

    public MyCalendar(int startday) {
    }

    public MyCalendar(String day, String date, String month, String year, int i,Long timemilli) {
        this.day = day;
        this.date = date;
        this.month = getMonthStr(month);
        this.year = year;
        this.timemilli = timemilli;
        this.pos =i;

    }



    private String getMonthStr(String month){

        Calendar cal= Calendar.getInstance();

        SimpleDateFormat month_date = new SimpleDateFormat("MMM");
        int monthnum= Integer.parseInt(month);
        cal.set(Calendar.MONTH,monthnum);
        String month_name = month_date.format(cal.getTime());
        return month_name;

    }

    public long getTimemilli() {
        return timemilli;
    }

    public void setTimemilli(long timemilli) {
        this.timemilli = timemilli;
    }

    public int getPos() {
        return pos;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String date) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setMonth(String month) {
        this.month = month;
    }
    public String getMonth() {
        return month;
    }


    public String getYear() {
        return year;
    }


    public void setYear(String year) {
        this.year = year;
    }


}

