package com.bawp.todoister.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by junyi on 1/11/23
 */
public class Utils {
    public static String formatDate(Date date) {
        SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
        simpleDateFormat.applyPattern("EEE, MMM d");
        return simpleDateFormat.format(date);
    }
}
