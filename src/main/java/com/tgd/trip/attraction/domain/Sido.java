package com.tgd.trip.attraction.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Sido {

    @Id
    private Long sidoCode;
    private String name;
}
