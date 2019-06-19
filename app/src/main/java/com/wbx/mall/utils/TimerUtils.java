package com.wbx.mall.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class TimerUtils {
    public static String timeParse(long duration) {
        String time = "" ;

        long minute = duration / 60000 ;
        long seconds = duration % 60000 ;

//        long second = Math.round((float)seconds/1000) ;

        if( minute < 10 ){
            time += "0" ;
        }
        time += minute;

//        if( second < 10 ){
//            time += "0" ;
//        }
//        time += second ;

        return time ;
    }

}
