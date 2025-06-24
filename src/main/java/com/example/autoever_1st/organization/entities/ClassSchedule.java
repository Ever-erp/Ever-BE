package com.example.autoever_1st.organization.entities;

import com.example.autoever_1st.common.entities.TimeStamp;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassSchedule extends TimeStamp {
    private String subjectName;

    private LocalDate startDate;

    private LocalDate endDate;

    private String classDesc;

    private String classUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id")
    private ClassEntity classEntity;
}
