package com.example.threadandroid;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 自己写一个精简版的HandlerThread
 */
public class CustomThread extends Thread {
    private Looper looper = new Looper();

    @Override
    public void run() {
        looper.loop();
    }

    class Looper {
        private Runnable task;
        private AtomicBoolean quit = new AtomicBoolean();

        synchronized void setTask(Runnable task) {
            this.task = task;
        }

        void quit() {
            quit.set(true);
        }

        void loop() {
            while (!quit.get()) {
                synchronized (this) { //注意添加线程同步

                    if (task != null) {
                        task.run();
                        task = null;
                    }
                }
            }
        }
    }
}
