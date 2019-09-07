package com.xiangyao;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author xianggua
 * @description
 * @date 2019-8-10 19:22
 * @since 1.0
 */
public class LRUCache<K,V> extends LinkedHashMap<K,V> {

    private final int CACHE_SIZE;

    //传递进来最多能缓存多少数据
    public LRUCache(int cacheSize){
        //这块就是设置一个hashmap的初始大小,同时最后一个true指的是让linkedhashmap按照访问顺序来进行排序,
        //最近访问的放在头,最老访问的就在尾
        super((int)Math.ceil(cacheSize/0.75) + 1,0.75f,true);
        CACHE_SIZE = cacheSize;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        //当map中的数据量大于指定的缓存个数时候,就自动删除最老的数据

        return size() > CACHE_SIZE;
    }



}
