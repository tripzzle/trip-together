package com.tgd.trip.attraction.domain;

import com.tgd.trip.user.domain.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AttractionLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attractionLikeId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attraction_id")
    private Attraction attraction;

    public AttractionLike(User user) {
        this.user = user;
    }
}
