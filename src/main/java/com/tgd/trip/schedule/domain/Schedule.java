package com.tgd.trip.schedule.domain;

import com.tgd.trip.global.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Schedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;
    private String title;
    private String content;
    private Long likes;
    private String imgUrl;
    private Boolean viewYn = false;
    @OneToMany(mappedBy = "schedule", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, orphanRemoval = true)
    private final List<Day> days = new ArrayList<>();

    public void addDays(Day day) {
        if (!this.days.contains(day)) {
            days.add(day);
        }
        day.setSchedule(this);
    }

}
