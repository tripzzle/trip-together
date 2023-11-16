package com.tgd.trip.schedule.domain;

import com.tgd.trip.attraction.domain.Attraction;
import com.tgd.trip.global.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class DayAttraction extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dayAttractionId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attraction_id")
    private Attraction attraction;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "day_id")
    private Day day;
    @Column(columnDefinition = "TEXT")
    private String memo;
    private Integer sequence;

    public DayAttraction(Attraction attraction, String memo) {
        this.attraction = attraction;
        Optional.of(memo)
                .ifPresent(this::setMemo);
    }
}
