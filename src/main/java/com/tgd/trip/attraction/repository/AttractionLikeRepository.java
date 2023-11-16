package com.tgd.trip.attraction.repository;

import com.tgd.trip.attraction.domain.Attraction;
import com.tgd.trip.attraction.domain.AttractionLike;
import com.tgd.trip.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttractionLikeRepository extends JpaRepository<AttractionLike, Long> {

    boolean existsByUserAndAttraction(User user, Attraction attraction);

    void deleteByUserAndAttraction(User findUser, Attraction findAttraction);
}
