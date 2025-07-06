package com.naedonnaepick.backend.restaurant.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.naedonnaepick.backend.restaurant.dto.RestaurantRecommendationDTO;
import com.naedonnaepick.backend.restaurant.dto.RestaurantWithDistanceDTO;
import com.naedonnaepick.backend.restaurant.entity.RestaurantEntity;
import com.naedonnaepick.backend.restaurant.entity.RestaurantMenu;
import com.naedonnaepick.backend.restaurant.service.RestaurantMenuService;
import com.naedonnaepick.backend.restaurant.service.RestaurantService;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/restaurant")
@CrossOrigin(origins = "http://localhost:8081")
public class RestaurantAPIController {

    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private RestaurantMenuService restaurantMenuService;

    // 특정 ID의 음식점 정보를 조회하는 엔드포인트
    // GET /api/restaurant/{id}
    @GetMapping("/{id}")
    public ResponseEntity<RestaurantEntity> getRestaurantById(@PathVariable("id") int id) {
        Optional<RestaurantEntity> restaurant = restaurantService.findById(id);
        return restaurant.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ✅ 현재 위치 기반 추천, 지역구 기반 추천 동일
    @PostMapping("recommended/location")
    public ResponseEntity<List<RestaurantRecommendationDTO>> recommendByLocation(@RequestBody Map<String, Object> payload) {
        try {
            // ✅ 위치 정보
            double lat = ((Number) payload.get("lat")).doubleValue();
            double lng = ((Number) payload.get("lng")).doubleValue();
            BigDecimal latitude = BigDecimal.valueOf(lat);
            BigDecimal longitude = BigDecimal.valueOf(lng);

            // ✅ 가격대
            int minPrice = ((Number) payload.get("minPrice")).intValue();
            int maxPrice = ((Number) payload.get("maxPrice")).intValue();

            // ✅ 지역 이름 (null 허용)
            String region = (String) payload.getOrDefault("region", null);
            System.out.println("전달받은 지역 이름: " + region);

            // ✅ 태그 점수 파싱
            Map<String, Object> rawScores = (Map<String, Object>) payload.get("tagScores");
            Map<String, Double> tagScores = new HashMap<>();
            for (Map.Entry<String, Object> entry : rawScores.entrySet()) {
                if (entry.getValue() instanceof Number) {
                    tagScores.put(entry.getKey(), ((Number) entry.getValue()).doubleValue());
                }
            }

            System.out.println("받은 사용자 태그 점수: {}" + tagScores);

            // ✅ 추천 결과 받기
            List<RestaurantRecommendationDTO> recommended =
                    restaurantService.recommendByLocation(latitude, longitude, tagScores, minPrice, maxPrice, region);

            return ResponseEntity.ok(recommended);

        } catch (Exception e) {
            System.out.println("📛 추천 요청 처리 중 예외 발생" + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 기존 sendSearchRestaurants 메서드 (프론트엔드에서 사용 중인 경우)
    // 이 메서드는 거리 정렬이 안 됩니다. 아래 general 검색을 사용하는 것이 좋습니다.
    @GetMapping("/search")
    public List<RestaurantEntity> sendSearchRestaurants(
            @RequestParam(name = "searchText") String searchText,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return restaurantService.getRestaurantsBySearchText(searchText, pageable).getContent();
    }

    // 기존 sendRestaurantMenus 메서드
    @GetMapping("/menus")
    public List<RestaurantMenu> sendRestaurantMenus(
            @RequestParam(name = "restaurantNo") int restaurantNo
    ) {
        return restaurantMenuService.getRestaurantMenus(restaurantNo);
    }

    // NearbyListScreen 에서 모든 음식점을 거리순으로 가져올 때 사용 (페이징 포함)
    // GET /api/restaurant/nearby
    @GetMapping("/nearby")
    public ResponseEntity<Page<RestaurantWithDistanceDTO>> getNearbyRestaurants(
            @RequestParam BigDecimal lat,
            @RequestParam BigDecimal lng,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        try {
            Page<RestaurantWithDistanceDTO> result = restaurantService.findNearbyRestaurants(lat, lng, page, size);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 1. 태그 검색 (거리 계산 및 정렬 포함)
    // GET /api/restaurant/tag
    @GetMapping("/tag")
    public ResponseEntity<Page<RestaurantEntity>> searchByTag(
            @RequestParam String tag,
            @RequestParam String district,
            @RequestParam BigDecimal lat,
            @RequestParam BigDecimal lng,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        try {
            Page<RestaurantEntity> result = restaurantService.searchRestaurantsByTagAndDistrict(tag, district, lat, lng, page, size);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 2. 일반 키워드 검색 (거리 계산 및 정렬 포함)
    // GET /api/restaurant/general
    @GetMapping("/general")
    public ResponseEntity<Page<RestaurantEntity>> searchByKeyword(
            @RequestParam String keyword,
            @RequestParam BigDecimal lat,
            @RequestParam BigDecimal lng,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        try {
            Page<RestaurantEntity> result = restaurantService.searchRestaurants(keyword, lat, lng, page, size);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}