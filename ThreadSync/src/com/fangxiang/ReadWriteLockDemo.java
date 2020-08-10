package com.fangxiang;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockDemo implements TestDemo{
    private int x = 0;
    private ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    private ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();
    private ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();

    /**
     * 多线程并发时，多个线程不能同时对同一资源"写操作"，不能同时"读"与"写"操作，但是可以同时对同一资源"读"操作。
     * 这就有了把[读锁]和[写锁]分开使用的必要性了
     */

    public void count(){
        writeLock.lock();
        try {
            x++;
        }finally {
            writeLock.unlock();
        }
    }

    public void print(){
        readLock.lock();
        try {
            System.out.println(x + " ");
        }finally {
            readLock.unlock();
        }
    }

    @Override
    public void runTest() {

    }
}
