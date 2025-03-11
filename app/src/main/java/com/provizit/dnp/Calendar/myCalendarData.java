package com.provizit.dnp.Calendar;

import com.provizit.dnp.Conversions;
import com.provizit.dnp.Services.DataManger;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class myCalendarData  {

    private int  currentmonth, currentyear,dayofweek;
    private String stringDayofWeek,startday;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("E");
    private Calendar calendar ;
    private Long timeMilli;
    private static final String[] dayEnglish = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
    private static final String[] dayArabic = {"الأحد","الاثنين","الثلاثاء","الاربعاء","الخمیس","الجمعہ","السبت"};



    // constructor

     public  myCalendarData (int startdate){

         this.calendar = Calendar.getInstance();
         calendar.add(Calendar.YEAR,startdate);
         setThis();

    }
    private void setThis (){



        Calendar day = calendar;
        day.set(Calendar.MILLISECOND, 0);
        day.set(Calendar.SECOND, 0);
        day.set(Calendar.MINUTE, 0);
        day.set(Calendar.HOUR_OF_DAY, 0);

        this.startday = calendar.get(Calendar.DAY_OF_MONTH) + "";

        this.timeMilli = day.getTimeInMillis() / 1000;
        this.currentmonth = calendar.get(Calendar.MONTH);
        this.currentyear=calendar.get(Calendar.YEAR);
        this.dayofweek= calendar.get(Calendar.DAY_OF_WEEK);

    System.out.println("sdjh "+dateFormat.format(calendar.getTime()));
        this.stringDayofWeek = dateFormat.format(calendar.getTime());
        if(DataManger.appLanguage.equals("ar")){
            this.startday = Conversions.convertToArabic(calendar.get(Calendar.DAY_OF_MONTH)+"");
            this.stringDayofWeek = dayArabic[find(dateFormat.format(calendar.getTime()))];
        }


//        this.stringDayofWeek = dateFormat.format(calendar.getTime()).substring(0,1);
//        if(dateFormat.format(calendar.getTime()).substring(1,2).equals("h")){
//         this.stringDayofWeek = dateFormat.format(calendar.getTime()).substring(0,2);
//        }


    }

    public void getNextWeekDay(int nxt){

        calendar.add(Calendar.DATE, nxt);
        setThis();

    }
    public String calendar(){
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

        return formatter.format(calendar.getTime());
    }
    public int find(String target)
    {
        for (int i = 0; i < dayEnglish.length; i++)
        {
            if (dayEnglish[i].equals(target)) {
                return i;
            }
        }

        return -1;
    }
    public String getWeekDay (){
         return this.stringDayofWeek;
    }

    public int getYear(){
        return this.currentyear;
    }
    public int getMonth(){
        return this.currentmonth;
    }
    public String getDay(){
        return this.startday;
    }
    public Long getTimeMilli(){
        return this.timeMilli;
    }


}
