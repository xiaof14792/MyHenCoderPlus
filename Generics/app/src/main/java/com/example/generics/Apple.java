package com.example.generics;

import java.util.List;

public class Apple {
    public <E> void merge(E item, List<E> list){
        list.add(item);
    }
}
