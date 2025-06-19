package com.example.autoever_1st.example_domain.dto.res;

import com.example.autoever_1st.example_domain.entities.Member;
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

    public static MemberResponseDto of(Member member) {
        return MemberResponseDto.builder()
                .email(member.getEmail())
                .name(member.getName())
                .birth(member.getBirth())
                .gender(member.getGender())
                .phone(member.getPhone())
                .address(member.getAddress())
                .profileImage(member.getProfileImage())
                .build();
    }
}
