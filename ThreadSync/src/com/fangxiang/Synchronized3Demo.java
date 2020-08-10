package com.fangxiang;

public class Synchronized3Demo implements TestDemo{
    private int x = 0;
    private int y = 0;
    private String name = "zhang";

    /**
     * 不同的monitor监视不同的方法，成对的方法被同一个monitor监视，另外一个方法被另一个monitor监视
     */
    private Object monitor1 = new Object(); //对于不同的锁，满足批量管理的同时，又能够分开管理
    private Object monitor2 = new Object(); //对于JVM来说，monitor只是一个"标记"，故选Object最简单的类

    private void count(){
        synchronized (monitor1){ // 保护的是"资源"，同步方法/代码块里的所有资源
            x++;
            y++;
        }
    }

    /**
     * 其它一些问题：死锁、乐观锁/悲观锁
     * @param delta
     */

    private void minus(int delta){
        synchronized (monitor1){
            x -= delta;
            y -= delta;
        }
    }

    private void setName(String name){
        synchronized (monitor2){
            this.name = name;
        }
    }

    @Override
    public void runTest() {

    }
}
