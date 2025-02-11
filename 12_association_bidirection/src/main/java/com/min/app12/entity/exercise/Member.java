package com.min.app12.entity.exercise;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

// Team   - Member
// 1      - M  (@ManyToOne + @JoinColum)   

// Member - Locker
// 1      - 1  (@OneToOne + @JoinColum)

@Entity(name = "member")
@Table(name = "tbl_member")
public class Member {
  
  @Id
  @GeneratedValue( strategy = GenerationType.IDENTITY )
  @Column(name = "member_id")
  private Integer memberId;
  
  @Column(name = "member_name")
  private String memberName;
  
  @JoinColumn(name = "team_id")
  @ManyToOne(cascade = CascadeType.PERSIST)
  private Team team;
  
  @JoinColumn(name = "locker_id")
  @OneToOne(cascade = CascadeType.PERSIST)
  private Locker locker;

  public Member(Integer memberId, String memberName, Team team, Locker locker) {
    super();
    this.memberId = memberId;
    this.memberName = memberName;
    this.team = team;
    this.locker = locker;
  }

  public Member() {
    super();
    // TODO Auto-generated constructor stub
  }

  public Integer getMemberId() {
    return memberId;
  }

  public void setMemberId(Integer memberId) {
    this.memberId = memberId;
  }

  public String getMemberName() {
    return memberName;
  }

  public void setMemberName(String memberName) {
    this.memberName = memberName;
  }

  public Team getTeam() {
    return team;
  }

  public void setTeam(Team team) {
    this.team = team;
  }

  public Locker getLocker() {
    return locker;
  }

  public void setLocker(Locker locker) {
    this.locker = locker;
  }

  @Override
  public String toString() {
    return "Member [memberId=" + memberId + ", memberName=" + memberName + ", team=" + team + ", locker=" + locker
        + "]";
  }
}
