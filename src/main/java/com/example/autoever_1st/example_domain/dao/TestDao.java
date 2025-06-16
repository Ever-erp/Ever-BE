package com.example.autoever_1st.example_domain.dao;

import com.example.autoever_1st.example_domain.entities.TestEntity;

import java.util.Optional;

public interface TestDao {

    // CRUD
    public TestEntity save(TestEntity testEntity);
    public Optional<TestEntity> findById(Long id);
    public boolean deleteById(Long id);
}
