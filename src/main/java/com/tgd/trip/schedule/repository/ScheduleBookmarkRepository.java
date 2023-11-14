package com.tgd.trip.schedule.repository;

import com.tgd.trip.schedule.domain.Schedule;
import com.tgd.trip.schedule.domain.ScheduleBookmark;
import com.tgd.trip.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleBookmarkRepository extends JpaRepository<ScheduleBookmark, Long> {

    void deleteByUserAndSchedule(User user, Schedule schedule);
}
