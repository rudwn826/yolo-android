package kr.co.triggers.yolo.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Converter {

    private static final String DURATION_FORMAT = "%d - %d";
    private static final DateFormat FIESTA_DATE_FORMAT = new SimpleDateFormat("MMM", Locale.getDefault());
    private static final DateFormat BIRTH_DATE_FORMAT = new SimpleDateFormat("yyyy. M. d", Locale.getDefault());

    public static String getMonth(Date date) { return date != null ? FIESTA_DATE_FORMAT.format(date).toUpperCase() : null; }

    public static String getDuration(Date start, Date end) {

        if (start == null || end == null)
            return null;

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(start);
        int dayStart = calendar.get(Calendar.DATE);

        calendar.setTime(end);
        int dayEnd = calendar.get(Calendar.DATE);

        return dayStart != dayEnd ? String.format(DURATION_FORMAT, dayStart, dayEnd) : String.valueOf(dayStart);
    }

    public static String getBirth(Date date) {

        return date != null ? BIRTH_DATE_FORMAT.format(date) : "0000. 0. 0";
    }
}
