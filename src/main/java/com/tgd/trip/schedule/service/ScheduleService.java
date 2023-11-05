package com.tgd.trip.schedule.service;

import com.tgd.trip.attraction.domain.Attraction;
import com.tgd.trip.attraction.repository.AttractionRepository;
import com.tgd.trip.schedule.domain.*;
import com.tgd.trip.schedule.dto.ScheduleDto;
import com.tgd.trip.schedule.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Period;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final DayRepository dayRepository;
    private final AttractionRepository attractionRepository;
    private final DayAttractionRepository dayAttractionRepository;

    public Schedule createSchedule(ScheduleDto.Post post) {
        long dayDiff = Period.between(post.startDate(), post.endDate()).getDays();

        Schedule schedule = new Schedule();
        scheduleRepository.save(schedule);

        Optional<Attraction> byId = attractionRepository.findById(125266L);


        IntStream.iterate(0, i -> i + 1)
                .limit(dayDiff)
                .forEach(day -> {
                            Day saveDay = new Day(post.startDate().plusDays(day));
                            schedule.addDays(saveDay);
                            dayRepository.save(saveDay);

                            DayAttraction dayAttraction = new DayAttraction();
                            dayAttraction.setAttraction(byId.get());
                            saveDay.addDayAttraction(dayAttraction);
                            dayAttractionRepository.save(dayAttraction);

                        }
                );


        return schedule;
    }

    public Schedule getSchedule(Long id) {
        return scheduleRepository.findById(id).get();
    }
}
