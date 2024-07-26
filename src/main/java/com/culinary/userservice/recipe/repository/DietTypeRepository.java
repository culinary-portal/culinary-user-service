package com.culinary.userservice.recipe.repository;

import com.culinary.userservice.recipe.model.DietType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DietTypeRepository extends JpaRepository<DietType, Integer> {
    Optional<DietType> findDietTypeByDietType(String dietType);
}
