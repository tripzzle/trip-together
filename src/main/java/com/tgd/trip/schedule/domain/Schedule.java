package com.tgd.trip.schedule.domain;

import com.tgd.trip.global.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;

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
    @OneToMany(mappedBy = "schedule")
    private List<Day> days;

    public void addDays(Day day) {
        if (!this.days.contains(day)) {
            days.add(day);
        }
        day.setSchedule(this);
    }

}
