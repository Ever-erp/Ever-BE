package com.example.autoever_1st.auth.dto.res;

import com.example.autoever_1st.auth.entities.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class MemberResponseDto {

    private String email;
    private String name;
    private LocalDate birth;
    private String gender;
    private String phone;
    private String address;
    private String profileImage;
    private Long classId;

    public static MemberResponseDto of(Member member) {
        return MemberResponseDto.builder()
                .email(member.getEmail())
                .name(member.getName())
                .birth(member.getBirth())
                .gender(member.getGender())
                .phone(member.getPhone())
                .address(member.getAddress())
                .profileImage(member.getProfileImage())
                .classId(member.getClassEntity().getId())
                .build();
    }
}
