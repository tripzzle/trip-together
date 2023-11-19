package com.tgd.trip.global.dto;

import org.springframework.data.domain.Page;

import java.util.List;

public record PageResponse<T>(List<T> data, PageInfo pageInfo) {

    public PageResponse(List<T> data, Page<T> page) {
        this(data, new PageInfo(page.getNumber() + 1, page.getSize(), page.getTotalElements(), page.getTotalPages()));
    }
}
