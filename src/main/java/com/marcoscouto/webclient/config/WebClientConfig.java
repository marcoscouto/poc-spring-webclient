package com.marcoscouto.webclient.config;

import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import static io.netty.channel.ChannelOption.CONNECT_TIMEOUT_MILLIS;
import static java.time.Duration.ofMillis;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
public class WebClientConfig {

    private final Integer TIME_OUT = 3000;

    @Bean
    public WebClient client() {
        return WebClient.builder()
                .baseUrl("https://api.chucknorris.io/jokes")
                .defaultHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .clientConnector(httpClient())
                .build();
    }

    private ReactorClientHttpConnector httpClient() {
        var client = HttpClient.create()
                .option(CONNECT_TIMEOUT_MILLIS, TIME_OUT)
                .responseTimeout(ofMillis(TIME_OUT))
                .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(TIME_OUT, MILLISECONDS))
                                .addHandlerLast(new WriteTimeoutHandler(TIME_OUT, MILLISECONDS))
                );

        return new ReactorClientHttpConnector(client);
    }

}
