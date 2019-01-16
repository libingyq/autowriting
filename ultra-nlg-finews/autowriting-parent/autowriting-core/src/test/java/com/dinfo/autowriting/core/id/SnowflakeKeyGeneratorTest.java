package com.dinfo.autowriting.core.id;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

public class SnowflakeKeyGeneratorTest {


    public static void main(String[] args) throws InterruptedException {
        int nt = 1000, n = 1000;
        CountDownLatch l = new CountDownLatch(1);
        CountDownLatch l2 = new CountDownLatch(nt);
        Map<Long, Long> map = new ConcurrentHashMap<>();
        SnowflakeIdGenerator keyGenerator = new SnowflakeIdGenerator(1);

        long t1 = System.currentTimeMillis();
        
        for (int i = 0; i < nt; i++) {
            new Thread(() -> {
                try {
                    l.await();
                    for (int j = 0; j < n; j++) {
                        long k = keyGenerator.nextId();
                        System.out.println(k);
                        map.put(k, k);
                    }
                    l2.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        l.countDown();
        l2.await();
        long t2 = System.currentTimeMillis();
        System.out.println(map.size());
        System.out.println("duration : " + (t2 - t1));
    }
}