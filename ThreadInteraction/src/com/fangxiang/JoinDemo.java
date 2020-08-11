package com.fangxiang;

public class JoinDemo implements TestDemo {
    private String sharedString;

    private synchronized void initString() {
            sharedString = "rengwuxian";
    }


    private synchronized void printString() {
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

                try {
                    /**
                     * 相当于简化版的wait()/notify()，而且不需要去唤醒，前面线程任务执行结束，后面逻辑接着往后执行
                     * 会自动释放monitor，所以不需要担心在嵌套方法里拿不到monitor的情况
                     */
                    thread1.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                printString();

                /**
                 * 把自己的时间片暂时让出一小片，让给同优先级的其它线程
                 */
                yield();
            }
        };
        thread2.start();

    }
}
