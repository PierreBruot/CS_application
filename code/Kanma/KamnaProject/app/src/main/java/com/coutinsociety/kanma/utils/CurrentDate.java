package com.coutinsociety.kanma.utils;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class CurrentDate {

//Convert Universal date and local date
    public static Date localToUtc(Date localDate) {
        return new Date(localDate.getTime()-TimeZone.getDefault().getOffset(localDate.getTime()));
    }
    public static Date utcToLocal(Date utcDate) {
        return new Date(utcDate.getTime()+TimeZone.getDefault().getOffset(utcDate.getTime()));
    }



    public static Date getAllDate(){
        return Calendar.getInstance().getTime();
    }
   /* public static int getCurrentMonth(){
        return Calendar.getInstance().get(Calendar.MONTH);
    }

    public static int getCurrentDayOfMonth(){
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

    }*/
   //for test
   public static Date getDate(int year, int month, int day) {
       Calendar cal = Calendar.getInstance();
       cal.set(Calendar.YEAR, year);
       cal.set(Calendar.MONTH, month);
       cal.set(Calendar.DAY_OF_MONTH, day);
       cal.set(Calendar.HOUR_OF_DAY, 0);
       cal.set(Calendar.MINUTE, 0);
       cal.set(Calendar.SECOND, 0);
       cal.set(Calendar.MILLISECOND, 0);
       return cal.getTime();
   }

    //For event
    public static boolean isNextMonth(Date date){
        long diff=date.getTime()
                -getAllDate().getTime();
        return ((int) (diff / (24 * 60 * 60 * 1000)))<=31;
    }
    public static boolean isNext2Week(Date date){
        long diff=date.getTime()
                -getAllDate().getTime();
        return ((int) (diff / (24 * 60 * 60 * 1000)))<=14;
    }
    public static boolean isNextWeek(Date date){
        long diff=date.getTime()
                -getAllDate().getTime();
        return ((int) (diff / (24 * 60 * 60 * 1000)))<=7;
    }
    public static boolean isToday(Date date){
       long diff=date.getTime()
               -getAllDate().getTime();
        return ((int) (diff / (24 * 60 * 60 * 1000)))==0;
    }

    //For message

}
