package com.example.autoever_1st.auth.repository;

import com.example.autoever_1st.auth.entities.Member;
import com.example.autoever_1st.organization.entities.ClassEntity;
import com.example.autoever_1st.organization.entities.Position;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByEmail(String email);
    Optional<Member> findByEmail(String email);
    List<Member> findByPosition(Position position);
    List<Member> findByClassEntity(ClassEntity classEntity);
    List<Member> findByNameContaining(String name);
    List<Member> findByClassEntityIdAndNameContaining(Long classId, String name);
}
