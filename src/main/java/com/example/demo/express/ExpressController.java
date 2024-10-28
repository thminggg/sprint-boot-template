package com.example.demo.express;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@RestController
@RequestMapping(path = "api/v1/express")
public class ExpressController {
    private final ExpressService expressService;

    @Autowired
    public ExpressController(ExpressService expressService) {
        this.expressService = expressService;
    }

    @GetMapping
    public String getHelloWorld(@RequestParam Map<String, String> queryParams) {
        return expressService.getHelloWorld(queryParams);
    }

    @PostMapping
    public String postData(@RequestBody String jsonBody) {
        return expressService.postData(jsonBody);
    }

    @PutMapping(path = "/users/{id}")
    public void putData(@PathVariable("id") String id, @RequestBody String jsonBody) {
        Mono<String> response = expressService.putData(id, jsonBody);
        response.subscribe(
                result -> System.out.println("Async handling: " + result),
                error -> System.out.println("Error from service: " + error.getMessage()));
        // Or
        // response.subscribe(System.out::println);
    }

    @GetMapping(path = "/get-no-data")
    public ResponseEntity<String> getNoData() {
        Mono<String> response = expressService.getNoData();
        return response
                .map(ResponseEntity::ok)
                .doOnError(error -> System.out.println("Error: " + error))
                .onErrorReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Not found"))
                .block();
    }
}
