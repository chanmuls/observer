package net.devotopia.observer.ex01;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class FluxScEx3 {
    private static final Logger logger = LoggerFactory.getLogger(FluxScEx3.class.getName());

    public static void main(String[] args) {
        Flux.range(1, 10)
                .publishOn(Schedulers.newSingle("pub"))
                .log()
                .subscribeOn(Schedulers.newSingle("sub"))
                .subscribe(s -> logger.debug(String.valueOf(s)));

        logger.debug("exit");
    }
}
