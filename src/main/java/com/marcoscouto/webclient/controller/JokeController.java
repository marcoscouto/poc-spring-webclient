package com.marcoscouto.webclient.controller;

import com.marcoscouto.webclient.data.Joke;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
public class JokeController {

    private final WebClient client;

    public JokeController(WebClient client) {
        this.client = client;
    }

    @GetMapping("/joke")
    public ResponseEntity response() {
        var joke = client.get().uri("/random").retrieve().bodyToMono(Joke.class).block();
        return ResponseEntity.ok(joke);
    }

}
