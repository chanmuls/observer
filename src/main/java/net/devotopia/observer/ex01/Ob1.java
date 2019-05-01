package net.devotopia.observer.ex01;

import java.util.Arrays;
import java.util.List;

public class Ob1 {

    // Duality
    // Observer patten
    // Reactive streams 표준 자바 1.9
    public static void main(String[] args) {

        // list type은 iterable 타입의 sub type 이다
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        for (Integer i:
                list) { // for - each
            System.out.println(i);
        }

        Iterable<Integer> iter = Arrays.asList(1, 2, 3, 4, 5);
        for (Integer i:
                iter) { // for - each
            System.out.println(i);
        }
    }
}
