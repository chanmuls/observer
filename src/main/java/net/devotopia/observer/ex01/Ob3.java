package net.devotopia.observer.ex01;

import java.util.Observable;
import java.util.Observer;

public class Ob3 {
    // Observable
    // Source -> Event/Data -> Observer
    // source에 observable을 만들어서 observer에 등록해준다.
    // source는 자신에게서 발생되는 event/data를 observer에게 던져준다.(notify한다.)
    // observer의 특징은 여러개가 될 수 있다는 것이다.
    // 갱오프포? 디자인 패턴?

    static class IntObserverble extends Observable implements Runnable {
        // GoF: subject
        // Reactive streams: publisher
        // reactive extension: Observerble
        // java: Observerble
        // dispatcher

        @Override
        public void run() {
            for (int i = 1; i <= 10; i++) {
                setChanged(); // 이전과 다른 정보의 변화에 대한 method 호출
                notifyObservers(i);  // Push 에 해당
                // int i = it.next() // Pull에 해당
            }
        }
    }

    // Data method(void) <--> void method(Data)
    // category 이론
    public static void main(String[] args) {
        // GoF: observer
        // Reactive streams: subscrabe
        // reactive extension: subscrabe, observer
        // java: observer
        // reducer

        Observer ob = new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                System.out.println(Thread.currentThread().getName() +"--"+ arg);
            }
        };

        Ob3.IntObserverble iob = new Ob3.IntObserverble(); // <-- 여기서 발생된 event를 observer에게 던짐
        iob.addObserver(ob); // <-- observer 등록
//        iob.addObserver(ob1); // 여러개 등록 가능
//        iob.addObserver(ob2);
//        iob.addObserver(ob3);

        iob.run();
    }
}
