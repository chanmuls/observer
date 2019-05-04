package net.devotopia.observer.ex01;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SchedulerEx3 {
    private static final Logger logger = LoggerFactory.getLogger(SchedulerEx3.class.getName());

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


        // Scheduler는 크게 두가지 타입으로 되어 있다.
        // 1. SubscribeOn: 하나의 스케줄러를 통해 subscribe 부터 모두 실행됨
        //   * Publisher가 느리지만 (e.g Blocking IO) Consumer 처리 속도가 빠른 시나리오에 사용된다.
        // Publisher<Integer> subOnPub = sub -> {
        //     ExecutorService es = Executors.newSingleThreadExecutor();
        //     es.execute(() -> pub.subscribe(sub));
        // };

        // 2. PublishOn: 하나의 스케줄러를 통해 subscriber 등록 이후부터 실행됨
        //   * Publisher 처리 속도가 빠르고 Consumer 처리 속도가 느리거나 알수없는 시나리오에 사용된다.
        Publisher<Integer> pubOnPub = sub -> {
            pub.subscribe(new Subscriber<Integer>() {
                ExecutorService es = Executors.newSingleThreadExecutor();

                @Override
                public void onSubscribe(Subscription s) {
                    sub.onSubscribe(s);
                }

                @Override
                public void onNext(Integer integer) {
                    es.execute(() -> sub.onNext(integer));
                }

                @Override
                public void onError(Throwable throwable) {
                    es.execute(() -> sub.onError(throwable));
                    es.shutdown();
                }

                @Override
                public void onComplete() {
                    es.execute(() -> sub.onComplete());
                    es.shutdown();
                }
            });
        };

        pubOnPub.subscribe(new Subscriber<Integer>() {
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

        logger.debug("Exit");
    }
}
