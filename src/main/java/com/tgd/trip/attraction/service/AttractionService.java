package com.tgd.trip.attraction.service;

import com.tgd.trip.attraction.domain.*;
import com.tgd.trip.attraction.repository.*;
import com.tgd.trip.global.exception.CustomException;
import com.tgd.trip.global.exception.ErrorCode;
import com.tgd.trip.global.util.Coordinate;
import com.tgd.trip.global.util.Pair;
import com.tgd.trip.user.domain.User;
import com.tgd.trip.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttractionService {

    private final AttractionRepository attractionRepository;
    private final AttractionBookmarkRepository attractionBookmarkRepository;
    private final AttractionLikeRepository attractionLikeRepository;
    private final UserService userService;

    public Page<Attraction> getAttractionsFromCenter(Coordinate center, Double height, Double width, Pageable pageable) {
        Pair<Coordinate> squareCoordinate = center.getSquareCoordinate(height, width);
        Coordinate topLeft = squareCoordinate.first();
        Coordinate bottomRight = squareCoordinate.second();
        log.debug("찾고자 하는 상단 좌표 : " + topLeft + ", 하단 좌표" + bottomRight);
        Page<Attraction> attractions = attractionRepository.findAllByLatitudeBetweenAndLongitudeBetween(topLeft.latitude(), bottomRight.latitude(), topLeft.longitude(), bottomRight.longitude(), pageable);
        return attractions;
    }

    public Page<Attraction> getAttractions(String keywowrd, Long sidoCode, Pageable pageable) {
        if (sidoCode == null) {
            return attractionRepository.findAllByTitleContaining(keywowrd, pageable);
        }
        return attractionRepository.findAllByTitleContainingAndSido_SidoCode(keywowrd, sidoCode, PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize()));
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

    @Transactional
    public void createLike(Long attractionId, Long userId) {
        User findUser = userService.getVerifyUser(userId);
        Attraction findAttraction = getAttraction(attractionId);

        // 유저가 해당 관광지를 좋아요 했다면 더 이상 좋아요 불가능
        if (attractionLikeRepository.existsByUserAndAttraction(findUser, findAttraction)) {
            throw new CustomException(ErrorCode.TOO_MANY_LIKES);
        }

        AttractionLike attractionLike = new AttractionLike(findUser);
        findAttraction.addLike(attractionLike);
        attractionLikeRepository.save(attractionLike);
    }

    @Transactional
    public void deleteLike(Long attractionId, Long userId) {
        User findUser = userService.getVerifyUser(userId);
        Attraction findAttraction = getAttraction(attractionId);
        attractionLikeRepository.deleteByUserAndAttraction(findUser, findAttraction);
    }
}
