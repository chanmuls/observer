package net.devotopia.observer.ex01;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class IntervalEx1 {
    private static final Logger logger = LoggerFactory.getLogger(IntervalEx1.class.getName());

    public static void main(String[] args) {
        Publisher<Integer> pub = sub -> sub.onSubscribe(new Subscription() {
            int no = 0;
            boolean cancelled = false;

            @Override
            public void request(long l) {
                logger.debug("request()");

                ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

                executorService.scheduleAtFixedRate(() -> {
                    if (cancelled) {
                        executorService.shutdown();
                        return;
                    }

                    sub.onNext(this.no++);
                }, 0, 300, TimeUnit.MILLISECONDS);
            }

            @Override
            public void cancel() {
                this.cancelled = true;
            }
        });

        Publisher<Integer> takePub = sub -> {
            pub.subscribe(new Subscriber<Integer>() {
                int count = 0;
                Subscription subc;

                @Override
                public void onSubscribe(Subscription s) {
                    sub.onSubscribe(s);
                    this.subc = s;
                }

                @Override
                public void onNext(Integer i) {
                    sub.onNext(i);

                    if (count++ >= 5) {
                        this.subc.cancel();
                    }
                }

                @Override
                public void onError(Throwable t) {
                    sub.onError(t);
                }

                @Override
                public void onComplete() {
                    sub.onComplete();
                }
            });
        };

        takePub.subscribe(new Subscriber<Integer>() {
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
