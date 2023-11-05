package com.tgd.trip.schedule.domain;

import com.tgd.trip.global.BaseEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Day extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dayId;
    @Column(columnDefinition = "DATE")
    @NotNull
    private LocalDate date;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;
    @OneToMany(mappedBy = "day", fetch = FetchType.LAZY)
    private List<DayAttraction> dayAttractions = new ArrayList<>();

    public Day(LocalDate date) {
        this.date = date;
    }

    public void addDayAttraction(DayAttraction dayAttraction) {
        if (!this.dayAttractions.contains(dayAttraction)) {
            dayAttractions.add(dayAttraction);
        }
        dayAttraction.setDay(this);
    }
}
