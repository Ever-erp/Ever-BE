package com.ever.autoever_1st.organization.repository;

import com.ever.autoever_1st.organization.entities.ClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClassEntityRepository extends JpaRepository<ClassEntity, Long> {
    List<ClassEntity>findAll();
    Optional<ClassEntity> findByNameAndCohort(String name, int cohort);
    Optional<ClassEntity> findByName(String className);
}
