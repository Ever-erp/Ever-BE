package com.ever.autoever_1st.organization.dto.res;

import com.ever.autoever_1st.organization.dto.common.ClassScheduleDto;
import com.ever.autoever_1st.auth.dto.res.MemberResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ClassDetailDto {
    private Long id;
    private String name;
    private int cohort;
    private List<ClassScheduleDto> schedules;
    private List<MemberResponseDto> members;
}
