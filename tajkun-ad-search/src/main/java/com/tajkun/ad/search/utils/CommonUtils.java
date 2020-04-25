package com.tajkun.ad.search.utils;

import java.util.Map;
import java.util.function.Supplier;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-04-24 18:20
 **/
public class CommonUtils {

    public static <K, V> V getorCreate(K key, Map<K, V> map, Supplier<V> factory) {
        return map.computeIfAbsent(key, k-> factory.get());
    }

    public static String stringConcat(String... strs){
        StringBuilder result = new StringBuilder();
        for (String str : strs) {
            result.append(str);
            result.append("-");
        }
        result.deleteCharAt(result.length() - 1);
        return  result.toString();
    }
}