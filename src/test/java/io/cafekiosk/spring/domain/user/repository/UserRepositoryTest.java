package io.cafekiosk.spring.domain.user.repository;

import io.cafekiosk.spring.api.user.dto.UserStatus;
import io.cafekiosk.spring.domain.user.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource("classpath:test-application.properties")
@Sql("/sql/user-repository-test-data.sql")
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;
    /*@Test
    void UserRepository_가_제대로_연결되었다() {
        //given
        User user = new User();
        user.setEmail("joo@test.com");
        user.setAddress("Seoul");
        user.setNickname("tester");
        user.setStatus(UserStatus.ACTIVE);
        user.setCertificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");

        //when
        User result = userRepository.save(user);

        //then
        assertThat(result.getId()).isNotNull();
    }*/

    @Test
    void findByIdAndStatus_로_유저_데이터를_찾아올_수_있다() {
        //given & when
        Optional<User> result = userRepository.findByIdAndStatus(1, UserStatus.ACTIVE);

        //then
        assertThat(result.isPresent()).isTrue();
    }

    @Test
    void findByIdAndStatus_는_데이터가_없으면_Optional_empty_를_내려준다() {
        //given & when
        Optional<User> result = userRepository.findByIdAndStatus(1, UserStatus.PENDING);

        //then
        assertThat(result.isEmpty()).isTrue();
    }


    @Test
    void findByEmailAndStatus_로_유저_데이터를_찾아올_수_있다() {
        //given & when
        Optional<User> result = userRepository.findByEmailAndStatus("joo@test.com", UserStatus.ACTIVE);

        //then
        assertThat(result.isPresent()).isTrue();

    }

    @Test
    void findByEmailAndStatus_는_데이터가_없으면_Optional_empty_를_내려준다() {
        //given & when
        Optional<User> result = userRepository.findByEmailAndStatus("joo@test.com", UserStatus.PENDING);

        //then
        assertThat(result.isEmpty()).isTrue();
    }
}