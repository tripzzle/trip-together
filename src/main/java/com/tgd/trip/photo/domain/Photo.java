package com.tgd.trip.photo.domain;

import com.tgd.trip.global.BaseEntity;
import com.tgd.trip.schedule.domain.Day;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Photo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long photoId;
    @ManyToOne
    @JoinColumn(name = "day_id")
    private Day day;
    @NotNull
    private String imgUrl;
}
