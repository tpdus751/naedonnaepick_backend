package com.naedonnaepick.backend.restaurant.service;

import com.naedonnaepick.backend.restaurant.db.RestaurantRepository;
import com.naedonnaepick.backend.restaurant.dto.RestaurantWithDistanceDTO;
import com.naedonnaepick.backend.restaurant.entity.RestaurantEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Override
    public Optional<RestaurantEntity> findById(int id) {
        return restaurantRepository.findById(id);
    }

    @Override
    public Page<RestaurantWithDistanceDTO> findNearbyRestaurants(BigDecimal lat, BigDecimal lng, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return restaurantRepository.findAllRestaurantsWithDistance(lat, lng, pageable);
    }


    // searchRestaurants - 기존의 general 키워드 검색과 매핑됨
    @Override
    public Page<RestaurantEntity> searchRestaurants(String searchText, BigDecimal lat, BigDecimal lng, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<RestaurantEntity> searchResultPage = restaurantRepository.searchByKeyword(searchText, pageable);

        List<RestaurantEntity> contentWithDistance = searchResultPage.getContent().stream()
                .peek(r -> {
                    if (r.getLatitude() != null && r.getLongitude() != null) {
                        r.setDistance(calculateDistance(
                                lat.doubleValue(), lng.doubleValue(),
                                r.getLatitude().doubleValue(), r.getLongitude().doubleValue()));
                    } else {
                        // 위도/경도 없으면 거리 알 수 없음으로 처리하고 목록 뒤로 보내기 위해 999999.0 할당
                        r.setDistance(999999.0); // Double.MAX_VALUE 대신 999999.0 사용
                        System.err.println("경고: 검색된 레스토랑 번호 " + r.getRestaurantNo() + "에 위도/경도 정보가 없습니다.");
                    }
                })
                .sorted(Comparator.comparing(RestaurantEntity::getDistance)) // 거리를 기준으로 정렬
                .collect(Collectors.toList());

        return new PageImpl<>(contentWithDistance, pageable, searchResultPage.getTotalElements());
    }

    // searchRestaurantsByTagAndDistrict - 기존의 tag 검색과 매핑됨
    @Override
    public Page<RestaurantEntity> searchRestaurantsByTagAndDistrict(String tag, String district, BigDecimal lat, BigDecimal lng, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<RestaurantEntity> searchResultPage = restaurantRepository.searchByTagWithDistrict(tag, district, pageable);

        List<RestaurantEntity> contentWithDistance = searchResultPage.getContent().stream()
                .peek(r -> {
                    if (r.getLatitude() != null && r.getLongitude() != null) {
                        r.setDistance(calculateDistance(
                                lat.doubleValue(), lng.doubleValue(),
                                r.getLatitude().doubleValue(), r.getLongitude().doubleValue()));
                    } else {
                        // 위도/경도 없으면 거리 알 수 없음으로 처리하고 목록 뒤로 보내기 위해 999999.0 할당
                        r.setDistance(999999.0); // Double.MAX_VALUE 대신 999999.0 사용
                        System.err.println("경고: 태그/지역 검색된 레스토랑 번호 " + r.getRestaurantNo() + "에 위도/경도 정보가 없습니다.");
                    }
                })
                .sorted(Comparator.comparing(RestaurantEntity::getDistance)) // 거리를 기준으로 정렬
                .collect(Collectors.toList());

        return new PageImpl<>(contentWithDistance, pageable, searchResultPage.getTotalElements());
    }

    @Override
    public List<RestaurantWithDistanceDTO> getRecommendedRestaurants(String location, int minPrice, int maxPrice, BigDecimal lat, BigDecimal lng) {
        return restaurantRepository.findRestaurantsByLocationPriceAndDistance(location, minPrice, maxPrice, lat, lng);
    }

    public Page<RestaurantWithDistanceDTO> getRecommendedRestaurants(
            String location, int minPrice, int maxPrice, BigDecimal lat, BigDecimal lng, Pageable pageable) {
        return restaurantRepository.findRestaurantsByLocationPriceAndDistancePageable(
                location, minPrice, maxPrice, lat, lng, pageable
        );
    }



    @Override
    public Page<RestaurantEntity> getRestaurantsBySearchText(String searchText, Pageable pageable) {
        return restaurantRepository.findRestaurantsBySearchText(searchText, pageable);
    }

    // --- `RestaurantAPIController`의 매핑 이름에 맞춰 새로 정의된 메서드 구현 (중복 방지, 필요시 사용) ---
    // Controller에서 이 메서드를 호출한다면, 내부적으로 searchRestaurants를 호출하도록 합니다.
    @Override
    public Page<RestaurantEntity> searchByKeywordWithDistance(String keyword, BigDecimal lat, BigDecimal lng, int page, int size) {
        return searchRestaurants(keyword, lat, lng, page, size);
    }

    // Controller에서 이 메서드를 호출한다면, 내부적으로 searchRestaurantsByTagAndDistrict를 호출하도록 합니다.
    @Override
    public Page<RestaurantEntity> searchByTagWithDistanceAndDistrict(String tag, String district, BigDecimal lat, BigDecimal lng, int page, int size) {
        return searchRestaurantsByTagAndDistrict(tag, district, lat, lng, page, size);
    }

    // Haversine 공식을 이용한 거리 계산
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371000; // 지구 반지름 (미터)
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c; // 🚩 미터 단위 거리 반환
    }
}