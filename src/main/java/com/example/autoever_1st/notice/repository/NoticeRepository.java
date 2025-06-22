package com.example.autoever_1st.notice.repository;

import com.example.autoever_1st.notice.entities.Notice;
import com.example.autoever_1st.notice.model.TargetRange;
import com.example.autoever_1st.notice.model.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    // TargetRange와 Type 조건으로 페이징 검색
    Page<Notice> findByTargetRangeAndType(TargetRange targetRange, Type type, Pageable pageable);

    // TargetRange만 조건일 경우
    Page<Notice> findByTargetRange(TargetRange targetRange, Pageable pageable);

    // Type만 조건일 경우
    Page<Notice> findByType(Type type, Pageable pageable);

    // pinned 상태를 먼저 내림차순, 그 다음 최신순 정렬 예
    @Query("SELECT n FROM Notice n ORDER BY n.isPinned DESC, n.noticeId DESC")
    List<Notice> findAllOrderByPinnedAndIdDesc();

    // 페이징 지원 시 Pageable 사용, 글 번호로 내림차순
    Page<Notice> findAllByOrderByIsPinnedDescNoticeIdDesc(Pageable pageable);
}