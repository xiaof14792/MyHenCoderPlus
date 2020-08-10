package com.fangxiang;

public class SingleMan {
    private static volatile SingleMan instance; //volatile 关键字必须加，单例类初始化完成后变量才对外标记为非空；否则多线程开发时会出错

    private SingleMan(){

    }

    public static SingleMan getInstance(){
        if (instance == null){ //第一次检查，每次检查都加锁会比较影响性能
            synchronized (SingleMan.class){
                if (instance == null){ //防止多个线程执行到这里，会导致单例类初始化多次
                    instance = new SingleMan();
                }
            }
        }

        return instance;
    }
}
