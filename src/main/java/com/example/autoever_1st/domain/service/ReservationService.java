package com.example.autoever_1st.domain.service;

import com.example.autoever_1st.example_domain.dto.res.ReservationStatusDto;
import org.springframework.security.core.Authentication;

public interface ReservationService {
    ReservationStatusDto getReservationStatus(Authentication authentication);
}
