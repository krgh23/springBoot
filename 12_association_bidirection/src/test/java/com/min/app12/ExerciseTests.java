package com.min.app12;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.min.app12.entity.exercise.Locker;
import com.min.app12.entity.exercise.Member;
import com.min.app12.entity.exercise.Team;

@SpringBootTest
class ExerciseTests {

  // 엔티티 매니저 팩토리
  private static EntityManagerFactory entityManagerFactory;
  
  // 엔티티 매니저
  private EntityManager entityManager;
  
  // 전체 테스트를 시작하기 전에 엔티티 매니저 팩토리를 생성합니다. (테스트 클래스가 동작하기 이전)
  @BeforeAll
  static void setEntityManagerFactory() throws Exception {
    entityManagerFactory = Persistence.createEntityManagerFactory("jpa_exercise");
  }
  
  // 개별 테스트를 시작하기 전에 엔티티 매니저를 생성합니다. (테스트 메소드가 동작하기 이전)
  @BeforeEach
  void setEntityManager() throws Exception {
    entityManager = entityManagerFactory.createEntityManager();
  }
  
  // 전체 테스트가 종료되면 엔티티 매니저 팩토리를 소멸합니다. (테스트 클래스가 동작한 이후)
  @AfterAll
  static void closeEntityManagerFactory() throws Exception {
    entityManagerFactory.close();
  }
  
  // 개별 테스트가 종료될때마다 엔티티 매니저를 소멸합니다. (테스트 메소드가 동작한 이후)
  @AfterEach
  void closeEntityMananger() throws Exception {
    entityManager.close();
  }
  
   @Test
   void 다대일_객체_insert_test() {
     
     // 엔티티 트랜잭션 생성
     EntityTransaction entityTransaction = entityManager.getTransaction();
     
     // 트랜잭션 시작
     entityTransaction.begin();
     
     // 삽입할 Team 엔티티
     Team team = new Team();
     team.setTeamName("무한도전");
     
     entityManager.persist(team);
     
     // 삽입할 Locker 엔티티
     Locker locker1 = new Locker();
     locker1.setLockerName("하늘색");

     Locker locker2 = new Locker();
     locker2.setLockerName("노란색");
     
     Locker locker3 = new Locker();
     locker3.setLockerName("하늘색");
     
     entityManager.persist(locker1);
     entityManager.persist(locker2);
     entityManager.persist(locker3);
     
     // 삽입할 Member 엔티티
     Member member1 = new Member();
     member1.setMemberName("유재석");
     member1.setLocker(entityManager.find(Locker.class, 1));
     member1.setTeam(entityManager.find(Team.class, 1));

     Member member2 = new Member();
     member2.setMemberName("노홍철");
     member2.setLocker(entityManager.find(Locker.class, 2));
     member2.setTeam(entityManager.find(Team.class, 1));

     Member member3 = new Member();
     member3.setMemberName("하하");
     member3.setLocker(entityManager.find(Locker.class, 3));
     member3.setTeam(entityManager.find(Team.class, 1));
     

     try {
      
       // persist() : 엔티티를 영속 컨텍스트에 저장하기
       entityManager.persist(member1);
       entityManager.persist(member2);
       entityManager.persist(member3);
       
       // 커밋 : 영속 컨텍스트의 엔티티를 DB에 반영
       entityTransaction.commit();
       
     } catch (Exception e) {
       e.printStackTrace();
       entityTransaction.rollback();
     }
     
     // 삽입된 엔티티 조회
     Member foundMember = entityManager.find(Member.class, 1); // 삽입된 엔티티의 key 값을 이용해서 조회합니다.
     System.out.println(foundMember);
     
   }
	
}
