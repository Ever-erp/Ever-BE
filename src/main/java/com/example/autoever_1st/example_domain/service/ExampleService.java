package com.example.autoever_1st.example_domain.service;

import com.example.autoever_1st.example_domain.entities.TestEntity;

public interface ExampleService {

    // CRUD
    public TestEntity save(String title, String content);

    public TestEntity findById(Long id);

    public TestEntity update(Long id, String title, String content);

    public void deleteById(Long id);

}
