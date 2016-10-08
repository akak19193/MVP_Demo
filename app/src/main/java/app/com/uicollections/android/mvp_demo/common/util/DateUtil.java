package app.com.uicollections.android.mvp_demo.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


public class DateUtil {
    public DateUtil() {
    }

    private static SimpleDateFormat sdf;

    private static SimpleDateFormat getSimpleDateFormat(String format) {
        sdf = new SimpleDateFormat(format);
        return sdf;
    }

    public static CharSequence formatMills(long duration, String format) {
        getSimpleDateFormat(format);
        Date dt2 = new Date(duration);
        String sDateTime = sdf.format(dt2);
        return sDateTime;
    }

    public static CharSequence formatMills(long duration) {
        return formatMills(duration, "yyyy/MM/dd HH:mm:ss");
    }

    public static String fromApiTime(String apiTime, String targetFormat, String apiFormat) {
        getSimpleDateFormat(apiFormat);
        SimpleDateFormat temp = new SimpleDateFormat(targetFormat, Locale.US);
        temp.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            Date dt = sdf.parse(apiTime);
            return temp.format(dt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
