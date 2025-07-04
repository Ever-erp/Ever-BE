package com.ever.autoever_1st.auth.entities;

import com.ever.autoever_1st.common.entities.TimeStamp;
import com.ever.autoever_1st.notice.entities.Notice;
import com.ever.autoever_1st.organization.entities.ClassEntity;
import com.ever.autoever_1st.organization.entities.Position;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id")
    private ClassEntity classEntity;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    @JsonManagedReference  // 직렬화 허용 (무한루프 참조 방지)
    private List<Notice> noticeList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id")
    private Position position;

    @Builder // 빌더 패턴 적용
    public Member(String email, String pwd, String name, LocalDate birth, String gender,
                  String phone, String address, String profileImage, ClassEntity classEntity, Position position) {
        this.email = email;
        this.pwd = pwd;
        this.name = name;
        this.birth = birth;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
        this.profileImage = profileImage;
        this.isActive = true;
        this.classEntity = classEntity;
        this.position = position;
    }
    public void deactivate() {
        this.isActive = false;
    }
    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
