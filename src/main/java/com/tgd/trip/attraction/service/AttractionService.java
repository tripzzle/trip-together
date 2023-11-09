package com.tgd.trip.attraction.service;

import com.tgd.trip.attraction.domain.Attraction;
import com.tgd.trip.attraction.repository.AttractionRepository;
import com.tgd.trip.global.util.Coordinate;
import com.tgd.trip.global.util.Pair;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttractionService {

    private final AttractionRepository attractionRepository;

    public List<Attraction> getAttractionsFromCenter(Coordinate center, Double height, Double width) {
        Pair<Coordinate> squareCoordinate = center.getSquareCoordinate(height, width);
        Coordinate topLeft = squareCoordinate.first();
        Coordinate bottomRight = squareCoordinate.second();
        System.out.println(topLeft);
        System.out.println(bottomRight);
        List<Attraction> attractions = attractionRepository.findAllByLatitudeBetweenAndLongitudeBetween(topLeft.latitude(), bottomRight.latitude(), topLeft.longitude(), bottomRight.longitude());
        return attractions;
    }

    public List<Attraction> getAttractions(Long gugunCode, Long sidoCode, Pageable pageable) {
        System.out.println(gugunCode + " " + sidoCode);
        return attractionRepository.findAllByGugun_IdGugunCodeAndGugun_IdSidoCode(gugunCode, sidoCode, pageable.previousOrFirst());
    }
}
