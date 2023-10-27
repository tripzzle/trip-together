package com.tgd.trip.user;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String password;
    private String name;
    private String email;
    private String role;
    private String status;
    private String provider;
    private String providerId;
    private LocalDate birth;
}
