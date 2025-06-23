package com.example.autoever_1st.organization.entities;

import com.example.autoever_1st.auth.entities.Member;
import com.example.autoever_1st.common.entities.TimeStamp;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Position extends TimeStamp {

    @Column(unique = true, nullable = false)
    private String role;
}
