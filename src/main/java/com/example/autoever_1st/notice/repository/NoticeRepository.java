package com.example.autoever_1st.notice.repository;

import com.example.autoever_1st.notice.entities.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}