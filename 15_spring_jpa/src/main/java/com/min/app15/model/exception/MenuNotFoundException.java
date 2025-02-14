package com.min.app15.model.exception;

/*
 * extends Exception (예상가능한 예외)
 *   - checked Exception
 *   - 예외 처리(try - catch) 또는 예외 회피 (throws) 코드가 반드시 필요하다. 
 * 
 * extends RuntimeException (실행을 해봐야 예외가 발생할지 알 수 있다)
 *   - Unchecked Exception
 *   - 예외 처리(try - catch) 또는 예외 회피 (throws) 코드를 생략할 수 있습니다.
 */

public class MenuNotFoundException extends Exception{

  /**
   * 
   */
  private static final long serialVersionUID = 544575198637096472L;

  public MenuNotFoundException(String message) {
    super(message);
  }
  
}
