package net.devotopia.observer.ex01;

import reactor.core.publisher.Flux;

public class FluxScEx1 {
    public static void main(String[] args) {
        Flux.range(1, 10)
                .subscribe(System.out::println);
    }
}
