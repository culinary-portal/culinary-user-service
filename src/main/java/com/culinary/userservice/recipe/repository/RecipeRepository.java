package com.culinary.userservice.recipe.repository;

import com.culinary.userservice.recipe.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}