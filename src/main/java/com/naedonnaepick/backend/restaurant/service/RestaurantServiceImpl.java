package com.naedonnaepick.backend.restaurant.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.naedonnaepick.backend.restaurant.db.RestaurantRepository;
import com.naedonnaepick.backend.restaurant.db.RestaurantTagsRepository;
import com.naedonnaepick.backend.restaurant.dto.RestaurantDTO;
import com.naedonnaepick.backend.restaurant.dto.RestaurantRecommendationDTO;
import com.naedonnaepick.backend.restaurant.dto.RestaurantWithDistanceDTO;
import com.naedonnaepick.backend.restaurant.entity.RestaurantEntity;
import com.naedonnaepick.backend.restaurant.entity.RestaurantTags;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantTagsRepository restaurantTagsRepository;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${flask.recommend.url}")
    private String flaskPredictUrl;

    private static final List<String> TAG_KEYS = List.of(
            "spicy", "valueForMoney", "kindness", "cleanliness", "atmosphere",
            "largePortions", "tasty", "waiting", "sweet", "salty",
            "savory", "freshness", "soloDining", "trendy", "parking"
    );

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

    public List<RestaurantRecommendationDTO> recommendByLocation(
            BigDecimal lat, BigDecimal lng,
            Map<String, Double> userTags,
            int minPrice, int maxPrice,
            String region
    ) {
        List<RestaurantEntity> baseList = restaurantRepository.findByLocationWithPriceRange(minPrice, maxPrice, region);
        double userLat = lat.doubleValue();
        double userLng = lng.doubleValue();

        List<RestaurantRecommendationDTO> allRecommendations = new ArrayList<>();
        List<Map<String, Object>> flaskInputList = new ArrayList<>();

        Map<Integer, RestaurantRecommendationDTO> dtoMap = new HashMap<>();

        for (RestaurantEntity restaurant : baseList) {
            if (restaurant.getLatitude() == null || restaurant.getLongitude() == null) continue;

            double distance = calculateDistance(
                    userLat, userLng,
                    restaurant.getLatitude().doubleValue(), restaurant.getLongitude().doubleValue()
            );

            RestaurantTags tags = restaurantTagsRepository.findById(restaurant.getRestaurantNo()).orElse(null);
            if (tags == null) continue;

            Map<String, Double> diffs = calculateAbsoluteTagDifferences(userTags, tags);
            Map<String, Object> flaskInput = buildFlaskInput(restaurant.getRestaurantNo(), diffs);

            flaskInputList.add(flaskInput);

            // 초기 probability = 0으로 생성
            RestaurantRecommendationDTO dto = new RestaurantRecommendationDTO(restaurant, distance, 0.0);
            allRecommendations.add(dto);
            dtoMap.put(restaurant.getRestaurantNo(), dto);
        }

        // ✅ Flask 서버로 요청 보내기
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<List<Map<String, Object>>> request = new HttpEntity<>(flaskInputList, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    flaskPredictUrl, HttpMethod.POST, request, String.class
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                List<Map<String, Object>> responseList = objectMapper.readValue(
                        response.getBody(), new TypeReference<>() {}
                );

                for (Map<String, Object> result : responseList) {
                    int no = (int) result.get("restaurant_no");
                    int sentiment = (int) result.get("predicted_sentiment");
                    double prob = ((Number) result.get("probability")).doubleValue();

                    if (sentiment == 1 && dtoMap.containsKey(no)) {
                        dtoMap.get(no).setProbability(prob); // 확률 업데이트
                    } else {
                        dtoMap.remove(no); // 추천 아님 → 제거
                    }
                }
            }

        } catch (Exception e) {
            log.error("❌ Flask 서버 예측 실패", e);
            // 실패 시 초기 DTO 리스트 중 probability == 0인 것만 반환할 수도 있음
        }

        // 최종 추천만 필터링하고, 거리 기준 정렬
        return dtoMap.values().stream()
                .sorted(Comparator
                        .comparingDouble(RestaurantRecommendationDTO::getProbability).reversed() // 확률 높은 순
                        .thenComparingDouble(RestaurantRecommendationDTO::getDistance))          // 동일 확률 시 가까운 순
                .limit(100)
                .collect(Collectors.toList());
    }

    @Override
    public List<RestaurantDTO> searchRestaurantsByName(String query) {
        List<RestaurantEntity> restaurants = restaurantRepository.findByNameContainingIgnoreCase(query);
        return restaurants.stream()
                .map(RestaurantDTO::fromEntity)
                .collect(Collectors.toList());
    }

    private String toSnakeCase(String input) {
        return input.replaceAll("([a-z])([A-Z]+)", "$1_$2").toLowerCase();
    }


    private Map<String, Object> buildFlaskInput(int restaurantNo, Map<String, Double> diffs) {
        Map<String, Object> input = new HashMap<>();
        input.put("restaurant_no", restaurantNo);

        for (String tag : TAG_KEYS) {
            String snakeTag = "input_" + toSnakeCase(tag);  // <-- 핵심
            input.put(snakeTag, diffs.getOrDefault(tag, 0.0));
        }

        return input;
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

    private Map<String, Double> calculateAbsoluteTagDifferences(Map<String, Double> userTags, RestaurantTags restaurantTags) {
        Map<String, Double> diffs = new HashMap<>();

        for (String tag : TAG_KEYS) {
            try {
                Field field = RestaurantTags.class.getDeclaredField(tag);
                field.setAccessible(true);
                Object value = field.get(restaurantTags);
                if (value instanceof Number && userTags.containsKey(tag)) {
                    double restaurantValue = ((Number) value).doubleValue();
                    double userValue = userTags.get(tag);
                    diffs.put(tag, Math.abs(userValue - restaurantValue));
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                System.err.println("태그 필드 오류: " + tag);
            }
        }

        return diffs;
    }
}