package com.example.autoever_1st.example_domain.controller;

import com.example.autoever_1st.example_domain.dto.res.ReservationResDto;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@Slf4j      // Log 메세지 출력을 위한 어노테이션
@RestController // REST API (GET, POST, PUT, DELETE)
//@RequiredArgsConstructor    // 생성자를 통한 의존성 주입
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:5173"})     // CORS 설정 : error 감지 방지
@RequestMapping("/mock/reservation")
public class ReservationController {
    @GetMapping("/test")
    public ReservationResDto mockReservation() {
        ReservationResDto reservationResDto = new ReservationResDto();
        reservationResDto.setStart_time(4);
        reservationResDto.setHead_count(4);
        reservationResDto.setReservation_desc("웹&앱 / 1시간");
        reservationResDto.setRoom_num(7);
        return reservationResDto;
    }
}