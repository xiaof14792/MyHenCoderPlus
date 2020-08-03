package com.example.generics;

import java.util.ArrayList;
import java.util.Collections;

class ReversibleArrayList<E> extends ArrayList<E> {
    public void reverse(){
        Collections.reverse(this);
    }
}
