package com.jpmorgan.mds.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;

@SpringBootApplication
public class Client {

    @Bean
    WebClient createWebClient() {
        return WebClient.create("http://localhost:8080");
    }

    @Bean
    CommandLineRunner logger(WebClient webclient) {
        return args -> {
            webclient.get().uri("/StreamOfEvents")
                    .accept(MediaType.TEXT_EVENT_STREAM)
                    .exchange().flatMapMany(clientRes -> clientRes
                    .bodyToFlux(Event.class))
                    .subscribe(System.out::println);
        };
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(Client.class)
                .properties(Collections.singletonMap("server.port",
                        "8081")).run(args);
    }

}