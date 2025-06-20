package com.example.autoever_1st.auth.repository;

import com.example.autoever_1st.auth.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

}
