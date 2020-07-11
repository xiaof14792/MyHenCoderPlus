package com.example.app.entity;

import android.widget.Toast;

import com.example.core.BaseApplication;
import com.example.core.utils.Utils;

public class A {
    public static void main(String[] args) {
        BaseApplication.Companion.currentApplication();

        BaseApplication.currentApplication();

        BaseApplication.currentApplication();


        Utils.INSTANCE.toast("123");
        Utils.INSTANCE.toast("123", Toast.LENGTH_SHORT);

    }
}
