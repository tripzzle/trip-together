package com.tgd.trip.user.service;

import com.tgd.trip.global.s3.S3Uploader;
import com.tgd.trip.jwt.JwtTokenProvider;
import com.tgd.trip.user.domain.Role;
import com.tgd.trip.user.domain.User;
import com.tgd.trip.user.dto.SignupDto;
import com.tgd.trip.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
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
}
