package com.tgd.trip.user.repository;

import com.tgd.trip.user.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void 유저_생성_및_조회_테스트() {
        User user = new User();
        user.setName("test user");
        user.setEmail("test@naver.com");
        entityManager.persist(user);
        entityManager.flush();

        User savedUser = userRepository.save(user);
        Optional<User> findUser = userRepository.findByEmail(user.getEmail());

        assertThat(findUser.isPresent()).isTrue();
        assertThat(findUser.get().getName()).isEqualTo(savedUser.getName());
        assertThat(findUser.get().getEmail()).isEqualTo(savedUser.getEmail());
    }
}