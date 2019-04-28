package net.devotopia.observer;

import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Ob {
    // Duality
    // Observer patten
    // Reactive streams 표준 자바 1.9
    // http://www.reactive-streams.org
    // http://reactivex.io

    // 1. Complete ???
    // 2. Error

/*
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        for (Integer i:
             list) {
            System.out.println(i);
        }

        Iterable<Integer> iter = Arrays.asList(1, 2, 3, 4, 5);
        for (Integer i:
                iter) {
            System.out.println(i);
        }

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

        for (Iterable<Integer> it = (Iterable<Integer>) iterable.iterator(); ((Iterator) it).hasNext();) {
            System.out.println(((Iterator) it).next()); // Pull에 해당
        }
    }
    */

    static class IntObserverble extends Observable implements Runnable {
        // dispatcher, publisher

        @Override
        public void run() {
            for (int i = 1; i <= 10; i++) {
                setChanged();
                notifyObservers(i);  // Push 에 해당
                // int i = it.next() // Pull에 해당
            }
        }
    }

    // Data method(void) <--> void method(Data)

    public static void main(String[] args) {
        // Duality(상대성)
        //Iterable            --> Observer patten
        //Pull                    Push
        //필요한 데이터를 끌어온다.     원하는 데이터를 보내준다.

        //Observableble // Source -> Event/Data -> Observer

        // reducer, subscrabe

        Observer ob = new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                System.out.println(Thread.currentThread().getName() +"--"+ arg);
            }
        };

        IntObserverble iob = new IntObserverble();
        iob.addObserver(ob);

        ExecutorService es = Executors.newSingleThreadExecutor();

        es.execute(iob);

        System.out.println(Thread.currentThread().getName() +"-- EXIT");
        es.shutdown();
    }
}
