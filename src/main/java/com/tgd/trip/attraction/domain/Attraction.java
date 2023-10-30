package com.tgd.trip.attraction.domain;

import com.tgd.trip.global.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attraction extends BaseEntity {

    @Id
    private Long attractionId;
    @Column(columnDefinition = "TEXT")
    private String title;
    private String overview;
    private String address;
    private String zipCode;
    private String tel;
    private String imgUrl;
    private Double latitude;
    private Double longitude;
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
}
