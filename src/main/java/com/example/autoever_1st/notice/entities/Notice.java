package com.example.autoever_1st.notice.entities;

import com.example.autoever_1st.auth.entities.Member;
import com.example.autoever_1st.common.entities.TimeStamp;
import com.example.autoever_1st.notice.model.SearchType;
import com.example.autoever_1st.notice.model.TargetRange;
import com.example.autoever_1st.notice.model.Type;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "notice")
@Getter @Setter
@NoArgsConstructor
public class Notice {       // extends TimeStamp (ID 중복 문제)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noticeId;                  // 글 번호

    @Enumerated(EnumType.STRING)
    private TargetRange targetRange;        // 공개 범위 : 전체/반(웹앱/스마트팩토리/SW임베디드/IT보안/클라우드)

    @Enumerated(EnumType.STRING)
    private Type type;                      // 글 유형 카테고리(enum) : 공지/설문

    private String title;                   // 글 제목
    private String writer;                  // 글 작성자 : member_id의 해당하는 member의 name 저장용
    private String contents;
    private LocalDate targetDate;           // 공개 기한


    @Column(name = "is_pinned")
    private boolean isPinned;               // 고정 우선 여부

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @JsonBackReference  // 직렬화 제외 (무한루프 참조 방지)
    private Member member;                  // 멤버(외래키)

    @PrePersist
    @PreUpdate
    private void copyWriterFromMember() {
        if (member != null) {
            this.writer = member.getName(); // 작성 직전에 작성자 란에 회원의 이름 불러오기
        }
    }
}