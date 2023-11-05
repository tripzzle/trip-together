package com.tgd.trip.schedule.repository;

import com.tgd.trip.schedule.domain.Day;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DayRepository extends JpaRepository<Day, Long> {
}
