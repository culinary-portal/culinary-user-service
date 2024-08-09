package com.culinary.userservice.ingredient.repository;

import com.culinary.userservice.ingredient.model.Specific;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecificRepository extends JpaRepository<Specific, Long> {
}
