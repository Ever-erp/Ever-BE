package com.ever.autoever_1st.auth.repository;

import com.ever.autoever_1st.auth.entities.Member;
import com.ever.autoever_1st.organization.entities.ClassEntity;
import com.ever.autoever_1st.organization.entities.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByEmail(String email);
    Optional<Member> findByEmail(String email);
    @Query("SELECT m FROM Member m JOIN FETCH m.position WHERE m.email = :email")
    Optional<Member> findByEmailWithPosition(@Param("email") String email);
    List<Member> findByPosition(Position position);
    @Query("SELECT m FROM Member m JOIN FETCH m.position WHERE m.classEntity = :classEntity")
    List<Member> findByClassEntityWithPosition(@Param("classEntity") ClassEntity classEntity);
    List<Member> findByNameContaining(String name);
    List<Member> findByClassEntityIdAndNameContaining(Long classId, String name);
    int countByClassEntity(ClassEntity classEntity);
    @Query("SELECT m FROM Member m JOIN FETCH m.position")
    List<Member> findAllWithPosition();
}
