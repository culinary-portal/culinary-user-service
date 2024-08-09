package com.culinary.userservice.recipe.repository;

import com.culinary.userservice.recipe.model.GeneralRecipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GeneralRecipeRepository extends JpaRepository<GeneralRecipe, Long> {

    @Query("SELECT c FROM GeneralRecipe c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<GeneralRecipe> findByNameRegex(String name);
}
