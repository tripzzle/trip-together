package com.tgd.trip.schedule.repository;

import com.tgd.trip.schedule.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
