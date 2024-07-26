package com.culinary.userservice.ingredient.repository;

import com.culinary.userservice.ingredient.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {
}
