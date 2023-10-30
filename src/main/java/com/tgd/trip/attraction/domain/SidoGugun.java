package com.tgd.trip.attraction.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
public class SidoGugun implements Serializable {

    @Column(name = "gugun_code")
    private Long gugunCode;
    @Column(name = "sido_code")
    private Long sidoCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SidoGugun sidoGugun = (SidoGugun) o;
        return Objects.equals(gugunCode, sidoGugun.gugunCode) &&
                Objects.equals(sidoCode, sidoGugun.sidoCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gugunCode, sidoCode);
    }
}
