package com.tgd.trip.attraction.service;

import com.tgd.trip.attraction.domain.Attraction;
import com.tgd.trip.attraction.domain.AttractionBookmark;
import com.tgd.trip.attraction.repository.AttractionBookmarkRepository;
import com.tgd.trip.attraction.repository.AttractionRepository;
import com.tgd.trip.global.exception.CustomException;
import com.tgd.trip.global.exception.ErrorCode;
import com.tgd.trip.global.util.Coordinate;
import com.tgd.trip.global.util.Pair;
import com.tgd.trip.user.domain.User;
import com.tgd.trip.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttractionService {

    private final AttractionRepository attractionRepository;
    private final AttractionBookmarkRepository attractionBookmarkRepository;
    private final UserService userService;

    public List<Attraction> getAttractionsFromCenter(Coordinate center, Double height, Double width) {
        Pair<Coordinate> squareCoordinate = center.getSquareCoordinate(height, width);
        Coordinate topLeft = squareCoordinate.first();
        Coordinate bottomRight = squareCoordinate.second();
        log.debug("찾고자 하는 상단 좌표 : " + topLeft + ", 하단 좌표" + bottomRight);
        List<Attraction> attractions = attractionRepository.findAllByLatitudeBetweenAndLongitudeBetween(topLeft.latitude(), bottomRight.latitude(), topLeft.longitude(), bottomRight.longitude());
        return attractions;
    }

    public List<Attraction> getAttractions(Long gugunCode, Long sidoCode, Pageable pageable) {
        log.debug("구군 코드 : " + gugunCode + ", 시도 코드 :" + sidoCode);
        return attractionRepository.findAllByGugun_IdGugunCodeAndGugun_IdSidoCode(gugunCode, sidoCode, pageable.previousOrFirst());
    }

    public Attraction getAttraction(Long attractionId) {
        return attractionRepository.findById(attractionId)
                .orElseThrow(() -> new CustomException(ErrorCode.ATTRACTION_NOT_FOUND));
    }

    @Transactional
    public void createBookmark(Long attractionId, Long userId) {
        User findUser = userService.getVerifyUser(userId);
        Attraction attraction = getAttraction(attractionId);
        AttractionBookmark attractionBookmark = new AttractionBookmark(attraction);
        findUser.addAttractionBookmark(attractionBookmark);
        attractionBookmarkRepository.save(attractionBookmark);
    }

    @Transactional
    public void deleteBookmark(Long attractionId, Long userId) {
        User findUser = userService.getVerifyUser(userId);
        Attraction findAttraction = getAttraction(attractionId);
        attractionBookmarkRepository.deleteByUserAndAttraction(findUser, findAttraction);
    }
}
