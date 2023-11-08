package com.tgd.trip.user.service;

import com.tgd.trip.user.domain.User;
import com.tgd.trip.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService userService;
    @Mock
    UserRepository userRepository;

    @Test
    public void 유저_생성_테스트() {
        User user = new User();
        user.setName("테스트");
        user.setEmail("test@naver.com");

        when(userRepository.save(any())).thenReturn(user);
        User verifyUser = userService.getVerifyUser(user.getEmail());

        assertEquals(user, verifyUser);
    }
}