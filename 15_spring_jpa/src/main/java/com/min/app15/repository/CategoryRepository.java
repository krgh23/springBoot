package com.min.app15.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.min.app15.model.entity.Category;

/*
 * Spring Data JPA 의 Repository 인터페이스 구조
 * 
 *   Repository                    별도기능없음
 *       ↑
 *   CrudRepository                CRUD 기능 제공
 *       ↑
 *   PagingAndSortingRepository    페이징 기능 제공
 *       ↑
 *   JpaRepository                 영속 컨텍스트 관련 일부 JPA 관련 추가 기능(예: 삭제)
 */

// JpaRepository<엔티티, 엔티티ID타입>

public interface CategoryRepository extends JpaRepository<Category, Integer>{

  List<Category> findByCategoryCodeGreaterThan(Integer categoryCode);


}
