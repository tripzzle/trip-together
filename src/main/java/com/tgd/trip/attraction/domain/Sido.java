package com.tgd.trip.attraction.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Sido {

    @Id
    private Long sidoCode;
    @NotNull
    @Column(unique = true, length = 50)
    private String name;
}
