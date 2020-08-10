package com.fangxiang;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static void main(String[] args) {
//        thread();
//        runnable();
//        threadFactory();
//        executor();
//        callable();
        runSynchronized1Demo();
//        runSynchronized2Demo();
//        runSynchronized3Demo();
//        runReadWriteLockDemo();
    }


    /**
     * 适用Thread类来定义工作
     */
    static void thread() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                System.out.println("Thread started!!");
            }
        };
        thread.start();
    }

    /**
     * 使用Runnable类来定义工作
     */
    private static void runnable() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread with runnable started!!");
            }
        };
        Thread thread = new Thread(runnable); //优点：runnable对象可复用，灵活
        thread.start();
    }

    /**
     * 线程工厂类，系统接口自己实现具体逻辑
     */
    private static void threadFactory() {
        ThreadFactory factory = new ThreadFactory() {
            private AtomicInteger atomicInteger = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "Thread-" + atomicInteger.getAndIncrement());
            }
        };

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " started!");
            }
        };

        Thread thread = factory.newThread(runnable);
        thread.start();
        Thread thread1 = factory.newThread(runnable);
        thread1.start();
    }

    private static void executor() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " Thread with executor started!!");
            }
        };

        Executor executor = Executors.newCachedThreadPool();

        executor.execute(runnable);
        executor.execute(runnable);
        executor.execute(runnable);
    }

    private static void callable() {
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(1500); //pool-1-thread-1
                System.out.println(Thread.currentThread().getName());
                return "Done";
            }
        };

        ExecutorService executor = Executors.newCachedThreadPool();
        Future<String> future = executor.submit(callable);

        while (true) {
            System.out.println("飞行");//...; 做自己的一些工作

            if (future.isDone()) {
                try {
                    String result = future.get(); //阻塞操作，主线程会等待直到出结果
                    System.out.println("result: " + result);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
                return; //取到值，退出死循环;避免主线程"卡死"
            }

        }
    }

    private static void runSynchronized1Demo() {
        new Synchronized1Demo().runTest();
    }

    private static void runSynchronized2Demo() {
        new Synchronized2Demo().runTest();
    }

    private static void runSynchronized3Demo() {

    }


    private static void runReadWriteLockDemo() {

    }
}
