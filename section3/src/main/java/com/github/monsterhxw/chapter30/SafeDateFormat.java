package com.github.monsterhxw.chapter30;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @author Xuewei Huang
 * @since 2022-09-20
 */
public class SafeDateFormat {
    
    private static final ThreadLocal<DateFormat> DATE_FORMAT_TL = ThreadLocal.withInitial(
            () -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    
    public static DateFormat get() {
        return DATE_FORMAT_TL.get();
    }
}
