package com.culinary.userservice.recipe.repository;

import com.culinary.userservice.recipe.model.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavouriteRepository extends JpaRepository<Favorite, Long> {
}
