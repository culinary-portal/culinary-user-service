package com.culinary.userservice.ingredient.service;

import com.culinary.userservice.ingredient.dto.IngredientDTO;
import com.culinary.userservice.ingredient.mapper.IngredientMapper;
import com.culinary.userservice.ingredient.model.Ingredient;
import com.culinary.userservice.ingredient.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    @Transactional(readOnly = true)
    public List<IngredientDTO> findAll() {
        return ingredientRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public IngredientDTO findById(int id) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ingredient not found"));
        return convertToDto(ingredient);
    }

    @Transactional
    public IngredientDTO save(IngredientDTO ingredientDto) {
        Ingredient ingredient = IngredientMapper.toEntity(ingredientDto);
        Ingredient savedIngredient = ingredientRepository.save(ingredient);
        return convertToDto(savedIngredient);
    }

    @Transactional
    public IngredientDTO update(int id, IngredientDTO ingredientDto) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ingredient not found"));
        IngredientMapper.updateEntityFromDto(ingredient, ingredientDto);
        Ingredient updatedIngredient = ingredientRepository.save(ingredient);
        return convertToDto(updatedIngredient);
    }

    @Transactional
    public void delete(int id) {
        ingredientRepository.deleteById(id);
    }

    private IngredientDTO convertToDto(Ingredient ingredient) {
        return new IngredientDTO(ingredient.getIngredientId(), ingredient.getName(), ingredient.getFat(), ingredient.getProtein(), ingredient.getCarbohydrate(), ingredient.getKcal(), ingredient.getIsVegan(), ingredient.getIsGlutenFree());
    }
}