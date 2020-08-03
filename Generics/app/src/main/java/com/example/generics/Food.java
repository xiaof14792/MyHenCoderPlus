package com.example.generics;

import java.io.Serializable;

interface Food {
    <T extends Runnable & Serializable> void someMethod (T param);
}
