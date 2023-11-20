package com.tgd.trip.schedule.domain;

import com.tgd.trip.global.BaseEntity;
import com.tgd.trip.schedule.dto.ScheduleDto;
import com.tgd.trip.user.domain.User;
import lombok.*;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.*;

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
    private String imgUrl;
    private Boolean viewYn = false;
    @Formula("(select count(*) from schedule_like sl where sl.schedule_id = schedule_id)")
    private Long likeCount = 0L;
    @Formula("(select count(*) from schedule_bookmark sb where sb.schedule_id = schedule_id)")
    private Long wishCount = 0L;
    @OneToMany(mappedBy = "schedule", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ScheduleLike> scheduleLikes = new ArrayList<>();
    @OneToMany(mappedBy = "schedule", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, orphanRemoval = true)
    private final List<Day> days = new ArrayList<>();
    @OneToMany(mappedBy = "schedule", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, orphanRemoval = true)
    private final List<Comment> comments = new ArrayList<>();
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

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

    public void addLike(ScheduleLike scheduleLike) {
        if (!this.scheduleLikes.contains(scheduleLike)) {
            scheduleLikes.add(scheduleLike);
        }
        scheduleLike.setSchedule(this);
    }

    public void addComments(Comment comment) {
        if (!this.comments.contains(comment)) {
            comments.add(comment);
        }
        comment.setSchedule(this);
    }
}
