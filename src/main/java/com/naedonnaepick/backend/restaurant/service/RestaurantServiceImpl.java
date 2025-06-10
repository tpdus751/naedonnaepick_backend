package com.naedonnaepick.backend.restaurant.service;

import com.naedonnaepick.backend.restaurant.dao.RestaurantDAO;
import com.naedonnaepick.backend.restaurant.entity.RestaurantEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantDAO restaurantDAO;

    @Override
    public List<RestaurantEntity> getRecommendedRestaurants(String location, int minPrice, int maxPrice) {
        return restaurantDAO.findRestaurantsByLocationAndPrice(location, minPrice, maxPrice);
    }

    @Override
    public Page<RestaurantEntity> getRestaurantsBySearchText(String searchText, Pageable pageable) {
        return restaurantDAO.findRestaurantsBySearchText(searchText, pageable);
    }

    @Override
    public List<RestaurantEntity> findNearbyRestaurants(BigDecimal lat, BigDecimal lng) {
        List<RestaurantEntity> all = restaurantDAO.findNearby(lat, lng);
        return sortByDistance(lat, lng, all);
    }

    @Override
    public Page<RestaurantEntity> searchByTagWithDistance(String tag, BigDecimal lat, BigDecimal lng, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<RestaurantEntity> pageResult = restaurantDAO.searchByTag(tag, pageable);

        List<RestaurantEntity> withDistance = pageResult.getContent().stream()
                .peek(r -> r.setDistance(calculateDistance(
                        lat.doubleValue(), lng.doubleValue(),
                        r.getLatitude().doubleValue(), r.getLongitude().doubleValue())))
                .sorted(Comparator.comparing(RestaurantEntity::getDistance))
                .collect(Collectors.toList());

        return new PageImpl<>(withDistance, pageable, pageResult.getTotalElements());
    }

    @Override
    public Page<RestaurantEntity> searchByKeywordWithDistance(String keyword, BigDecimal lat, BigDecimal lng, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<RestaurantEntity> pageResult = restaurantDAO.searchByKeyword(keyword, pageable);

        List<RestaurantEntity> withDistance = pageResult.getContent().stream()
                .peek(r -> r.setDistance(calculateDistance(
                        lat.doubleValue(), lng.doubleValue(),
                        r.getLatitude().doubleValue(), r.getLongitude().doubleValue())))
                .sorted(Comparator.comparing(RestaurantEntity::getDistance))
                .collect(Collectors.toList());

        return new PageImpl<>(withDistance, pageable, pageResult.getTotalElements());
    }

    private List<RestaurantEntity> sortByDistance(BigDecimal lat, BigDecimal lng, List<RestaurantEntity> list) {
        return list.stream()
                .peek(r -> {
                    double dist = calculateDistance(
                            lat.doubleValue(), lng.doubleValue(),
                            r.getLatitude().doubleValue(), r.getLongitude().doubleValue()
                    );
                    r.setDistance(dist); // @Transient 필드 주입
                })
                .sorted(Comparator.comparing(RestaurantEntity::getDistance))
                .collect(Collectors.toList());
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}
