package com.tgd.trip.schedule.domain;

import com.tgd.trip.global.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Day extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dayId;
    private LocalDate day;
    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;
}
