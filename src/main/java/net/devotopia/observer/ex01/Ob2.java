package net.devotopia.observer.ex01;

import java.util.Iterator;

public class Ob2 {
    // Duality(상대성)
    //Iterable            --> Observer patten
    //Pull                    Push
    //필요한 데이터를 끌어온다.     원하는 데이터를 보내준다.

    public static void main(String[] args) {

//        lambda function
//        Iterable<Integer> iterable = new Iterable<Integer>() {
//            @Override
//            public Iterator<Integer> iterator() {
//
//
//                return null;
//            }
//        };

//        Iterable<Integer> iterable =
//            Iterator<Integer> iterator() {
//
//
//                return null;
//            }
//        ;

//        Iterable<Integer> iterable =
//                Iterator<Integer> () {
//
//
//            return null;
//        }
//        ;

//        Iterable<Integer> iterable = () -> {
//            return null;
//        };

        Iterable<Integer> iterable = () ->
                new Iterator<Integer>() {
                    int i = 0;
                    final static int MAX = 10;

                    @Override
                    public boolean hasNext() {
                        return i < MAX;
                    }

                    @Override
                    public Integer next() {
                        return ++i;
                    }
                };

        for (Integer i:
                iterable) {
            System.out.println(i);
        }

        for (Iterator<Integer> iterator = iterable.iterator(); iterator.hasNext();) {
            System.out.println(iterator.next()); // Pull에 해당
        }
    }
}
