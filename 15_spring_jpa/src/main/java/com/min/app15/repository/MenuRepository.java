package com.min.app15.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.min.app15.model.entity.Menu;

/*
 * Spring Data JPA 의 Repository 인터페이스 구조
 * 
 *   Repository                    별도 기능 없음
 *       ↑
 *   CrudRepository                CRUD 기능 제공
 *       ↑
 *   PagingAndSortingRepository    페이징 기능 제공
 *       ↑
 *   JpaRepository                 영속 컨텍스트 관련 일부 JPA 관련 추가 기능(예: 삭제)
 */

/*
 * 쿼리 메소드 (Query Method)
 * 
 * 1. JPQL을 메소드로 대신 처리할 수 있도록 제공하는 기능입니다.
 * 2. 메소드의 이름을 이용해서 JPQL을 생성하고 조회합니다.
 * 3. 메소드의 이름은 "find + 엔티티 + By + 변수명 + 키워드" 규칙을 사용합니다. (엔티티는 생략 가능합니다.)
 * 4. 쿼리 메소드 유형
 *      KEYWORD          | METHOD                      |JPQL
 *      -----------------|-----------------------------|---------------------------------------
 *   1) And              | findByCodeAndName           | WHERE m.code = ?1 AND m.name = ?2
 *   2) Or               | findByCodeOrName            | WHERE m.code = ?1 OR m.name = ?2
 *   3) Not              | findByNameNot               | WHERE m.name <> ?1
 *   4) Between          | findByPriceBetween          | WHERE m.price BETWEEN 1? AND ?2
 *   5) LessThan         | findByPriceLessThan         | WHERE m.price < ?1
 *   6) LessThanEqual    | findByPriceLessThanEqual    | WHERE m.price < ?1
 *   7) GreaterThan      | findByPriceGreaterThan      | WHERE m.price > ?1
 *   8) GreaterThanEqual | findByPriceGreaterThanEqual | WHERE m.price >= ?1
 *   9) IsNull           | findByNameIsNull            | WHERE m.name IS NULL
 *  10) (Is)NotNull      | findByNameIsNotNull         | WHERE m.name IS NOT NULL
 *  11) Like             | findByNameLike              | WHERE m.name LIKE ?1
 *  12) StartingWith     | findByNameStartingWith      | WHERE m.name LIKE ?1 || '%'
 *  13) EndingWith       | findByNameEndingWith        | WHERE m.name LIKE '%' || ?1
 *  14) Containing       | findByNameContaining        | WHERE m.name LIKE '%' || ?1 || '%'
 *  15) OrderBy          | findByNameOrderByCodeDesc   | WHERE m.name = ?1 ORDER BY m.code DESC
 */

public interface MenuRepository extends JpaRepository<Menu, Integer> {  // JpaRepository<엔티티, 엔티티ID>
  
  List<Menu> findByMenuPriceGreaterThanEqual(Integer menuPrice);
  List<Menu> findByMenuPriceGreaterThanEqual(Integer menuPrice, Sort sort);
  List<Menu> findByMenuPriceGreaterThanEqualOrderByMenuPriceDesc(Integer menuPrice);
  
  List<Menu> findByMenuNameContaining(String menuName);
  
}