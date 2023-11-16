package com.tgd.trip.attraction.repository;

import com.tgd.trip.attraction.domain.Attraction;
import com.tgd.trip.global.util.Coordinate;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.greaterThan;

//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AttractionRepositoryTest {

//    @Autowired
    private AttractionRepository attractionRepository;

//    @Test
//    public void 서울시_도봉구_시구군_코드로_관광지를_받아오는_테스트() {
//        // given
//        List<Attraction> attractions = attractionRepository.findAllByGugun_IdGugunCodeAndGugun_IdSidoCode(10L, 1L);
//        System.out.println(attractions);
//        // then
//        assertThat(greaterThan(0).matches(attractions.size()));
//    }
//
//    @Test
//    public void 강원도_시도_코드로_관광지를_받아오는_테스트() {
//        // given
//        List<Attraction> attractions = attractionRepository.findAllBySido_SidoCode(32L);
//        // then
//        assertThat(greaterThan(0).matches(attractions.size()));
//    }
//
//    @Test
//    @DisplayName("")
//    public void A와_B_좌표_사이에_있는_영역에_있는_관광지를_가져오는_테스트() {
//        // given
//        Coordinate topLeft = new Coordinate(37.533540, 126.971115);
//        Coordinate bottomRight = new Coordinate(37.537388, 127.013507);
//        // when
//        List<Attraction> attractions = attractionRepository.findAllByLatitudeBetweenAndLongitudeBetween(topLeft.latitude(), bottomRight.latitude(), topLeft.longitude(), bottomRight.longitude());
//        // then
//        assertThat(greaterThan(0).matches(attractions.size()));
//    }
}