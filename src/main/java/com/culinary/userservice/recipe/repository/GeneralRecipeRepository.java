package com.culinary.userservice.recipe.repository;

import com.culinary.userservice.recipe.model.GeneralRecipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeneralRecipeRepository extends JpaRepository<GeneralRecipe, Integer> {
}
