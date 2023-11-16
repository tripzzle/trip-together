package com.tgd.trip.attraction.domain;

import com.tgd.trip.global.BaseEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attraction extends BaseEntity {

    @Id
    private Long attractionId;
    @NotNull
    private String title;
    @Column(columnDefinition = "TEXT")
    private String overview;
    @NotNull
    private String address;
    private String zipCode;
    private String tel;
    private String imgUrl;
    @NotNull
    private Double latitude;
    @NotNull
    private Double longitude;
    @NotNull
    private Integer contentTypeId;
    private Integer mlevel;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sido_code", insertable = false, updatable = false)
    private Sido sido;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "gugun_code"),
            @JoinColumn(name = "sido_code")
    })
    private Gugun gugun;
    @OneToMany(mappedBy = "attraction", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<AttractionLike> attractionLikes = new ArrayList<>();

    public void addLike(AttractionLike attractionLike) {
        if (!this.attractionLikes.contains(attractionLike)) {
            attractionLikes.add(attractionLike);
        }
        attractionLike.setAttraction(this);
    }
}
