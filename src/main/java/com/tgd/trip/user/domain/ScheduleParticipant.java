package com.tgd.trip.user.domain;

import com.tgd.trip.global.BaseEntity;
import com.tgd.trip.schedule.domain.Schedule;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ScheduleParticipant extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleParticipantId;
    private Boolean isAdmin = false;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;
}
