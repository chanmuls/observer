package net.devotopia.observer.ex01;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Reactive Streams - Operators
 * Publisher -> Data -> Subscriber
 * Publisher -> Data1 -> Operators1 -> Data2 -> Operators2 -> data3 -> Subscriber
 *
 * [data] -------> data가 위에서 아래로 흐르는것을 down stream
 * pub -> Data1 -> mapPub -> data2 -> logSub
 *              <- subscribe(logsub())
 *              -> onSubscribe(s)
 *              -> onNext
 *              -> onNext
 *              -> onNext
 *              -> onComplete
 * <------- [data] data가 아래에서 위로 흐르는것을 up stream
 *
 * 1. map (d1 -> f -> d2)
 */
public class PubSub4 {
    private static final Logger logger = LoggerFactory.getLogger( PubSub4.class.getName() );

    public static void main(String[] args) {
        Iterable<Integer> iter = Stream.iterate(1, a -> a+1).limit(10)
                .collect(Collectors.toList());

        Publisher<Integer> pub = iterPub(iter);

        // Publisher<Integer> mapPub = mapPub(pub, (Function<Integer, Integer>) s -> s * 10);
        Publisher<Integer> mapPub = mapPub(pub, s -> s * 10);

        mapPub.subscribe(logSub());
    }

    private static Publisher<Integer> mapPub(Publisher<Integer> pub, Function<Integer, Integer> f) {
        return new Publisher<Integer>() {
            @Override
            public void subscribe(Subscriber<? super Integer> subscriber) {
                pub.subscribe(subscriber);
            }
        };
    }

    private static Subscriber<Integer> logSub() {
        return new Subscriber<Integer>() {
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
    }

    private static Publisher<Integer> iterPub(Iterable<Integer> iter) {
        return sub -> sub.onSubscribe(new Subscription() {
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
}
