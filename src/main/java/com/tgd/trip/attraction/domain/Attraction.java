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
    @ManyToOne
    @JoinColumn(name = "sido_code")
    private Sido sido;
    @ManyToOne
    @JoinColumn(name = "gugun_code")
    private Gugun gugun;
}
