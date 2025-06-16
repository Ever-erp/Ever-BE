package com.example.autoever_1st.example_domain.repository;

import com.example.autoever_1st.example_domain.entities.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TestRepository extends JpaRepository<TestEntity, Long> {

    // CRUD
    TestEntity save(TestEntity testEntity);
    Optional<TestEntity> findById(Long id);
    void deleteById(Long id);

}
