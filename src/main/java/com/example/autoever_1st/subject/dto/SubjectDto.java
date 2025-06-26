package com.example.autoever_1st.subject.dto;

import com.example.autoever_1st.organization.dto.common.ClassScheduleDto;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExtendedClassScheduleDto {
    private ClassScheduleDto extended;
}
