package net.devotopia.observer.ex01;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

// demon thread test
public class FluxInEx3 {
    private static final Logger logger = LoggerFactory.getLogger(FluxInEx3.class.getName());

    public static void main(String[] args) throws InterruptedException {
        Flux.interval(Duration.ofMillis(500))
                .take(10) // data를 받을 양을 정함
                .subscribe(s -> logger.debug(String.valueOf(s)));

        TimeUnit.SECONDS.sleep(5); // demon thread 일경우에 stackoverflow 답변은 이렇게
    }
}
