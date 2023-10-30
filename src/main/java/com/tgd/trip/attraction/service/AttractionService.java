package com.tgd.trip.attraction.service;

import com.tgd.trip.attraction.domain.Attraction;
import com.tgd.trip.attraction.repository.AttractionRepository;
import com.tgd.trip.global.util.Coordinate;
import com.tgd.trip.global.util.Pair;
import lombok.RequiredArgsConstructor;
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
        List<Attraction> attractions = attractionRepository.findAllByLatitudeBetweenAndLongitudeBetween(topLeft.latitude(), bottomRight.latitude(), topLeft.longitude(), bottomRight.longitude());
        return attractions;
    }
}
