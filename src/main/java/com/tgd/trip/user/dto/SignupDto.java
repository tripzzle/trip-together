package com.tgd.trip.user.dto;

import com.tgd.trip.user.domain.User;
import lombok.*;

import java.time.LocalDate;

@ToString
@Getter
@Setter
@AllArgsConstructor
public class SignupDto {
    private Long userId;
    private String name;
    private String email;
    private LocalDate birth;
    private String nickName;
    private Boolean sex;

    @Builder
    public SignupDto(User user) {
        this.userId = user.getUserId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.birth = user.getBirth();
        this.nickName = user.getNickName();
        this.sex = user.getSex();
    }
}
