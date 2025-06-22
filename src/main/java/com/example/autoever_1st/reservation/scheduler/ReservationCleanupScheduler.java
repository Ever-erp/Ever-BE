package com.example.autoever_1st.reservation.scheduler;

import com.example.autoever_1st.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReservationCleanupScheduler {
    private final ReservationRepository reservationRepository;
    /**
     * 매일 자정(00:00:00)에 실행되어 전날 예약 데이터 삭제
     */
    @Scheduled(cron = "0 0 0 * * *") // 매일 00:00
    public void deleteYesterdayReservations() {
        LocalDate today = LocalDate.now();
        reservationRepository.deleteByReservationDateBefore(today);
        log.info("[예약 삭제] {} 이전의 예약 데이터 삭제 완료", today);
    }
}
