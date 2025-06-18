package com.naedonnaepick.backend.restaurant.db;

import com.naedonnaepick.backend.restaurant.entity.RestaurantTags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantTagsRepository extends JpaRepository<RestaurantTags, Integer> {
}
