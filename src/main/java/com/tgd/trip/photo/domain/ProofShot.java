package com.tgd.trip.photo.domain;

import com.tgd.trip.attraction.domain.Visited;
import com.tgd.trip.global.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ProofShot extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long proofShotId;
    @ManyToOne
    @JoinColumn(name = "visited_id")
    private Visited visited;
}
