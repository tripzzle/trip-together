package com.tgd.trip.global.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CoordinateTest {

    @Test
    public void 두_좌표의_거리를_찾는_테스트() {
        // given
        Coordinate first = new Coordinate(37.529704, 126.957173);
        Coordinate second = new Coordinate(37.523995, 127.025066);

        // when
        double degree = Coordinate.getHaversineDistance(first, second);

        // then
        assertThat(degree).isGreaterThan(6);
    }

    @Test
    public void 중심_좌표에서_N_KM의_좌표를_찾는_테스트() {
        // given
        double height = 3.0;
        double width = 3.0;
        Coordinate center = new Coordinate(37.551088, 126.991929);

        // when
        Pair<Coordinate> squareCoordinate = center.getSquareCoordinate(height, width);
        Coordinate first = squareCoordinate.first();
        Coordinate second = squareCoordinate.second();

        // then
        // 한 변의 길이가 6인 정사각형의 대각선 길이는 11.04
        System.out.println(Coordinate.getHaversineDistance(first, second));
        assertThat(Coordinate.getHaversineDistance(first, second)).isGreaterThan(11);
    }
}