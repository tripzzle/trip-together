package com.tgd.trip.schedule.service;

import com.tgd.trip.global.exception.CustomException;
import com.tgd.trip.global.exception.ErrorCode;
import com.tgd.trip.global.s3.S3Uploader;
import com.tgd.trip.photo.domain.Photo;
import com.tgd.trip.schedule.domain.*;
import com.tgd.trip.schedule.dto.CommentDto;
import com.tgd.trip.schedule.dto.ScheduleDto;
import com.tgd.trip.schedule.repository.CommentRepository;
import com.tgd.trip.schedule.repository.ScheduleBookmarkRepository;
import com.tgd.trip.schedule.repository.ScheduleLikeRepository;
import com.tgd.trip.schedule.repository.ScheduleRepository;
import com.tgd.trip.security.SecurityUser;
import com.tgd.trip.user.domain.User;
import com.tgd.trip.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleBookmarkRepository scheduleBookmarkRepository;
    private final ScheduleLikeRepository scheduleLikeRepository;
    private final CommentRepository commentRepository;
    private final DayAttractionService dayAttractionService;
    private final S3Uploader s3Uploader;
    private final UserService userService;

    @Transactional
    public Schedule createSchedule(ScheduleDto.Post post, SecurityUser securityUser, MultipartFile file) {
        // 유저 존재하는지 판별
        User user = userService.getVerifyUser(securityUser.getMember().getUserId());

        // 일정 이름, 내용을 가지는 객체 생성
        String imgUrl = "";

        // 일정용 이미지 업로드
        if (file != null) {
            imgUrl = s3Uploader.saveUploadFile(file);
            imgUrl = s3Uploader.getFilePath(imgUrl);
            log.debug("filePath : " + imgUrl);
        }

        Schedule schedule = new Schedule(post, imgUrl);

        post.days().forEach(dayDtoPost -> {
            // 새로운 일자 객체 생성 및 일정 객체와 연결
            Day day = new Day(dayDtoPost.date());
            schedule.addDays(day);

            dayDtoPost.dayAttractions()
                    .forEach(dayAttractionDto -> dayAttractionService.update(dayAttractionDto, day));
        });
        // 유저와 연결
        user.addSchedule(schedule);
        scheduleRepository.save(schedule);

        return schedule;
    }

    @Transactional
    public Schedule updateSchedule(SecurityUser securityUser, Long scheduleId, ScheduleDto.Patch patch, MultipartFile file) {
        // 기존 스케줄 가져오기
        Schedule schedule = getSchedule(scheduleId);

        if (!securityUser.getMember().getEmail().equals(schedule.getUser().getEmail())) {
            throw new CustomException(ErrorCode.DIFFERENT_USER);
        }

        // 일정용 이미지 업로드
        String imgUrl = "";
        if (file != null) {
            imgUrl = s3Uploader.saveUploadFile(file);
            imgUrl = s3Uploader.getFilePath(imgUrl);
            log.debug("filePath : " + imgUrl);
        }

        // 일정 객체 업데이트
        schedule.updateSchedule(patch, imgUrl);

        // 받아온 day로 새로운 일자 만들기
        patch.days().forEach(dayDtoPatch -> {
            // 기존에 존재하는 일자 가져오기
            Day day = schedule.getDays()
                    .stream()
                    .filter(d -> d.getDate().equals(dayDtoPatch.date()))
                    .findFirst()
                    .orElseThrow(() -> new CustomException(ErrorCode.DAY_NOT_FOUND));

            // 갖고있는 dayAttractions의 id로 in절을 delete
            List<Long> dayAttractionIds = day.getDayAttractions()
                    .stream()
                    .map(DayAttraction::getDayAttractionId)
                    .toList();
            dayAttractionService.deleteAll(dayAttractionIds);

            // 수정된 관광지들 insert
            dayDtoPatch.dayAttractions()
                    .forEach(dayAttractionDto -> dayAttractionService.update(dayAttractionDto, day));
        });
        scheduleRepository.save(schedule);

        return schedule;
    }

    public Schedule getSchedule(Long id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.SCHEDULE_NOT_FOUND));
    }

    @Transactional
    public void deleteSchedule(Long id) {
        Schedule schedule = getSchedule(id);
        scheduleRepository.delete(schedule);
    }

    public Page<Schedule> getSchedules(String keyword, String sort, Pageable pageable) {
        Page<Schedule> schedules = scheduleRepository.findAllByTitleContainingAndViewYnNot(keyword, true, PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize()));
        return schedules;
    }

    @Transactional
    public void createPhoto(Long scheduleId, Long dayId, List<MultipartFile> files) {
        // 스케줄 가져오고 없으면 Exception
        Schedule schedule = getSchedule(scheduleId);

        // 가져온 스케줄에 dayId가 있는지 확인하여 Day 객체 가져오기
        Day day = schedule.getDays().stream()
                .filter(d -> d.getDayId().equals(dayId))
                .findFirst()
                .orElseThrow(() -> new CustomException(ErrorCode.DAY_NOT_FOUND));

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
        Schedule findSchedule = getSchedule(scheduleId);
        ScheduleBookmark scheduleBookmark = new ScheduleBookmark(findSchedule);
        findUser.addScheduleBookmark(scheduleBookmark);
        scheduleBookmarkRepository.save(scheduleBookmark);
    }

    @Transactional
    public void deleteBookmark(Long scheduleId, Long userId) {
        User findUser = userService.getVerifyUser(userId);
        Schedule findSchedule = getSchedule(scheduleId);
        scheduleBookmarkRepository.deleteByUserAndSchedule(findUser, findSchedule);
    }

    @Transactional
    public void createLike(Long scheduleId, Long userId) {
        User findUser = userService.getVerifyUser(userId);
        Schedule findSchedule = getSchedule(scheduleId);

        // 오늘의 시작 시간과 끝 시간을 구하기
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);

        // 찾은 범위를 바탕으로 해당 스케줄에 해당 유저가 좋아요를 했는지 검증
        if (scheduleLikeRepository.existsByCreatedAtBetweenAndUserAndSchedule(startOfDay, endOfDay, findUser, findSchedule)) {
            throw new CustomException(ErrorCode.TOO_MANY_LIKES);
        }

        // 좋아요 생성
        ScheduleLike scheduleLike = new ScheduleLike(findUser);
        findSchedule.addLike(scheduleLike);
        scheduleLikeRepository.save(scheduleLike);
    }

    @Transactional
    public void createComment(SecurityUser securityUser, Long scheduleId, CommentDto.Post post) {
        // 유저 가져오기
        User user = securityUser.getMember();

        // 스케줄 예외 처리
        Schedule findSchedule = getSchedule(scheduleId);

        // 댓글 생성
        Comment comment = new Comment(user, post.content());
        findSchedule.addComments(comment);
        commentRepository.save(comment);
    }

    @Transactional
    public void updateComment(Long scheduleId, Long commentId, CommentDto.Patch patch) {
        // 스케줄 예외 처리
        getSchedule(scheduleId);

        // 유저 예외 처리
        User findUser = userService.getVerifyUser(patch.userId());

        // 댓글 가져오기
        Comment findComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

        // 유저 검증
        if (!findUser.getEmail().equals(findComment.getUser().getEmail())) {
            throw new CustomException(ErrorCode.DIFFERENT_USER);
        }

        // 댓글 수정
        findComment.update(patch);
        commentRepository.save(findComment);
    }

    @Transactional
    public void deleteComment(SecurityUser securityUser, Long scheduleId, Long commentId) {
        User user = securityUser.getMember();

        // 스케줄 예외 처리
        getSchedule(scheduleId);

        // 댓글 가져오기
        Comment findComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

        if (!findComment.getUser().getEmail().equals(user.getEmail())) {
            throw new CustomException(ErrorCode.DIFFERENT_USER);
        }

        commentRepository.delete(findComment);
    }
}
