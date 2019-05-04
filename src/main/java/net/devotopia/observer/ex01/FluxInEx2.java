package net.devotopia.observer.ex01;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FluxInEx2 {
    private static final Logger logger = LoggerFactory.getLogger(FluxInEx2.class.getName());

    // user thread test
    public static void main(String[] args) throws InterruptedException {
//        Flux.interval(Duration.ofMillis(500))
//                .subscribe(s -> logger.debug(String.valueOf(s)));
//
//        TimeUnit.SECONDS.sleep(5); // stackoverflow 답변은 이렇게


        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            logger.debug("hello");
        });

        logger.debug("exit");
    }
}
