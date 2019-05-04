package net.devotopia.observer.ex01;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IntervalEx1 {
    private static final Logger logger = LoggerFactory.getLogger(IntervalEx1.class.getName());

    public static void main(String[] args) {
        Publisher<Integer> pub = sub -> sub.onSubscribe(new Subscription() {
            @Override
            public void request(long l) {
                logger.debug("request()");

                sub.onNext(1);
                sub.onNext(2);
                sub.onNext(3);
                sub.onNext(4);
                sub.onNext(5);
                sub.onComplete();
            }

            @Override
            public void cancel() {

            }
        });

        pub.subscribe(new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                logger.debug("onSubscribe");
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(Integer integer) {
                logger.debug("onNext: {}", integer);
            }

            @Override
            public void onError(Throwable throwable) {
                logger.debug("onError: {}", throwable.getMessage());
            }

            @Override
            public void onComplete() {
                logger.debug("onComplete");
            }
        });
    }
}
