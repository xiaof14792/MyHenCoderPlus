package com.fangxiang;

public class ThreadInteractionDemo implements TestDemo {
    @Override
    public void runTest() {
        Thread thread = new Thread(){
            @Override
            public void run() {
                for (int i = 0; i < 1_000_000; i++) {
                    if (isInterrupted()){
                        /**
                         * 需要配合interrupt操作，才能中断线程，一般放在"大的"耗时操作之前，判断要不要自己结束线程
                         */
                        return;
                    }
                    System.out.println("number: " + i);
                }

                /*try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    //擦屁股
                    return; //这时候可以立即打断，因为不用担心改资源出问题，所以抛出异常来通知线程中断线程；这里只有一次机会中断线程，因为并不会将线程中断状态置为true
                }*/
            }
        };

        thread.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.interrupt();
    }
}
