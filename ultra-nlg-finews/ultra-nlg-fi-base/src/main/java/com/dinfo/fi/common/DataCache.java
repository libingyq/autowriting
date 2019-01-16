package com.dinfo.fi.common;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Interner;
import com.google.common.collect.Interners;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * <p>Date:2018/3/7</p>
 * <p>Module:</p>
 * <p>Description: 模型缓存类</p>
 * <p>Remark: </p>
 *
 * @author wuxiangbo
 * @version 1.0
 *          <p>------------------------------------------------------------</p>
 *          <p> Change history</p>
 *          <p> Serial number: date:modified person: modification reason:</p>
 */

public class DataCache implements Serializable{

    private Logger log = LoggerFactory.getLogger(DataCache.class);

    private static final Interner<String> pool = Interners.newWeakInterner();

    private Cache<String, Object> cache ;

    private static DataCache instance;
    //默认非本地copy模式（先hdfs下载到本地再load）
    public static Boolean isLocal = false;


    private DataCache(Integer initialCapacity,
                      Integer maximumSize,
                      Integer concurrencyLevel,
                      Integer expireAfterAccess,
                      TimeUnit timeUnit) {
        cache = CacheBuilder.newBuilder()
                //设置cache的初始大小
                .initialCapacity(initialCapacity)
                //设置最大容量
                .maximumSize(maximumSize)
                //设置并发数
                .concurrencyLevel(concurrencyLevel)
                //设置cache中的数据在写入之后的存活时间
                .expireAfterAccess(expireAfterAccess, timeUnit)
                //构建cache实例
                .build();
    }

    public static DataCache getInstance(Integer initialCapacity,
                                              Integer maximumSize,
                                              Integer concurrencyLevel,
                                              Integer expireAfterAccess,
                                              TimeUnit timeUnit){
        if(instance == null){
            synchronized (DataCache.class){
                if(instance == null){
                    instance = new DataCache(initialCapacity,
                            maximumSize,concurrencyLevel,expireAfterAccess,timeUnit);
                }
            }

        }
        return instance ;
    }



    public static DataCache getInstance(){
        return getInstance(10,1000,5,1,TimeUnit.HOURS);
    }




    public void removeByPreKey(String k){
        for(String key : cache.asMap().keySet()){
            if(key.startsWith(k)){
                cache.invalidate(key);
            }
        }
    }

    public void removeCache(String k){
        cache.invalidate(k);
    }


    public <T> T get(String key){
        return (T) cache.getIfPresent(key);
    }

    public <T> void set(String key,T value){
        cache.put(key,value);
    }
}
