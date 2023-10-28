package com.tgd.trip.schedule.domain;

import com.tgd.trip.attraction.domain.Attraction;
import com.tgd.trip.global.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Memo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memoId;
    @ManyToOne
    @JoinColumn(name = "attraction_id")
    private Attraction attraction;
    @ManyToOne
    @JoinColumn(name = "day_id")
    private Day day;
}
