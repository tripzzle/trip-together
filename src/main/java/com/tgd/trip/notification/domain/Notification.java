package com.tgd.trip.notification.domain;

import com.tgd.trip.global.BaseEntity;
import com.tgd.trip.user.domain.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Notification extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;
    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;
    @NotNull
    @Column(columnDefinition = "TEXT")
    private String content;
    @NotNull
    private Integer type;
    private String contentLink;
}
