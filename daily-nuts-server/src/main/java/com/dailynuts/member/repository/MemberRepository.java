package com.dailynuts.member.repository;

import com.dailynuts.member.entity.Member;
import com.dailynuts.member.entity.type.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByLoginId(String loginId);
    List<Member> findByNameContainingAndRole(String name, Role role);
    boolean existsByLoginId(String loginId);
    List<Member> findAllByIdIn(List<Long> ids);

}