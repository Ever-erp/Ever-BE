package com.ever.autoever_1st.reservation.repository;

import com.ever.autoever_1st.auth.entities.Member;
import com.ever.autoever_1st.reservation.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByMemberAndReservationDate(Member member, LocalDate reservationDate);
    List<Reservation> findByReservationDate(LocalDate reservationDate);
    List<Reservation> findByRoomNumAndReservationDate(int roomNum, LocalDate reservationDate);
    long countByMemberAndReservationDate(Member member, LocalDate reservationDate);
    boolean existsByRoomNumAndStartTimeAndReservationDate(int roomNum, int startTime, LocalDate reservationDate);
    void deleteByReservationDateBefore(LocalDate date);

    @Query("SELECT r FROM Reservation r WHERE r.roomNum = :roomNum AND r.startTime = :startTime AND r.reservationDate = :date AND r.member = :member")
    Optional<Reservation> findByRoomNumAndStartTimeAndDateAndMember(@Param("roomNum") int roomNum, @Param("startTime") int startTime,
                                                                    @Param("date") LocalDate date, @Param("member") Member member);
}
