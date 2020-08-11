package com.fangxiang;

public class Main {

    public static void main(String[] args) {
//        startThreadInteractionDemo();
        startWaitDemo();
        startJoinDemo();
    }

    static void startThreadInteractionDemo(){
        new ThreadInteractionDemo().runTest();
    }
    static void startWaitDemo(){
        new WaitDemo().runTest();
    }

    static void startJoinDemo(){
        new JoinDemo().runTest();
    }
}
