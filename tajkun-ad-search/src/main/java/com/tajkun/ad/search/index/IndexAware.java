package com.tajkun.ad.search.index;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-04-24 17:03
 **/
public interface IndexAware<K, V> {

    V get(K key);
    void add(K key, V value);
    void update(K key, V value);
    void delete(K key, V value);
}