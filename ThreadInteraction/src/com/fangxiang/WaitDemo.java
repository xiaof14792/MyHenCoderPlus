package com.fangxiang;

public class WaitDemo implements TestDemo {
    private String sharedString;
    private Object monitor = new Object();

    private void initString() {
        synchronized (monitor){
            sharedString = "rengwuxian";
            monitor.notifyAll();
        }
    }

    /**
     * wait()、notify()/notifyAll()是需要成对使用的，需要使用monitor 去调用的，还有2逻辑：等待和唤醒都是围绕monitor来的
     * 故这两个方法需要在同步方法里面去调用
     */
    private void printString() {
        synchronized (monitor){
            while (sharedString == null) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("String: " + sharedString);
    }

    @Override
    public void runTest() {
        Thread thread1 = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                initString();
            }
        };
        thread1.start();

        Thread thread2 = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                printString();
            }
        };
        thread2.start();

        try {
            /**
             * 把两个并行的线程变成一个线性的先后次序关系
             */
            thread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("haha");
    }
}
