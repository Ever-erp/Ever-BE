package com.ever.autoever_1st.organization.dto.res;

import com.ever.autoever_1st.auth.dto.res.MemberResponseDto;
import com.ever.autoever_1st.organization.dto.common.ClassWithScheduleDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class OrgInitResDto {
    private List<ClassWithScheduleDto> classWithScheduleDtos;
    private List<MemberResponseDto> instructors;
}
