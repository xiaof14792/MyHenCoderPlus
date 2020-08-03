package com.example.generics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppleArrayList appleArrayList = new AppleArrayList();
        appleArrayList.add(new Apple());

//        ArrayList<? extends View> fruitList = new ArrayList<TextView>();

        ArrayList<? super AppCompatTextView> fruitList = new ArrayList<TextView>();

        List<String> list = new ArrayList<String>(){};
    }

}