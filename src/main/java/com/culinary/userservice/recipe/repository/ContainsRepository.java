package com.culinary.userservice.recipe.repository;

import com.culinary.userservice.recipe.model.Contains;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContainsRepository extends JpaRepository<Contains, Integer>{
}
