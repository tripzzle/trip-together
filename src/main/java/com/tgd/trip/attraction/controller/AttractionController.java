package com.tgd.trip.attraction.controller;

import com.tgd.trip.attraction.domain.Attraction;
import com.tgd.trip.attraction.dto.AttractionDto;
import com.tgd.trip.attraction.mapper.AttractionMapper;
import com.tgd.trip.attraction.service.AttractionService;
import com.tgd.trip.global.util.Coordinate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/attraction")
@RequiredArgsConstructor
public class AttractionController {

    private final AttractionService attractionService;
    private final AttractionMapper attractionMapper;

    @GetMapping("/all")
    public ResponseEntity<?> getAllAttractions(Coordinate coordinate,
                                               @RequestParam(name = "height", required = false) Double height,
                                               @RequestParam(name = "width", required = false) Double width) {
        System.out.println(coordinate.longitude() + " " + coordinate.latitude());
        List<Attraction> attractions = attractionService.getAttractionsFromCenter(coordinate, height, width);
        List<AttractionDto.Response> responses = attractionMapper.entityToResponse(attractions);
        return ResponseEntity.ok(responses);
    }

    @GetMapping()
    public ResponseEntity<?> getAttractions(@RequestParam(name = "sido_code", required = false) Long sidoCode,
                                            @RequestParam(name = "gugun_code", required = false) Long gugunCode,
                                            @PageableDefault Pageable pageable) {
        List<Attraction> attractions = attractionService.getAttractions(gugunCode, sidoCode, pageable);
        List<AttractionDto.Response> responses = attractionMapper.entityToResponse(attractions);
        return ResponseEntity.ok(responses);
    }
}
