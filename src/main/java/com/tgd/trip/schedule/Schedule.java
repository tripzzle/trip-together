package com.tgd.trip.schedule;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Schedule {

    @Id
    private Long scheduleId;
    private String title;
    private String content;
    private Long like;
    private String imgUrl;
    private boolean viewYn;
}
