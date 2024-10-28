package com.example.demo.express;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Service
public class ExpressService {

  private final WebClient webClient;

  @Autowired
  public ExpressService(@Qualifier("webClientExpress") WebClient webClient) {
    this.webClient = webClient;
  }

  public String getHelloWorld(Map<String, String> queryParams) {
    // Convert Map to MultiValueMap
    MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
    multiValueMap.setAll(queryParams);

    // Make a GET request to a specific endpoint
    Mono<String> response = webClient.get()
        .uri(uriBuilder -> uriBuilder
            .path("/")
            .queryParams(multiValueMap) // Use MultiValueMap here
            .build())
        .retrieve()
        .bodyToMono(String.class);

    // Block to get the response synchronously
    return response.block();
  }

  public String postData(String jsonBody) {
    Mono<String> response = webClient.post()
        .uri("/")
        .headers(h -> h.setContentType(MediaType.APPLICATION_JSON))
        .bodyValue(jsonBody)
        .retrieve()
        .bodyToMono(String.class);

    // Block to get the response synchronously
    return response.block();
  }

  public Mono<String> putData(String id, String jsonBody) {
    // Async
    return webClient.put()
        .uri("/users/" + id)
        .headers(h -> h.setContentType(MediaType.APPLICATION_JSON))
        .bodyValue(jsonBody)
        .retrieve()
        .bodyToMono(String.class);
  }

  public Mono<String> getNoData() {
    return webClient.get()
        .uri("/no-such-path")
        .retrieve()
        .onStatus(
            status -> status.is4xxClientError() || status.is5xxServerError(),
            clientResponse -> clientResponse.bodyToMono(String.class)
                .flatMap(errorBody -> Mono.error(new RuntimeException("Error from service: " + errorBody))))
        .bodyToMono(String.class);
  }
}
