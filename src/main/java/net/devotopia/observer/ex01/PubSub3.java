package net.devotopia.observer.ex01;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Collectors;
import java.util.stream.Stream;


public class PubSub3 {
    private static final Logger logger = LoggerFactory.getLogger( PubSub3.class.getName() );

    public static void main(String[] args) {
        Publisher<Integer> p = new Publisher<Integer>() {
            Iterable<Integer> iter = Stream.iterate(1, a -> a+1).limit(10)
                    .collect(Collectors.toList());

            @Override
            public void subscribe(Subscriber<? super Integer> sub) {
                sub.onSubscribe(new Subscription() {
                    @Override
                    public void request(long l) {
                        try {
                            iter.forEach(s -> sub.onNext(s));
                            sub.onComplete();
                        } catch (Throwable t) {
                            sub.onError(t);
                        }
                    }

                    @Override
                    public void cancel() {

                    }
                });
            }
        };

        Subscriber<Integer> sub = new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription subscription) {
                logger.debug("onComplete");
                subscription.request(Long.MAX_VALUE);
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
        };

        p.subscribe(sub);
    }
}
