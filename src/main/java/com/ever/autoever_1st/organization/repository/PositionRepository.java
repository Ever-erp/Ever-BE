package com.ever.autoever_1st.organization.repository;

import com.ever.autoever_1st.organization.entities.Position;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PositionRepository extends JpaRepository<Position, Long> {
    Optional<Position> findByRole(String role);
}
