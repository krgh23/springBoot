package com.min.app05.controller;

import java.util.Map;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.min.app05.model.ResponseMessage;
import com.min.app05.model.dto.InsertUserDto;
import com.min.app05.model.dto.UpdateUserDto;
import com.min.app05.model.dto.UserDto;
import com.min.app05.service.IUserService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

// REST API 서비스 개발을 위한 컨트롤러 : @Controller + @ResponseBody
// 뷰를 반환하지 않는다.
@RestController
@RequiredArgsConstructor
public class UserController {

  private final IUserService userService;
  
  /*
   * <확인방법>
   * https://web.postman.co/workspace 사이트 들어가서
   * POST : http://localhost:8080/users 입력
   * row -> JSON
   * 본문
      {
       "email": "admin1@namver.com",
       "pwd": "admin1",
       "nickname": "관리자1"
      }
      넣고 send 누르기
   */
  // @Validated : 유효성 검사를 수행할 객체에 추가하는 애너테이션입니다.
  //              실제 유효성 검사는 해당 객체(UserDto)에서 수행합니다.

  @PostMapping("/users")
  public ResponseMessage registUser(@Validated @RequestBody InsertUserDto insertUserDto) {
    return ResponseMessage.builder()
              .status(201)  // 201 Created (요청이 성공적으로 처리되었으며, 자원이 생성되었음을 나타내는 성공 상태 응답 코드)
                            // 200 OK 를 사용해도 무방합니다.
                            // https://developer.mozilla.org/ko/docs/Web/HTTP/Status/201
              .message("사용자 등록 성공")
              .results(Map.of("user", userService.registUser(insertUserDto)))
              .build();
  }
  
  /*
   * <확인방법>
   * https://web.postman.co/workspace 사이트 들어가서
   * PUT : http://localhost:8080/users 입력
   * row -> JSON
   * 본문
      {
       "userId": "1",
       "pwd": "admin2",
       "nickname": "관리자2"
      }
      넣고 send 누르기
   */
  @PutMapping("/users")
  public ResponseMessage modifyUser(@Validated @RequestBody UpdateUserDto updateUserDto) throws Exception {
    return ResponseMessage.builder()
              .status(201) // 수정 정보 또한 삽입과 동일한 응답코드를 사용합니다.
              .message("사용자 정보 수정 성공")
              .results(Map.of("user", userService.modifyUser(updateUserDto)))
              .build();
  }
  
  /*
   * <확인방법>
   * https://web.postman.co/workspace 사이트 들어가서
   * PUT : http://localhost:8080/users/1 입력
   * row -> JSON
   * 본문
      {
       "pwd": "admin3",
       "nickname": "관리자3"
      }
      넣고 send 누르기
   */
  @PutMapping("/users/{userId}")
  public ResponseMessage modifyUser(@PathVariable int userId, @Validated @RequestBody UpdateUserDto updateUserDto) throws Exception {
    updateUserDto.setUserId(userId);
    return ResponseMessage.builder()
              .status(201)  // 수정 또한 삽입과 동일한 응답 코드를 사용합니다.
                            // 200 OK 를 사용해도 무방합니다.
              .message("사용자 정보 수정 성공")
              .results(Map.of("user", userService.modifyUser(updateUserDto)))
              .build();
  }
  
  /*
   * <확인방법>
   * https://web.postman.co/workspace 사이트 들어가서
   * DELETE : http://localhost:8080/users/1 입력
   * send 누르기
   */
  @DeleteMapping("/users/{userId}")
  public ResponseMessage removeUser(@PathVariable int userId) throws Exception {
    userService.removeUser(userId);
    return ResponseMessage.builder()
                .status(204) // 204 실무사용x, 
                            // 요청이 성공하였으나, 해당 데이터를 참조할 수 없음을 의미합니다.
                            // 삭제 후 204를 사용할 수 있으나 실제로는 주로 200을 사용합니다.
                            // https://developer.mozilla.org/ko/docs/Web/HTTP/Status/204
                .message("사용자 삭제 성공")
                .results(null)
              .build();
  }
  
  /*
   * <확인방법>
   * https://web.postman.co/workspace 사이트 들어가서
   * GET : http://localhost:8080/users 입력
   * send 누르기
   */
  @GetMapping("/users")
  public ResponseMessage getUsers(HttpServletRequest request) {
    return ResponseMessage.builder()
              .status(200)  // 요청이 성공하여 가져온 리소스를 메시지 본문으로 전송하였습니다.
              .message("사용자 목록 조회 성공")
              .results(Map.of("users", userService.getUsers(request)))
            .build();
  }
  
  /*
   * <확인방법>
   * https://web.postman.co/workspace 사이트 들어가서
   * GET : http://localhost:8080/users/1 입력
   * send 누르기
   */
  @GetMapping("/users/{userId}")
  public ResponseMessage getUserById(@PathVariable int userId) throws Exception {
    return ResponseMessage.builder()
              .status(200)  // 요청이 성공하여 가져온 리소스를 메시지 본문으로 전송하였습니다.
              .message("사용자 조회 성공")
              .results(Map.of("user", userService.getUserById(userId)))
            .build();
  }
  
}
