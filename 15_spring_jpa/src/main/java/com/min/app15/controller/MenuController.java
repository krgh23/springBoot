package com.min.app15.controller;

import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.min.app15.model.dto.MenuDto;
import com.min.app15.model.message.ResponseMessage;
import com.min.app15.service.MenuService;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
public class MenuController {

  private final MenuService menuService;
  
  
  /*
   * <확인방법>
   * https://web.postman.co/workspace 사이트 들어가서
   * POST : http://localhost:8080/menu 입력
   * Body 탭클릭
   * row -> JSON
   * 본문
      {
        "menuName": "갯장어샤베트",
        "menuPrice": 20000,
        "categoryCode": 4,
        "orderableStatus": "Y"
      }
      넣고 send 누르기
   */

  @PostMapping(value = "/menu", produces = "application/json")
  public ResponseMessage regist(@RequestBody MenuDto menuDto) {
    
    return ResponseMessage.builder()
                .status(200)
                .message("메뉴 등록 성공")
                .results(Map.of("menu", menuService.registMenu(menuDto)))
              .build();
    
  }
  
  
  /*
   * <확인방법>
   * https://web.postman.co/workspace 사이트 들어가서
   * PUT : http://localhost:8080/menu/1 입력(menuCode 1번 수정)
   * Body 탭클릭
   * row -> JSON
   * 본문
      {
        "menuName": "벌꿀아이스티",
        "menuPrice": 50000,
        "categoryCode": 10,
        "orderableStatus": "Y"
      }
      넣고 send 누르기
   */
  @PutMapping(value = "/menu/{menuCode}", produces = "application/json")
  public ResponseMessage modify(
      @PathVariable(name = "menuCode") Integer menuCode
    , @RequestBody MenuDto menuDto) {
    
    menuDto.setMenuCode(menuCode);
    
    return ResponseMessage.builder()
                .status(200)
                .message("메뉴 수정 성공")
                .results(Map.of("menu", menuService.modifyMenu(menuDto)))
              .build();
    
  }
  
  /*
   * <확인방법>
   * https://web.postman.co/workspace 사이트 들어가서
   * DELETE : http://localhost:8080/menu/21 입력(menuCode 21번 삭제)
   * send 누르기
   */
  @DeleteMapping(value = "/menu/{menuCode}", produces = "application/json")
  public ResponseMessage delete(@PathVariable(name = "menuCode") Integer menuCode) {
    
    menuService.deleteMenu(menuCode);
    
    return ResponseMessage.builder()
                .status(200)
                .message("메뉴 삭제 성공")
                .results(null)
              .build();
    
  }
  
  /*
   * <확인방법>
   * https://web.postman.co/workspace 사이트 들어가서
   * GET : http://localhost:8080/menu/5 입력(menuCode 5번 조회)
   * send 누르기
   */
  @GetMapping(value = "/menu/{menuCode}", produces = "application/json")
  public ResponseMessage findMenuById(@PathVariable(name = "menuCode") Integer menuCode) {
    
    return ResponseMessage.builder()
                .status(200)
                .message("메뉴 조회 성공")
                .results(Map.of("menu", menuService.findMenuById(menuCode)))
              .build();

  }
  
  /*
   * <확인방법>
   * https://web.postman.co/workspace 사이트 들어가서
   * GET : http://localhost:8080/menu 입력(menu 전체 조회)
   * send 누르기
   */
  /*
   * <확인방법>
   * https://web.postman.co/workspace 사이트 들어가서
   * GET : http://localhost:8080/menu?size=1000&page=1&sort=menuCode,desc   // ?size=1000&page=1&sort=menuCode,desc 이건 안넣어도됨 파라미터 넣으면 자동등록
   * Params 탭에서
   *   Key          Value
   *   size         5
   *   page         1
   *   sort         menuCode,desc
   *   
   *  send 누르기
   */
  @GetMapping(value = "/menu", produces = "application/json")
  public ResponseMessage findMenuList(/* @PageableDefault */ Pageable pageable) {
    
    /*
     * Pageable 인터페이스 (org.springframework.data.domain.Pageable)
     * 
     * 1. 페이징 처리에 필요한 정보(size, page, sort)를 처리하는 인터페이스입니다.
     * 2. Pageable 인터페이스의 정보를 초기화할 수 있습니다.
     *   1) @PageableDefault Annotation
     *   2) 프로퍼티에 등록 (application.properties) 
     * 3. Pageable 인터페이스에 파라미터를 전달할 수 있습니다.
     *   1) page : page=1
     *   2) size : size=10
     *   3) sort : sort=menuCode,desc 또는 sort=menuCode,asc
     * 4. 주의사항  
     *   파라미터 page=1 로 전달되면 Pageable 인터페이스는 2페이지로 인식합니다.(시작 페이지가 0이기 때문입니다.)
     *   Pageable 인터페이스의 page 값은 -1 처리해야 합니다.
     */
    
    // Pageable 인터페이스의 page 값을 1 감소하는 코드
    pageable = pageable.withPage(pageable.getPageNumber() - 1);
    
    // System.out.println("페이저블 : " + pageable);
    
    return ResponseMessage.builder()
                .status(200)
                .message("메뉴 조회 성공")
                .results(Map.of("menuList", menuService.findMenuList(pageable)))
              .build();
              
  }
  
  @GetMapping(value = "/menu/price/{menuPrice}", produces = "application/json")
  public ResponseMessage findByMenuPrice(@PathVariable(name = "menuPrice") Integer menuPrice) {
    
    return ResponseMessage.builder()
        .status(200)
        .message(menuPrice + "원 이상 메뉴 조회 성공")
        .results(Map.of("menuList", menuService.findByMenuPrice(menuPrice)))
      .build();
    
  }
  
  @GetMapping(value = "/menu/name/{menuName}", produces = "application/json")
  public ResponseMessage findByMenuName(@PathVariable(name = "menuName") String menuName) {
    
    return ResponseMessage.builder()
        .status(200)
        .message(menuName + "이 포함된 메뉴 조회 성공")
        .results(Map.of("menuList", menuService.findByMenuName(menuName)))
        .build();
    
  }
  
}
