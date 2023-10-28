package com.tgd.trip.schedule.domain;

import com.tgd.trip.global.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Schedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;
    private String title;
    private String content;
    private Long likes;
    private String imgUrl;
    private Boolean viewYn;
}
