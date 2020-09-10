package com.jpmorgan.mds.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.Date;
import java.util.stream.Stream;

import static java.util.stream.Stream.*;

@SpringBootApplication
@RestController
public class Endpoint {

    @GetMapping("/StreamOfEvents/{id}")
    Mono eventById(@PathVariable long id) {
        return Mono.just(new Event(id, new Date()));
    }

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE,
            value = "/StreamOfEvents")
    Flux events() {
        final Stream<Event> generate = generate(() -> new Event(System.currentTimeMillis(),
                new Date()));
        Flux<Event> eventFlux = Flux.fromStream(generate);
        Flux<Long> durationFlux = Flux.interval(Duration.ofSeconds(1));
        Flux<Tuple2<Event,Long>> zip = Flux.zip(eventFlux, durationFlux);
        final Flux<Object> map = zip.map((t1) -> t1.getT1());
        return map;
    }

    public static void main(String[] args) {
        SpringApplication.run(Endpoint.class, args);
    }
}