package com.tgd.trip.user.domain;

import com.tgd.trip.attraction.domain.AttractionBookmark;
import com.tgd.trip.attraction.repository.AttractionBookmarkRepository;
import com.tgd.trip.global.BaseEntity;
import com.tgd.trip.schedule.domain.ScheduleBookmark;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String password;
    private String name;
    @NotNull
    @Column(unique = true)
    private String email;
    @NotNull
    private Role role;
    private String provider;
    private String providerId;
    @Null
    private String status;
    private LocalDate birth;
    private String nickName;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ScheduleBookmark> scheduleBookmarks = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AttractionBookmark> attractionBookmarks = new ArrayList<>();

    @Builder
    public User(Long userId, String password, String name, String email, Role role, String status, String provider, String providerId, LocalDate birth, String nickName) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
        this.role = role;
        this.status = status;
        this.provider = provider;
        this.providerId = providerId;
        this.birth = birth;
        this.nickName = nickName;
    }

    public void addScheduleBookmark(ScheduleBookmark scheduleBookmark) {
        if (!this.scheduleBookmarks.contains(scheduleBookmark)) {
            scheduleBookmarks.add(scheduleBookmark);
        }
        scheduleBookmark.setUser(this);
    }

    public void addAttractionBookmark(AttractionBookmark attractionBookmark) {
        if (!this.attractionBookmarks.contains(attractionBookmark)) {
            attractionBookmarks.add(attractionBookmark);
        }
        attractionBookmark.setUser(this);
    }
}
