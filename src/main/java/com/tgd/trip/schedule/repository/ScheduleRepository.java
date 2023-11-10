package com.tgd.trip.schedule.repository;

import com.tgd.trip.schedule.domain.Schedule;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findAllByTitleContaining(String keyword, Pageable pageable);
}
