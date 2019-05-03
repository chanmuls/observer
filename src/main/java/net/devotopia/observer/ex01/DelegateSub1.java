package net.devotopia.observer.ex01;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class DelegateSub1 implements Subscriber<Integer> {
    private final Subscriber sub;

    public DelegateSub1(Subscriber sub) {
        this.sub = sub;
    }

    @Override
    public void onSubscribe(Subscription s) {
        sub.onSubscribe(s);
    }

    @Override
    public void onNext(Integer integer) {
        sub.onNext(integer);
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
