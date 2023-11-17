package com.tgd.trip.attraction.repository;

import com.tgd.trip.attraction.domain.Attraction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttractionRepository extends JpaRepository<Attraction, Long> {
    Page<Attraction> findAllByLatitudeBetweenAndLongitudeBetween(Double startLat, Double endLat, Double startLong, Double endLong, Pageable pageable);

    List<Attraction> findAllByGugun_IdGugunCodeAndGugun_IdSidoCode(Long gugunCode, Long sidoCode, Pageable pageable);

    Page<Attraction> findAllByTitleContainingAndSido_SidoCode(String keyword, Long sidoCode, Pageable pageable);

    List<Attraction> findAllBySido_SidoCode(Long sidoCode);

    Page<Attraction> findAllByTitleContaining(String title, Pageable pageable);
}
