package com.tgd.trip.attraction.service;

import com.tgd.trip.attraction.domain.Attraction;
import com.tgd.trip.attraction.repository.AttractionRepository;
import com.tgd.trip.global.util.Coordinate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class AttractionServiceTest {

    @InjectMocks
    AttractionService attractionService;

    @Mock
    AttractionRepository attractionRepository;

    @Test
    public void 중심_좌표에서_NKM의_사각형영역에_해당하는_관광지를_가져오는_테스트() {
        // given
        Coordinate center = new Coordinate(37.543902, 126.979745);
        Double height = 5.0;
        Double width = 5.0;

        // when
        List<Attraction> attractions = attractionService.getAttractionsFromCenter(center, height, width);

        // then
        assertThat(attractions.size()).isGreaterThan(0);
    }
}