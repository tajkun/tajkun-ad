package com.tajkun.ad.search.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-04-24 18:20
 **/
@Slf4j
public class CommonUtils {

    public static <K, V> V getorCreate(K key, Map<K, V> map, Supplier<V> factory) {
        return map.computeIfAbsent(key, k-> factory.get());
    }

    // 拼接字符串
    public static String stringConcat(String... strs){
        StringBuilder result = new StringBuilder();
        for (String str : strs) {
            result.append(str);
            result.append("-");
        }
        result.deleteCharAt(result.length() - 1);
        return  result.toString();
    }

    // Thu Jan 01 08:00:00 CST 1970, 解析binlog日志数据中的date字符串为java对象
    public static Date parseStringDate(String dataString) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
            return DateUtils.addHours(dateFormat.parse(dataString), -8);
        } catch (ParseException e) {
            log.error("parseStringDate error: {}", dataString);
            return null;
        }
    }

}