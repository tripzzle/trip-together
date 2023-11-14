package com.tgd.trip.schedule.repository;

import com.tgd.trip.schedule.domain.DayAttraction;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DayAttractionRepository extends JpaRepository<DayAttraction, Long> {

    @Modifying
    @Query("delete from DayAttraction da where da.dayAttractionId in :ids")
    void deleteAllByIds(@Param("ids") List<Long> ids);
}
