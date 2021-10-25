package com.multiventas.app.utils;

import androidx.room.TypeConverter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Converters {
    @TypeConverter
    public static String fromTimestamp(Long value) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");

        String dateString = value == null ? null : format.format( new Date(value));

        return dateString;
    }

    public static String fromTimestampWithOutHour(Long value) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

        String dateString = value == null ? null : format.format( new Date(value));

        return dateString;
    }

    @TypeConverter
    public static Long fromDatetoTimeStampNotBeforeToday(String value) throws Exception{
        DateFormat df = new SimpleDateFormat("MM/dd/yy");
        Date fecha = df.parse(value);

        Date hoy = new Date();

        if (fecha.before(hoy))
            return null;

        return fecha.getTime();
    }

    @TypeConverter
    public static Long fromDatetoTimeStampNotAfterToday(String value) throws Exception{
        DateFormat df = new SimpleDateFormat("MM/dd/yy");
        Date fecha = df.parse(value);

        Date hoy = new Date();

        if (fecha.after(hoy))
            return null;

        return fecha.getTime();
    }


}
