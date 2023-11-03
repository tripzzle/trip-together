package com.tgd.trip.attraction.dto;

import com.tgd.trip.attraction.domain.Attraction;
import lombok.Getter;

public class AttractionDto {

    @Getter
    public static class Response {

        private final Long attractionId;
        private final String title;
        private final String overview;
        private final Double latitude;
        private final Double longitude;
        private final String gugunName;
        private final String sidoName;
        private final String address;
        private final String phone;
        private final String imgUrl;
        private final String zipCode;
        private final Integer mLevel;
        private final Integer contentTypeId;

        public Response(Attraction attraction){
            this.attractionId = attraction.getAttractionId();
            this.title = attraction.getTitle();
            this.overview = attraction.getOverview();
            this.latitude = attraction.getLatitude();
            this.longitude = attraction.getLongitude();
            this.gugunName = attraction.getGugun().getName();
            this.sidoName = attraction.getSido().getName();
            this.address = attraction.getAddress();
            this.phone = attraction.getAddress();
            this.imgUrl = attraction.getImgUrl();
            this.zipCode = attraction.getZipCode();
            this.mLevel = attraction.getMlevel();
            this.contentTypeId = attraction.getContentTypeId();
        }
    }
}
