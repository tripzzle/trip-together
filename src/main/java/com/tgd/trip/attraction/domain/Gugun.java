package com.tgd.trip.attraction.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Gugun {

    @EmbeddedId
    private SidoGugun id;
    @NotNull
    @Column(length = 50)
    private String name;
}