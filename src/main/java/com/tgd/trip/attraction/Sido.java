package com.tgd.trip.attraction;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Sido {

    @Id
    private Long code;
    private String name;
}
