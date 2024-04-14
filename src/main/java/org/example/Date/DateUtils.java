package org.example.Date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    private static final String DATE_FORMAT = "MMM d, yyyy"; // Define the date format

    public static Date parseDate(String dateString) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        return formatter.parse(dateString);
    }
}
