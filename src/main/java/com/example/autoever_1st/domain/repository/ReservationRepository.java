package com.example.autoever_1st.domain.repository;

import com.example.autoever_1st.domain.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

}
