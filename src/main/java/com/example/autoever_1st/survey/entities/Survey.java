package com.example.autoever_1st.survey.entities;

import com.example.autoever_1st.common.entities.TimeStamp;
import com.example.autoever_1st.organization.entities.ClassEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@Builder
public class Survey extends TimeStamp {

    @Column(unique = true, nullable = false)
    private String uuid;

    private String title;

    private String description;

    private String status;

    @Column(columnDefinition = "TEXT")
    private String question;

    @Column(columnDefinition = "TEXT", name = "question_meta")
    private String questionMeta;

    private LocalDate postDate;

    private LocalDate dueDate;
}
