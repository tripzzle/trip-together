package com.tgd.trip.schedule.repository;

import com.tgd.trip.schedule.domain.ScheduleBookmark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleBookmarkRepository extends JpaRepository<ScheduleBookmark, Long> {
}
