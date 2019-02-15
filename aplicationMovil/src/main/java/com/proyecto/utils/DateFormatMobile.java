package com.proyecto.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormatMobile {

    public static String _FORMAT_DATE_WS = "yyyyMMdd";

    public static Date ConvertStringToDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(_FORMAT_DATE_WS);
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(dateString);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return convertedDate;
    }

    public static String ConvertDateToString(Date date) {
        SimpleDateFormat df = new SimpleDateFormat(_FORMAT_DATE_WS, Locale.ENGLISH);
        String result = null;
        try {
            result = df.format(date);
        } catch (Exception e) {
            Log.d("Exception", e.getMessage());
        }
        return result;
    }
}
