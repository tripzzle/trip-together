package com.tgd.trip.attraction.domain;

import com.tgd.trip.global.BaseEntity;
import com.tgd.trip.user.domain.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AttractionBookmark extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long AttractionBookMarkId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attraction_id")
    private Attraction attraction;

    public AttractionBookmark(Attraction attraction) {
        this.attraction = attraction;
    }
}
