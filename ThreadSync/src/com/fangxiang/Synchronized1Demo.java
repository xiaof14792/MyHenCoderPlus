package com.fangxiang;

import java.util.concurrent.atomic.AtomicBoolean;

public class Synchronized1Demo implements TestDemo {
//    private volatile boolean running = true; //打开"同步性"，数据在不同线程之间是同步的。哪些变量需要打开同步性需要开发者自己来判断
    private AtomicBoolean running = new AtomicBoolean(true);

    private void stop(){
        running.set(false);
    }

    @Override
    public void runTest() {
        Thread thread = new Thread(){
            @Override
            public void run() {
                while (running.get()){
                    //TODO
                }
            }
        };
        thread.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stop();
    }
}
