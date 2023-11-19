package com.tgd.trip.attraction.service;

import com.tgd.trip.attraction.domain.Attraction;
import com.tgd.trip.attraction.repository.AttractionRepository;
import com.tgd.trip.global.util.Coordinate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class AttractionServiceTest {

    @InjectMocks
    AttractionService attractionService;

    @Mock
    AttractionRepository attractionRepository;

    @Test
    public void 중심_좌표에서_NKM의_사각형영역에_해당하는_관광지를_가져오는_테스트() {
        // given
        Coordinate center = new Coordinate(37.543902, 127.979745);
        Double height = 5.0;
        Double width = 5.0;
        Attraction attraction1 = new Attraction();
        Attraction attraction2 = new Attraction();
        List<Attraction> expectedAttractions = Arrays.asList(attraction1, attraction2);
        PageRequest of = PageRequest.of(1, 10);

        // when
        when(attractionRepository.findAllByLatitudeBetweenAndLongitudeBetween(anyDouble(), anyDouble(), anyDouble(), anyDouble(), any()).getContent())
                .thenReturn(expectedAttractions);
        Page<Attraction> attractions = attractionService.getAttractionsFromCenter(center, height, width, of);

        // then
        assertEquals(expectedAttractions, attractions);
        verify(attractionRepository, times(1))
                .findAllByLatitudeBetweenAndLongitudeBetween(anyDouble(), anyDouble(), anyDouble(), anyDouble(), any());
    }
}