package com.min.app15.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.min.app15.model.exception.MenuNotFoundException;
import com.min.app15.model.message.ResponseErrorMessage;

@ControllerAdvice
public class ExceptionControllerAdvice {

  @ExceptionHandler(MenuNotFoundException.class)
  public ResponseEntity<ResponseErrorMessage> handler(MenuNotFoundException e) {
    
    // 응답 코드 404 를 사용하는 경우
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseErrorMessage.builder()
                .code(10)
                .message(e.getMessage())
                .decribe("존재하지 않는 메뉴 코드")
              .build());
    
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ResponseErrorMessage> handler(IllegalArgumentException e) {
    
    // 응답 코드 400 을 사용하는 경우
    return ResponseEntity.badRequest().body(ResponseErrorMessage.builder()
        .code(20)
        .message(e.getMessage())
        .decribe("존재하지 않는 메뉴 코드")
        .build());
    
  }
  
}
