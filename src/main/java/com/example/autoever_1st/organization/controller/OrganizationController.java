package com.example.autoever_1st.organization.controller;

import com.example.autoever_1st.auth.dto.res.MemberResponseDto;
import com.example.autoever_1st.common.dto.response.ApiResponse;
import com.example.autoever_1st.organization.dto.res.ClassDetailDto;
import com.example.autoever_1st.organization.dto.res.OrgInitResDto;
import com.example.autoever_1st.organization.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/organization")
@RequiredArgsConstructor
public class OrganizationController {
    private final OrganizationService organizationService;

    @GetMapping("/init")
    public ApiResponse<OrgInitResDto> getOrganizationInitData() {
            OrgInitResDto orgInitResDto = organizationService.getOrganizationInitData();
            return ApiResponse.success(orgInitResDto, HttpStatus.OK.value());
    }

    @GetMapping("/class/{classId}")
    public ApiResponse<ClassDetailDto> getClassDetail(@PathVariable Long classId) {
        ClassDetailDto classDetailDto = organizationService.getClassDetail(classId);
        return ApiResponse.success(classDetailDto, HttpStatus.OK.value());
    }

    @GetMapping("/search")
    public ApiResponse<List<MemberResponseDto>> searchAllMembers(@RequestParam String name) {
        List<MemberResponseDto> members = organizationService.searchMembersByName(name);
        return ApiResponse.success(members, HttpStatus.OK.value());
    }

    @GetMapping("/search/class")
    public ApiResponse<List<MemberResponseDto>> searchMembersInClass(@RequestParam Long classId, @RequestParam String name) {
        List<MemberResponseDto> members = organizationService.searchMembersInClass(classId, name);
        return ApiResponse.success(members, HttpStatus.OK.value());
    }
}
