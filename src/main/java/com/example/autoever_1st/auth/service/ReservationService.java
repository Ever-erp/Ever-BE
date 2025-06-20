package com.example.autoever_1st.auth.service;

import com.example.autoever_1st.notice.dto.res.ReservationStatusDto;
import org.springframework.security.core.Authentication;

public interface ReservationService {
    ReservationStatusDto getReservationStatus(Authentication authentication);
}
