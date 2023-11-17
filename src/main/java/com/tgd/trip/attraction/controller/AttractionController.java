package com.tgd.trip.attraction.controller;

import com.tgd.trip.attraction.domain.Attraction;
import com.tgd.trip.attraction.dto.AttractionDto;
import com.tgd.trip.attraction.mapper.AttractionMapper;
import com.tgd.trip.attraction.service.AttractionService;
import com.tgd.trip.global.dto.PageResponse;
import com.tgd.trip.global.util.Coordinate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
                                               @RequestParam(name = "width", required = false) Double width,
                                               @PageableDefault(page = 1) Pageable pageable) {
        Page<Attraction> attractions = attractionService.getAttractionsFromCenter(coordinate, height, width, pageable);
        Page<AttractionDto.Response> responsePage = attractionMapper.entityToPageResponse(attractions);
        List<AttractionDto.Response> responses = attractionMapper.entityToResponse(attractions);
        return ResponseEntity.ok(new PageResponse<>(responses, responsePage));
    }

    @GetMapping
    public ResponseEntity<PageResponse<AttractionDto.Response>> getAttractions(@RequestParam String keyword,
                                                                               @RequestParam(name = "sidoCode", required = false) Long sidoCode,
                                                                               @PageableDefault(page = 1) Pageable pageable) {
        Page<Attraction> attractions = attractionService.getAttractions(keyword, sidoCode, pageable);
        Page<AttractionDto.Response> responsePage = attractionMapper.entityToPageResponse(attractions);
        List<AttractionDto.Response> responses = attractionMapper.entityToResponse(attractions);
        return ResponseEntity.ok(new PageResponse<>(responses, responsePage));
    }

    @PostMapping(value = "{attraction-id}/wish")
    public ResponseEntity<?> createAttractionBookmark(@PathVariable("attraction-id") Long attractionId,
                                                      @RequestParam("userId") Long userId) {
        attractionService.createBookmark(attractionId, userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping(value = "{attraction-id}/wish")
    public ResponseEntity<?> deleteAttractionBookmark(@PathVariable("attraction-id") Long attractionId,
                                                      @RequestParam("userId") Long userId) {
        attractionService.deleteBookmark(attractionId, userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping(value = "{attraction-id}/like")
    public ResponseEntity<?> createAttractionLike(@PathVariable("attraction-id") Long attractionId,
                                                  @RequestParam("userId") Long userId) {
        attractionService.createLike(attractionId, userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping(value = "{attraction-id}/like")
    public ResponseEntity<?> deleteAttractionLike(@PathVariable("attraction-id") Long attractionId,
                                                  @RequestParam("userId") Long userId) {
        attractionService.deleteLike(attractionId, userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
