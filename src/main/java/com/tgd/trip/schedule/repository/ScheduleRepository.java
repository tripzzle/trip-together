package com.tgd.trip.schedule.repository;

import com.tgd.trip.schedule.domain.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    Page<Schedule> findAllByTitleContainingAndViewYnNot(String keyword, Boolean viewYn, Pageable pageable);
}
