package com.example.autoever_1st.notice.dao;

import com.example.autoever_1st.notice.entities.TestEntity;

import java.util.Optional;

public interface TestDao {

    // CRUD
    public TestEntity save(TestEntity testEntity);
    public Optional<TestEntity> findById(Long id);
    public boolean deleteById(Long id);
}
