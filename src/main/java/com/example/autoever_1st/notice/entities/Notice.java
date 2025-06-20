package com.example.autoever_1st.notice.entities;

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
}