package com.tgd.trip.schedule.repository;

import com.tgd.trip.schedule.domain.Schedule;
import com.tgd.trip.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    Page<Schedule> findAllByTitleContainingAndViewYnNot(String keyword, Boolean viewYn, Pageable pageable);

    List<Schedule> findAllByUser(User user);
}