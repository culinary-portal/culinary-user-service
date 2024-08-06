package com.culinary.userservice.ingredient.repository;

import com.culinary.userservice.ingredient.model.Substitute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubstituteRepository extends JpaRepository<Substitute, Integer> {
    @Query("SELECT s FROM Substitute s WHERE s.ingredient1.ingredientId = ?1 OR s.ingredient2.ingredientId = ?1")
    List<Substitute> findSubstituteByIngredientId(int ingredientId);
}
