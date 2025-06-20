package com.example.autoever_1st.example_domain.dto.res;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
public class VacationResDto {
    private String vacation_type;       // 휴가 종류 ( 휴가 / 병가 / 조퇴 / 외출 / 공휴일 / 휴무일 )
    private LocalDate vacation_date;    // 휴가 날짜
    private String vacation_desc;       // 휴가 사유
}
