package com.tgd.trip.attraction.mapper;

import com.tgd.trip.attraction.domain.Attraction;
import com.tgd.trip.attraction.dto.AttractionDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AttractionMapper {

    public List<AttractionDto.Response> entityToResponse(List<Attraction> attractions) {
        return attractions.stream().map(AttractionDto.Response::new).collect(Collectors.toList());
    }
}
