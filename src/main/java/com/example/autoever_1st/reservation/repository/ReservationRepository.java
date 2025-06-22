package com.example.autoever_1st.reservation.repository;

import com.example.autoever_1st.auth.entities.Member;
import com.example.autoever_1st.reservation.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByMemberAndReservationDate(Member member, LocalDate reservationDate);
    List<Reservation> findByReservationDate(LocalDate reservationDate);
    List<Reservation> findByRoomNumAndReservationDate(int roomNum, LocalDate reservationDate);
    long countByMemberAndReservationDate(Member member, LocalDate reservationDate);
    boolean existsByRoomNumAndStartTimeAndReservationDate(int roomNum, int startTime, LocalDate reservationDate);
    void deleteByReservationDateBefore(LocalDate date);
}
