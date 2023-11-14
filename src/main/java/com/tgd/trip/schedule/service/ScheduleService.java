package com.tgd.trip.schedule.service;

import com.tgd.trip.attraction.domain.Attraction;
import com.tgd.trip.attraction.repository.AttractionRepository;
import com.tgd.trip.global.s3.S3Uploader;
import com.tgd.trip.photo.domain.Photo;
import com.tgd.trip.schedule.domain.*;
import com.tgd.trip.schedule.dto.ScheduleDto;
import com.tgd.trip.schedule.repository.*;
import com.tgd.trip.user.domain.User;
import com.tgd.trip.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final AttractionRepository attractionRepository;
    private final DayAttractionRepository dayAttractionRepository;
    private final S3Uploader s3Uploader;
    private final UserService userService;
    private final ScheduleBookmarkRepository scheduleBookmarkRepository;

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

    @Transactional
    public Schedule updateSchedule(Long id, ScheduleDto.Patch patch) {
        // 기존 스케줄 가져오기
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(RuntimeException::new);

        // 받아온 day로 새로운 일자 만들기
        patch.days().forEach(dayDtoPatch -> {
                    // 기존에 존재하는 일자 가져오기
                    Day day = schedule.getDays()
                            .stream()
                            .filter(d -> d.getDate().equals(dayDtoPatch.date()))
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException("해당 일자가 없습니다."));

                    // 갖고있는 dayAttractions의 id로 in절을 delete
                    dayAttractionRepository.deleteAllByIds(day.getDayAttractions().stream().map(DayAttraction::getDayAttractionId).toList());

                    // 수정된 관광지들 insert
                    dayDtoPatch.dayAttractions().forEach(dayAttractionDto -> {
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

    public List<Schedule> getSchedules(String keyword, String sort, Pageable pageable) {
        List<Schedule> schedules = scheduleRepository.findAllByTitleContaining(keyword, PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));
        return schedules;
    }

    @Transactional
    public void createPhoto(Long scheduleId, Long dayId, List<MultipartFile> files) {
        // 스케줄 가져오고 없으면 Exception
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(RuntimeException::new);

        // 가져온 스케줄에 dayId가 있는지 확인하여 Day 객체 가져오기
        Day day = schedule.getDays().stream().filter(d -> d.getDayId().equals(dayId)).findFirst().orElseThrow(RuntimeException::new);

        // S3에 올리려는 파일들 올리고 리스트로 만들어 반환
        files.forEach(file -> {
            String savedFileName = s3Uploader.saveUploadFile(file);
            String filePath = s3Uploader.getFilePath(savedFileName);
            Photo photo = new Photo(filePath);
            day.addPhoto(photo);
        });
    }

    @Transactional
    public void createBookmark(Long scheduleId, Long userId) {
        User findUser = userService.getVerifyUser(userId);
        Schedule findSchedule = scheduleRepository.findById(scheduleId).orElseThrow();
        ScheduleBookmark scheduleBookmark = new ScheduleBookmark(findSchedule);
        findUser.addScheduleBookmark(scheduleBookmark);
        scheduleBookmarkRepository.save(scheduleBookmark);
    }

    @Transactional
    public void deleteBookmark(Long scheduleId, Long userId) {
        User findUser = userService.getVerifyUser(userId);
        Schedule findSchedule = scheduleRepository.findById(scheduleId).orElseThrow();
        scheduleBookmarkRepository.deleteByUserAndSchedule(findUser, findSchedule);
    }
}
