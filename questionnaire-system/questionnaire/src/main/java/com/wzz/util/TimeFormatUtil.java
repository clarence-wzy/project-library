package com.wzz.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>Title: TimeFormatUtil</p>
 * <p>Description: </p>
 *
 * @author lizihao
 * @version 1.0.0
 * @date 2019/9/4 14:56
 */
public class TimeFormatUtil {

    public static String stampToTime(Date time){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(time);
    }

}
