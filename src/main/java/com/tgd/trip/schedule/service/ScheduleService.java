package com.tgd.trip.schedule.service;

import com.tgd.trip.attraction.domain.Attraction;
import com.tgd.trip.attraction.repository.AttractionRepository;
import com.tgd.trip.schedule.domain.Day;
import com.tgd.trip.schedule.domain.DayAttraction;
import com.tgd.trip.schedule.domain.Schedule;
import com.tgd.trip.schedule.dto.ScheduleDto;
import com.tgd.trip.schedule.repository.DayAttractionRepository;
import com.tgd.trip.schedule.repository.DayRepository;
import com.tgd.trip.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final AttractionRepository attractionRepository;
    private final DayAttractionRepository dayAttractionRepository;

    @Transactional
    public Schedule createSchedule(ScheduleDto.Post post) {
        Schedule schedule = new Schedule(post.title(), post.content());

        post.days().forEach(dayDtoPost -> {
                    Day day = new Day(dayDtoPost.date());
                    schedule.addDays(day);
                    dayDtoPost.dayAttractions().forEach(dayAttractionDto -> {
                                Attraction attraction = attractionRepository.findById(dayAttractionDto.attractionId())
                                        .orElseThrow(RuntimeException::new);
                                DayAttraction dayAttraction = new DayAttraction(attraction, dayAttractionDto.memo());
                                day.addDayAttraction(dayAttraction);
                                dayAttractionRepository.save(dayAttraction);
                            }
                    );
                }
        );
        scheduleRepository.save(schedule);

        return schedule;
    }

    public Schedule getSchedule(Long id) {
        return scheduleRepository.findById(id).get();
    }

    public void deleteSchedule(Long id) {
        Schedule schedule = getSchedule(id);
        scheduleRepository.delete(schedule);
    }
}
