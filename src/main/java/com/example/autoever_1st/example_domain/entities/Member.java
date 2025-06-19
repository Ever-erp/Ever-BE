package com.example.autoever_1st.example_domain.entities;

import com.example.autoever_1st.common.entities.TimeStamp;
import com.example.autoever_1st.constant.Authority;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor
public class Member extends TimeStamp {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String pwd;

    @Column(nullable = false)
    private String name;

    private LocalDate birth;

    private String gender;

    private String phone;

    private String address;

    private String profileImage;

    private boolean isActive = true; // 기본값 true

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @Builder // 빌더 패턴 적용
    public Member(String email, String pwd, String name, LocalDate birth, String gender,
                  String phone, String address, String profileImage) {
        this.email = email;
        this.pwd = pwd;
        this.name = name;
        this.birth = birth;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
        this.profileImage = profileImage;
        this.isActive = true;
        this.authority = Authority.ROLE_USER;
    }

}
