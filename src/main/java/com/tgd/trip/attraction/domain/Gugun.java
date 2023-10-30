package com.tgd.trip.attraction.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Gugun {

    @EmbeddedId
    private SidoGugun id;
    private String name;
}