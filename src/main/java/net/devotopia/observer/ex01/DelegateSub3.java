package net.devotopia.observer.ex01;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class DelegateSub3<T, R> implements Subscriber<T> {
    private final Subscriber sub;

    public DelegateSub3(Subscriber<? super R> sub) {
        this.sub = sub;
    }

    @Override
    public void onSubscribe(Subscription s) {
        sub.onSubscribe(s);
    }

    @Override
    public void onNext(T i) {
        sub.onNext(i);
    }

    @Override
    public void onError(Throwable throwable) {
        sub.onError(throwable);
    }

    @Override
    public void onComplete() {
        sub.onComplete();
    }
}
