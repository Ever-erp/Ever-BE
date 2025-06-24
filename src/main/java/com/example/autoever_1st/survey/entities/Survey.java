package com.example.autoever_1st.survey.entities;

import com.example.autoever_1st.common.entities.TimeStamp;
import com.example.autoever_1st.organization.entities.ClassEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Getter
@AllArgsConstructor
@Builder
public class Survey extends TimeStamp {

    @Column(unique = true, nullable = false)
    private String uuid;

    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id")
    private ClassEntity classEntity;

    private String question;

    private String questionMeta;

    private LocalDate postDate;
}
