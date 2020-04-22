package com.tajkun.ad.delivery.utils;

import com.tajkun.ad.common.exception.AdException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.time.DateUtils;

import java.text.ParseException;
import java.util.Date;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-04-22 21:04
 **/
public class CommonUtils {

    // 字符串转换满足条件
    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy/MM/dd","yyyy.MM.dd"
    };

    public static String md5(String value) {
        return DigestUtils.md5Hex(value).toUpperCase();
    }

    public static Date parseStringToDate(String value) throws AdException {
        try {
            return DateUtils.parseDate(value, parsePatterns);
        } catch (ParseException e) {
            throw new AdException(e.getMessage());
        }
    }
}