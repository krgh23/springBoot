package edu.min.pay.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class HomeController {

  @GetMapping("/payhome")
  public String payhome() {
    log.info("-------------------- welcome payhome --------------------");
    return "pay/home";
  }
  
}
