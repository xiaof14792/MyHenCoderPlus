package com.example.generics;

interface Eater<T extends Food> {
    void eat(T food);
}
