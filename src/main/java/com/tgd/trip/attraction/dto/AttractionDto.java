package com.tgd.trip.attraction.dto;

import com.tgd.trip.attraction.domain.Attraction;

public class AttractionDto {

    public record Response(Long attractionId, String title, String overview, Double latitude, Double longitude,
                           Long likeCount, Long wishCount, String gugunName, String sidoName, String address,
                           String phone, String imgUrl, String zipCode, Integer mLevel, Integer contentTypeId) {
        public Response(Attraction attraction) {
            this(attraction.getAttractionId(), attraction.getTitle(), attraction.getOverview(), attraction.getLatitude(),
                    attraction.getLongitude(), attraction.getLikeCount(), attraction.getWishCount(), attraction.getGugun().getName(),
                    attraction.getSido().getName(), attraction.getAddress(), attraction.getTel(), attraction.getImgUrl(), attraction.getZipCode(),
                    attraction.getMlevel(), attraction.getContentTypeId());
        }
    }
}
