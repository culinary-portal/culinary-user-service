package com.culinary.userservice.ingredient.repository;

import com.culinary.userservice.ingredient.model.Substitute;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubstituteRepository extends JpaRepository<Substitute, Integer> {
}
