package com.marcoscouto.webclient.controller;

import com.marcoscouto.webclient.data.Joke;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

import java.util.Random;

import static java.time.Duration.ofMillis;
import static reactor.util.retry.Retry.backoff;

@RestController
public class JokeController {

    private final WebClient client;

    public JokeController(WebClient client) {
        this.client = client;
    }

    @GetMapping("/joke")
    public ResponseEntity response() {

        var joke = client.get()
                .uri("/random")
                .retrieve()
                .bodyToMono(Joke.class)
                .retryWhen(backoff(2, ofMillis(backoffRandomValue())))
                .block();

        return ResponseEntity.ok(joke);
    }

    private Integer backoffRandomValue() {
        return new Random().nextInt(600) + 100;
    }

}
