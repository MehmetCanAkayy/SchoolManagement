package com.alamkanak.weekview;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by jesse on 6/02/2016.
 */
public class WeekViewUtil {


    /////////////////////////////////////////////////////////////////
    //
    //      Helper methods.
    //
    /////////////////////////////////////////////////////////////////

    /**
     * Checks if two times are on the same day.
     * @param dayOne The first day.
     * @param dayTwo The second day.
     * @return Whether the times are on the same day.
     */
    public static boolean isSameDay(Calendar dayOne, Calendar dayTwo) {
        return dayOne.get(Calendar.YEAR) == dayTwo.get(Calendar.YEAR) && dayOne.get(Calendar.DAY_OF_YEAR) == dayTwo.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * Returns a calendar instance at the start of this day
     * @return the calendar instance
     */
    public static Calendar today(){

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, cal.getActualMinimum(Calendar.DAY_OF_WEEK));
        Date now = new Date();
        cal.setTime(now);
        int week = cal.get(Calendar.DAY_OF_WEEK);
        cal.setTime(new Date(now.getTime() - 24 * 60 * 60 * 1000 * (week -2)));
        return cal;
    }
}
