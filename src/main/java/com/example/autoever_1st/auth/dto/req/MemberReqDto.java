package com.example.autoever_1st.auth.dto.req;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class MemberReqDto {
    private String email;
    private String pwd;
    private String pwdCheck;
    private String name;
    private LocalDate birth;
    private String gender;
    private String phone;
    private String address;
    private String profileImage;
}
