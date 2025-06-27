package com.example.autoever_1st.notice.repository;

import com.example.autoever_1st.notice.constant.TargetRange;
import com.example.autoever_1st.notice.constant.Type;
import com.example.autoever_1st.notice.entities.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    // TargetRange와 Type 조건으로 페이징 검색
    Page<Notice> findByTargetRangeAndType(TargetRange targetRange, Type type, Pageable pageable);

    // title 에 text가 포함된 공지
    Page<Notice> findByTitleContainingIgnoreCaseOrderByIsPinnedDescIdDesc(
            String text, Pageable pageable);
    // contents 에 text가 포함된 공지
    Page<Notice> findByContentsContainingIgnoreCaseOrderByIsPinnedDescIdDesc(
            String text, Pageable pageable);
    // writer 에 text가 포함된 공지
    Page<Notice> findByWriterContainingIgnoreCaseOrderByIsPinnedDescIdDesc(
            String text, Pageable pageable);
    /* ▶︎ “제목 | 내용 | 작성자 세 필드 전부 한 번에” 라는 요구가 있으면
          JPQL 하나로 뽑는 방법도 가능. 필요하면 주석 풀어서 써! */
    @Query("""
                   SELECT n FROM Notice n
                   WHERE LOWER(n.title)    LIKE LOWER(CONCAT('%', :text, '%'))
                      OR LOWER(n.contents) LIKE LOWER(CONCAT('%', :text, '%'))
                      OR LOWER(n.writer)   LIKE LOWER(CONCAT('%', :text, '%'))
                   ORDER BY n.isPinned DESC, n.id DESC
               """)
    Page<Notice> searchEverywhere(@Param("text") String text, Pageable pageable);
    // startDate와 endDate 사이에 있는 TargetDate(not null, 상단 고정 내림차순, 작성일 내림차순)
    List<Notice> findByTargetDateIsNotNullAndTargetDateBetweenOrderByIsPinnedDescRegistedAtDesc(
            LocalDate startDate, LocalDate endDate);

    Page<Notice> findByTargetRange(TargetRange targetRange, Pageable pageable);

    Page<Notice> findByType(Type type, Pageable pageable);
}