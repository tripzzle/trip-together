package com.tgd.trip.user.service;

import com.tgd.trip.attraction.domain.Attraction;
import com.tgd.trip.attraction.domain.AttractionBookmark;
import com.tgd.trip.attraction.dto.AttractionDto;
import com.tgd.trip.attraction.repository.AttractionBookmarkRepository;
import com.tgd.trip.global.s3.S3Uploader;
import com.tgd.trip.jwt.JwtTokenProvider;
import com.tgd.trip.schedule.domain.Schedule;
import com.tgd.trip.schedule.domain.ScheduleBookmark;
import com.tgd.trip.schedule.dto.ScheduleDto;
import com.tgd.trip.schedule.repository.ScheduleBookmarkRepository;
import com.tgd.trip.schedule.repository.ScheduleRepository;
import com.tgd.trip.user.domain.Role;
import com.tgd.trip.user.domain.User;
import com.tgd.trip.user.domain.UserStatus;
import com.tgd.trip.user.dto.SignupDto;
import com.tgd.trip.user.dto.UserDto;
import com.tgd.trip.user.mapper.UserMapper;
import com.tgd.trip.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final ScheduleBookmarkRepository scheduleBookmarkRepository;
    private final AttractionBookmarkRepository attractionBookmarkRepository;
    private final S3Uploader s3Uploader;
    private final JwtTokenProvider provider;


    public User getVerifyUser(String email) {
        return userRepository.findByEmail(email).orElseThrow();
    }

    public User getVerifyUser(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    public SignupDto getSignup(Long userId) {
        User user = null;
        SignupDto tempuser = null;
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
            tempuser = new SignupDto(user);
        }

        return tempuser;
    }

    public User getUserInfo(Long userId) {
        User user = null;
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        }
        return user;
    }

    public String postSignup(SignupDto user, MultipartFile file) {
        String newToken = null;
        String imgUrl = "";
        //signupDto로 추가정보 안한 유저 정보 가져옴
        Optional<User> optionalUser = userRepository.findById(user.getUserId());
        if (optionalUser.isPresent()) {
            User oldUser = optionalUser.get();
            oldUser.setNickName(user.getNickName());
            oldUser.setBirth(user.getBirth());
            oldUser.setSex(user.getSex());

            oldUser.getRoles().add(Role.USER.name());
            if(file != null){
                imgUrl = s3Uploader.saveUploadFile(file);
                imgUrl = s3Uploader.getFilePath(imgUrl);
            }else {
                imgUrl = "https://tgd-bucket.s3.ap-northeast-2.amazonaws.com/pxfuel.jpg";
            }
            oldUser.setImgUrl(imgUrl);
            System.out.println(oldUser);

            userRepository.save(oldUser);

            newToken = provider.createToken(oldUser.getUserId(), oldUser.getRoles());
        }

        return newToken;
    }


    public void createUser(User user) {
        userRepository.save(user);
    }


    public List<Schedule> userSchedule(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return null;
    }

    public List<Schedule> userWishSD(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        List<ScheduleBookmark> bookmarks = scheduleBookmarkRepository.findAllByUser(user);
        return bookmarks.stream().map(ScheduleBookmark::getSchedule).collect(Collectors.toList());
    }

    public List<Attraction> userWishAT(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        List<AttractionBookmark> bookmarks = attractionBookmarkRepository.findAllByUser(user);

        return bookmarks.stream().map(AttractionBookmark::getAttraction).collect(Collectors.toList());
    }

    public User userUpdate(User member, UserDto.Patch patch, MultipartFile file) {
        String imgUrl = "";
        
        System.out.println("유저업데이트 서비스 요청" + patch);
        if(file != null){
            imgUrl = s3Uploader.saveUploadFile(file);
            imgUrl = s3Uploader.getFilePath(imgUrl);
        }else {
            imgUrl = patch.imgUrl();
        }
        member.userUpdate(patch, imgUrl);

        System.out.println("유저업데이트 서비스 요청 이후" + member.getNickName());

        userRepository.save(member);

        return member;
    }

    public void userDelete(User member) {
        UUID uuid = UUID.randomUUID();
        member.setName(uuid.toString());
        member.setNickName(uuid.toString());
        member.setEmail(uuid.toString());
        member.setRoles(Collections.emptyList());
        member.setProvider(uuid.toString());
        member.setProviderId(uuid.toString());
        member.setStatus(UserStatus.DELETE);
        member.setImgUrl("");
        userRepository.save(member);
    }

    public List<ScheduleDto.SimpleResponse> findScheduleBookmarkAllByUser(User user) {
        List<ScheduleBookmark> responses = null;

        responses = scheduleBookmarkRepository.findAllByUser(user);

        return userMapper.entityToScheduleResponse(responses);
    }

    public List<AttractionDto.Response> findAttractionBookmarkAllByUser(User user) {
        List<AttractionBookmark> responses = null;

        responses = attractionBookmarkRepository.findAllByUser(user);

        return userMapper.entityToAttractionResponse(responses);
    }

}
