package com.milaboratory.mir;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.*;

public class Temp {
    @Test
    public void test() {
        Integer[] arr = (Integer[]) Array.newInstance(Integer.class, 10);
        for (int i = 0; i < 10; i++) {
            arr[i] = i;
        }

        for (int i = 0; i < 10; i++) {
            System.out.println((Integer) arr[i] + 1);
        }
    }

    @Test
    public void test2() {
        Collection<Integer> a = new ArrayList<>(),
                b = new LinkedList<>();

        for (int i = 0; i < 10; i++) {
            a.add(i);
            b.add(i);
        }

        long start = 0, end = 0;

        int suma = 0;
        start = System.nanoTime();
        for(Integer i : a) {
            suma += i;
        }
        end = System.nanoTime();
        System.out.println((end - start) + "ns");

        int sumb = 0;
        start = System.nanoTime();
        for(Integer i : b) {
            sumb += i;
        }
        end = System.nanoTime();
        System.out.println((end - start) + "ns");
    }
}
