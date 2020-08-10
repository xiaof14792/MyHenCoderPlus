package com.fangxiang;

public class Synchronized2Demo implements TestDemo{
    private int value = 0;

    private synchronized void count(){
        value++;
        /*int temp = value + 1;
        value = temp;
        两步操作，不是原子操作;通过同步方法，将两步合成一个原子操作
        同步方法效果：方法里的操作具有原子性，方法里面的变量具有同步性*/
    }

    @Override
    public void runTest() {
        Thread thread1 = new Thread(){
            @Override
            public void run() {
                for (int i = 0; i < 1000_000; i++) {
                    count();
                }

                System.out.println("final value from 1: " + value);
            }
        };
        thread1.start();

        Thread thread2 = new Thread(){
            @Override
            public void run() {
                for (int i = 0; i < 1000_000; i++) {
                    count();
                }

                System.out.println("final value from 2: " + value);
            }
        };
        thread2.start();
    }
}
