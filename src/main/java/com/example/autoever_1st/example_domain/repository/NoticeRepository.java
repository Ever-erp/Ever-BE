package com.example.autoever_1st.example_domain.repository;

import com.example.autoever_1st.example_domain.entities.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}