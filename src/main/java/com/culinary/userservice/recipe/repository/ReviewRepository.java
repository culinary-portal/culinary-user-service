package com.culinary.userservice.recipe.repository;

import com.culinary.userservice.recipe.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
}
