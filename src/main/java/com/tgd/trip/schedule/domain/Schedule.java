package com.tgd.trip.schedule.domain;

import com.tgd.trip.global.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Schedule extends BaseEntity {

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, orphanRemoval = true)
    private final List<Day> days = new ArrayList<>();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;
    private String title;
    private String content;
    private Long likes;
    private String imgUrl;
    private Boolean viewYn = false;

    public Schedule(String title, String content, Boolean viewYn) {
        Optional.of(title)
                .ifPresent(this::setTitle);
        Optional.of(content)
                .ifPresent(this::setContent);
        Optional.of(viewYn)
                .ifPresent(this::setViewYn);
    }

    public void addDays(Day day) {
        if (!this.days.contains(day)) {
            days.add(day);
        }
        day.setSchedule(this);
    }
}
