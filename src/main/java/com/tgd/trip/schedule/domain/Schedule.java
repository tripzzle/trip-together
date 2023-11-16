package com.tgd.trip.schedule.domain;

import com.tgd.trip.global.BaseEntity;
import com.tgd.trip.schedule.dto.ScheduleDto;
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

    public Schedule(ScheduleDto.Post post, String imgUrl) {
        Optional.of(post.title())
                .ifPresent(this::setTitle);
        Optional.of(post.content())
                .ifPresent(this::setContent);
        Optional.of(imgUrl)
                .ifPresent(this::setImgUrl);
        Optional.of(post.viewYn())
                .ifPresent(this::setViewYn);
    }

    public void updateSchedule(ScheduleDto.Patch patch, String imgUrl) {
        Optional.of(patch.title())
                .ifPresent(this::setTitle);
        Optional.of(patch.content())
                .ifPresent(this::setContent);
        Optional.of(imgUrl)
                .ifPresent(this::setImgUrl);
        Optional.of(patch.viewYn())
                .ifPresent(this::setViewYn);
    }

    public void addDays(Day day) {
        if (!this.days.contains(day)) {
            days.add(day);
        }
        day.setSchedule(this);
    }
}
