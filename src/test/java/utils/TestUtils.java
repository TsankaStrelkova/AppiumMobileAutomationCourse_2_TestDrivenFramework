package utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestUtils {
    public static final long WAIT = 10;

    public static String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-HH-mm-ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String getCurrentDateAndTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat timeFormat = new SimpleDateFormat("HH-mm-ss");
        Date date = new Date();
        String currentDate = dateFormat.format(date);
        String currentTime = timeFormat.format(date);
        return currentDate + "_" + currentTime;
    }
}
