package com.tgd.trip.chatting.domain;

import com.tgd.trip.global.BaseEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ChatRoom extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatRoomId;
    @NotNull
    @Column(length = 50)
    private String title;
}
