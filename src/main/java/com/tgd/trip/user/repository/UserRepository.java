package com.tgd.trip.user.repository;

import com.tgd.trip.schedule.domain.Schedule;
import com.tgd.trip.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    List<Schedule> findAllSchedulesByUserId(Long userId);

}
