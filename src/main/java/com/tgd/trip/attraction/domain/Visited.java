package com.tgd.trip.attraction.domain;

import com.tgd.trip.global.BaseEntity;
import com.tgd.trip.user.domain.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Visited extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long visitedId;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "attraction_id")
    private Attraction attraction;
}
