package com.ever.autoever_1st.monthly_schedule.dto;

import com.ever.autoever_1st.notice.dto.res.NoticeDto;
import com.ever.autoever_1st.organization.dto.res.ClassScheduleResDto;
import com.ever.autoever_1st.vacation.dto.VacationScheduleDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MonthlyScheduleDto {
    private List<NoticeDto> notices;
    private List<VacationScheduleDto> vacations;
    private List<ClassScheduleResDto> classes;
}
