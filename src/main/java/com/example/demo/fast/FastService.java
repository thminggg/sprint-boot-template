package com.example.demo.fast;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class FastService {
    private final WebClient webClient;

    public FastService(@Qualifier("webClientFast") WebClient webClient) {
        this.webClient = webClient;
    }

    public String getHelloWorld() {
        // Make a GET request to a specific endpoint
        Mono<String> response = webClient.get()
                .uri("/")
                .retrieve()
                .bodyToMono(String.class);

        // Block to get the response synchronously
        return response.block();
    }
}
