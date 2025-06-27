package com.example.autoever_1st.organization.dto.req;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClassScheduleWriteDto {
    private String subjectName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String classDesc;
    private String classUrl;
    private Long classId;
}
