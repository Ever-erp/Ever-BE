package com.example.autoever_1st.notice.repository;

import com.example.autoever_1st.notice.entities.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    Page<Notice> findByTitleContaining(String keyword, Pageable pageable);
    Page<Notice> findByContentsContaining(String keyword, Pageable pageable);
    Page<Notice> findByWriterContaining(String keyword, Pageable pageable);
}