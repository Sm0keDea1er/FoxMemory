package com.smartfox.foxmemory;

import java.util.Calendar;

/**
 * Created by SmartFox on 16.03.2018.
 */

public class Utils {

    public static String getSeconds(long milliseconds){

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);

        return Integer.toString(calendar.get(Calendar.SECOND));
    }
}
