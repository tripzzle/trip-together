package com.tgd.trip.attraction;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Attraction {

    @Id
    private Long id;
    @Column(columnDefinition = "TEXT")
    private String title;
    private String overview;
    private String address;
    private String zipCode;
    private String tel;
    private String imgUrl;
    private Double latitude;
    private Double longitude;
}
