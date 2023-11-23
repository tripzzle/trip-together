package com.tgd.trip.schedule.repository;

import com.tgd.trip.attraction.repository.AttractionBookmarkRepository;
import com.tgd.trip.schedule.domain.Schedule;
import com.tgd.trip.schedule.domain.ScheduleBookmark;
import com.tgd.trip.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleBookmarkRepository extends JpaRepository<ScheduleBookmark, Long> {

    void deleteByUserAndSchedule(User user, Schedule schedule);

    boolean existsScheduleBookmarkByUserAndSchedule(User user, Schedule schedule);

    List<ScheduleBookmark> findAllByUser(User user);

}
