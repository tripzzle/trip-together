package com.tgd.trip.attraction.repository;

import com.tgd.trip.attraction.domain.Attraction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttractionRepository extends JpaRepository<Attraction, Long> {
    List<Attraction> findAllByLatitudeBetweenAndLongitudeBetween(Double startLat, Double endLat, Double startLong, Double endLong);

    List<Attraction> findAllByGugun_IdGugunCodeAndGugun_IdSidoCode(Long gugunCode, Long sidoCode);

    List<Attraction> findAllBySido_SidoCode(Long sidoCode);
}
