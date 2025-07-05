package com.ever.autoever_1st.notice.repository;

import com.ever.autoever_1st.notice.entities.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    // title 에 text가 포함된 공지
    Page<Notice> findByTitleContainingIgnoreCaseOrderByIsPinnedDescIdDesc(
            String text, Pageable pageable);
    // writer 에 text가 포함된 공지
    Page<Notice> findByWriterContainingIgnoreCaseOrderByIsPinnedDescIdDesc(
            String text, Pageable pageable);
    // 모든 경우에 text가 포함된 공지
    @Query("""
                   SELECT n FROM Notice n
                   WHERE LOWER(n.title)    LIKE LOWER(CONCAT('%', :text, '%'))
                      OR LOWER(n.writer)   LIKE LOWER(CONCAT('%', :text, '%'))
                   ORDER BY n.isPinned DESC, n.id DESC
               """)
    Page<Notice> searchEverywhere(@Param("text") String text, Pageable pageable);
    // startDate와 endDate 사이에 있는 TargetDate(not null, 상단 고정 내림차순, 작성일 내림차순)
    List<Notice> findByTargetDateIsNotNullAndTargetDateBetweenOrderByIsPinnedDescRegistedAtDesc(
            LocalDate startDate, LocalDate endDate);
}