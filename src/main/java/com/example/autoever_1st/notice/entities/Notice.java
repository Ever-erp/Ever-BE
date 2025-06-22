package com.example.autoever_1st.notice.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "notice")
@Getter
@Setter
@NoArgsConstructor
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noticeId;

    private String type;
    private String title;
    private String writer;
    private String contents;
    private String file;

    @Column(name = "is_pinned")
    private boolean isPinned;

    @ManyToOne
    @JoinColumn(name = "member_id")
    @JsonBackReference  // 직렬화 제외 (무한루프 참조 방지)
    private Member member;
}