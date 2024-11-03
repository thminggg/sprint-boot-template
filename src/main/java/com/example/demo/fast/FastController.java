package com.example.demo.fast;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/fast")
public class FastController {
  private final FastService fastService;

  @Autowired
  public FastController(FastService fastService) {
    this.fastService = fastService;
  }

  @GetMapping
  public String getHelloWorld() {
    return fastService.getHelloWorld();
  }

}
