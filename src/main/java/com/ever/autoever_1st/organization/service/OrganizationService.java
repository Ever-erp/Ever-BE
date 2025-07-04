package com.ever.autoever_1st.organization.service;

import com.ever.autoever_1st.auth.dto.res.MemberResponseDto;
import com.ever.autoever_1st.organization.dto.res.ClassDetailDto;
import com.ever.autoever_1st.organization.dto.res.OrgInitResDto;

import java.util.List;

public interface OrganizationService {
    OrgInitResDto getOrganizationInitData();
    ClassDetailDto getClassDetail(Long classId);
    List<MemberResponseDto> searchMembersByName(String name);
    List<MemberResponseDto> searchMembersInClass(Long classId, String name);
}
