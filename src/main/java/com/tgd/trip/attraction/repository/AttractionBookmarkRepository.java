package com.tgd.trip.attraction.repository;

import com.tgd.trip.attraction.domain.Attraction;
import com.tgd.trip.attraction.domain.AttractionBookmark;
import com.tgd.trip.schedule.domain.Schedule;
import com.tgd.trip.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttractionBookmarkRepository extends JpaRepository<AttractionBookmark, Long> {

    void deleteByUserAndAttraction(User findUser, Attraction findAttraction);
    List<AttractionBookmark> findAllByUser(User user);

}
