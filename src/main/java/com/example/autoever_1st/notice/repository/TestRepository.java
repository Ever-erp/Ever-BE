package com.example.autoever_1st.notice.repository;

import com.example.autoever_1st.notice.entities.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TestRepository extends JpaRepository<TestEntity, Long> {

    // CRUD
    TestEntity save(TestEntity testEntity);
    Optional<TestEntity> findById(Long id);
    void deleteById(Long id);

}
