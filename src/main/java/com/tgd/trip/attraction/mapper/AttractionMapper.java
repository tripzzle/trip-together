package com.tgd.trip.attraction.mapper;

import com.tgd.trip.attraction.domain.Attraction;
import com.tgd.trip.attraction.dto.AttractionDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AttractionMapper {

    public AttractionDto.Response entityToResponse(Attraction attraction) {
        return new AttractionDto.Response(attraction);
    }

    public List<AttractionDto.Response> entityToResponse(Page<Attraction> attractions) {
        return attractions.map(this::entityToResponse).stream().toList();
    }

    public Page<AttractionDto.Response> entityToPageResponse(Page<Attraction> attractions) {
        return attractions.map(this::entityToResponse);
    }
}
