package com.dinfo.fi.func;
 
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

interface RequestHandler<T> {

    public void handle(T result);
}
 
public class ConcurrentTestUtil {
 
    /**
     * 多线程并发执行某项任务
     *
     * @param concurrentThreads    并发线程数，可以用来模拟并发访问用户数
     * @param times                总共执行多少次
     * @param task                 任务
     * @param requestHandler        结果处理器
     * @param executeTimeoutMillis 执行任务总超时
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public static <T> void concurrentTest(long concurrentThreads, int times, final Callable<T> task,
                                          RequestHandler<T> requestHandler, long executeTimeoutMillis)
            throws InterruptedException, ExecutionException {
 
        ExecutorService executor = Executors.newFixedThreadPool((int) concurrentThreads);
        List<Future<T>> results = new ArrayList<Future<T>>(times);
 
        long startTimeMillis = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            results.add(executor.submit(task));
        }
        executor.shutdown();
 
        boolean executeCompleteWithinTimeout = executor.awaitTermination(executeTimeoutMillis,TimeUnit.MILLISECONDS);
        if (!executeCompleteWithinTimeout) {
            System.out.println("Execute tasks out of timeout [" + executeTimeoutMillis + "ms]");
 
            /*
             * 取消所有任务
             */
            for (Future<T> r : results) {
                r.cancel(true);
            }
        } else {
            long totalCostTimeMillis = System.currentTimeMillis() - startTimeMillis;
 
            // 线程池此时肯定已关闭，处理任务结果
            for (Future<T> r : results) {
                if (requestHandler != null) {
                    requestHandler.handle(r.get());
                }
            }
 
            System.out.println("concurrent threads: " + concurrentThreads + ", times: "   + times);
            System.out.println("total cost time(ms): " + totalCostTimeMillis  + "ms, avg time(ms): " + ((double) totalCostTimeMillis / times));
            System.out.println("tps: " + (double) (times * 1000) / totalCostTimeMillis);
        }
    }
 
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ConcurrentTestUtil.concurrentTest(1000, 3000,
                new Callable<String>() {
                    @Override
                    public String call() throws Exception {


                        return "ok";
                    }
                },
                new RequestHandler<String>() {
                    @Override
                    public void handle(String result) {
                        System.out.println("result: " + result);
                    }
                }, 6000);
    }
}
