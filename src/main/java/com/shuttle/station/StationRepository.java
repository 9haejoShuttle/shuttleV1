package com.shuttle.station;

import com.shuttle.domain.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface StationRepository extends JpaRepository<Station, Long> {
}
