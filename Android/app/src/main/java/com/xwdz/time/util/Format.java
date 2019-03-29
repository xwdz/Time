package com.xwdz.time.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author xingwei.huang (xwdz9989@gamil.com)
 * @since 2019/3/27
 */
public class Format {


    public static String format(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date curDate = new Date(time);
        return formatter.format(curDate);
    }
}
