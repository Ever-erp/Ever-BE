package com.example.autoever_1st.monthly_schedule.dto;

import com.example.autoever_1st.notice.dto.res.NoticeDto;
import com.example.autoever_1st.organization.dto.res.ClassScheduleResDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MonthlyScheduleDto {
    private List<NoticeDto> notices;
//    private List<VacationDto> vacations;
    private List<ClassScheduleResDto> classes;
}
