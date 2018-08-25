package com.milaboratory.mir;

import org.junit.Test;

import java.lang.reflect.Array;

public class Temp {
    @Test
    public void test() {
        Integer[] arr = (Integer[]) Array.newInstance(Integer.class, 10);
        for (int i = 0; i < 10; i++) {
            arr[i] = i;
        }

        for (int i = 0; i < 10; i++) {
            System.out.println((Integer)arr[i] + 1);
        }
    }
}
