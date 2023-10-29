package com.tgd.trip.attraction.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Gugun {

    @Id
    private Long gugunCode;
    private String name;
    @ManyToOne
    @JoinColumn(name = "sido_code")
    private Sido sido;
}
