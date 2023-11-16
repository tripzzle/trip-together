package com.tgd.trip.schedule.service;

import com.tgd.trip.attraction.domain.Attraction;
import com.tgd.trip.attraction.repository.AttractionRepository;
import com.tgd.trip.global.exception.CustomException;
import com.tgd.trip.global.exception.ErrorCode;
import com.tgd.trip.schedule.domain.Day;
import com.tgd.trip.schedule.domain.DayAttraction;
import com.tgd.trip.schedule.dto.DayAttractionDto;
import com.tgd.trip.schedule.repository.DayAttractionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class DayAttractionService {

    private final DayAttractionRepository dayAttractionRepository;
    private final AttractionRepository attractionRepository;

    @Transactional
    public void update(DayAttractionDto dayAttractionDto, Day day) {
        // 관광지 정보 찾아오기
        Attraction attraction = attractionRepository.findById(dayAttractionDto.attractionId())
                .orElseThrow(() -> new CustomException(ErrorCode.ATTRACTION_NOT_FOUND));
        // 관광지 일자 중간 테이블 객체 생성 및 일자와 연결
        DayAttraction dayAttraction = new DayAttraction(attraction, dayAttractionDto.memo());
        day.addDayAttraction(dayAttraction);
        dayAttractionRepository.save(dayAttraction);
    }

    @Transactional
    public void deleteAll(List<Long> dayAttractionIds) {
        dayAttractionRepository.deleteAllByIds(dayAttractionIds);
    }
}
