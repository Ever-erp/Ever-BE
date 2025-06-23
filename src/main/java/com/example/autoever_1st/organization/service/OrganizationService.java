package com.example.autoever_1st.organization.service;

import com.example.autoever_1st.auth.dto.res.MemberResponseDto;
import com.example.autoever_1st.organization.dto.res.ClassDetailDto;
import com.example.autoever_1st.organization.dto.res.OrgInitResDto;

import java.util.List;

public interface OrganizationService {
    OrgInitResDto getOrganizationInitData();
    ClassDetailDto getClassDetail(Long classId);
    List<MemberResponseDto> searchMembersByName(String name);
    List<MemberResponseDto> searchMembersInClass(Long classId, String name);
}
