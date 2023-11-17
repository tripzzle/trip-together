package com.tgd.trip.schedule.repository;

import com.tgd.trip.schedule.domain.Schedule;
import com.tgd.trip.schedule.domain.ScheduleLike;
import com.tgd.trip.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ScheduleLikeRepository extends JpaRepository<ScheduleLike, Long> {

    boolean existsByCreatedAtBetweenAndUserAndSchedule(LocalDateTime startDate, LocalDateTime endDate, User user, Schedule schedule);
}
