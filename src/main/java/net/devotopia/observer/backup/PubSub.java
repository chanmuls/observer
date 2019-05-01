package net.devotopia.observer.backup;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.Arrays;
import java.util.Iterator;

public class PubSub {
    public static void main(String[] args) {
        // Publisher <-- Observable
        // Subscriber <-- Observer

        Iterable<Integer> iter = Arrays.asList(1, 2, 3, 4, 5);

        Publisher p = new Publisher() {
            @Override
            public void subscribe(Subscriber subscriber) {
                Iterator<Integer> it = iter.iterator();

                subscriber.onSubscribe(new Subscription() {
                    @Override
                    public void request(long l) {
                        try {
//                        while (true) {
                            while (l-- > 0) {
                                if (it.hasNext()) {
                                    subscriber.onNext(it.next());
                                } else {
                                    subscriber.onComplete();
                                    break;
                                }
                            }
                        } catch (RuntimeException e) {
                            subscriber.onError(e);
                        }
                    }

                    @Override
                    public void cancel() {

                    }
                });
            }
        };

        Subscriber<Integer> s = new Subscriber<Integer>() {
            private Subscription subscription;

            @Override
            public void onSubscribe(Subscription subscription) {
                // 받는 데이터 설정 처리

                System.out.println("onSubscribe");
//                subscription.request(Long.MAX_VALUE);
                this.subscription = subscription;
                this.subscription.request(1);
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("onNext " + integer);
                this.subscription.request(1);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("onError");
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        };

        p.subscribe(s);
    }
}
