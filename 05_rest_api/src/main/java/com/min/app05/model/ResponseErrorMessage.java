package com.min.app05.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class ResponseErrorMessage {
  
  @Schema(description = "응답 에러 코드", nullable = false, allowableValues = {"00", "01", "02", "03", "404", "05", "06", "07"})
  private String code;
  
  @Schema(description = "예외 메시지", nullable = false)
  private String message;        // 예외 메시지 (Exception 의 message)
  
  @Schema(description = "예외 메시지 설명", nullable = false)
  private String describe;  
}