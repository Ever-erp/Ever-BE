package com.example.autoever_1st.survey.entities;

import com.example.autoever_1st.common.entities.TimeStamp;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Survey extends TimeStamp {

    @Column(unique = true, nullable = false)
    private String uuid;

    private String title;

    private String description;

    private String status;

    private int surveySize;

    @Column(columnDefinition = "TEXT")
    private String question;

    @Column(columnDefinition = "TEXT", name = "question_meta")
    private String questionMeta;

    private LocalDate postDate;

    private LocalDate dueDate;

    public void updateSurvey(String title, String desc, LocalDate dueDate, String status,
                             Integer size, String question, String meta) {
        this.title = title;
        this.description = desc;
        this.dueDate = dueDate;
        this.status = status;
        this.surveySize = size;
        this.question = question;
        this.questionMeta = meta;
    }
}
