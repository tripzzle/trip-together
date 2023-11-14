package com.tgd.trip.user.domain;

import com.tgd.trip.global.BaseEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String password;
    private String name;
    @NotNull
    @Column(unique = true)
    private String email;
    @NotNull
    private Role role;
    private String provider;
    private String providerId;
    @Null
    private UserStatus status=UserStatus.ACTIVE;
    private LocalDate birth;
    private String nickName;
    private Boolean sex;

    @Builder
    public User(Long userId, String password, String name, String email, Role role, String provider, String providerId, LocalDate birth, String nickName,  Boolean sex){
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
        this.birth = birth;
        this.nickName = nickName;
        this.sex = sex;
    }
}
