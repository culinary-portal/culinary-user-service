package com.culinary.userservice.ingredient.service;

import com.culinary.userservice.ingredient.dto.ingredient.IngredientDTO;
import com.culinary.userservice.ingredient.dto.ingredient.PutIngredientDTO;
import com.culinary.userservice.ingredient.mapper.IngredientMapper;
import com.culinary.userservice.ingredient.model.Ingredient;
import com.culinary.userservice.ingredient.repository.IngredientRepository;
import com.culinary.userservice.user.exception.NotFoundException;
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
                .map(IngredientMapper::toDto)
                .collect(Collectors.toList());
    }

    public IngredientDTO findById(long id) {
        return IngredientMapper.toDto(findEntityById(id));
    }

    @Transactional(readOnly = true)
    public Ingredient findEntityById(long id) {
        return ingredientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ingredient not found"));
    }

    @Transactional
    public IngredientDTO save(PutIngredientDTO ingredientDto) {
        Ingredient ingredient = IngredientMapper.toEntity(ingredientDto);
        Ingredient savedIngredient = ingredientRepository.save(ingredient);
        return IngredientMapper.toDto(savedIngredient);
    }

    @Transactional
    public IngredientDTO update(long id, PutIngredientDTO ingredientDto) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ingredient not found"));
        IngredientMapper.updateEntityFromDto(ingredient, ingredientDto);
        Ingredient updatedIngredient = ingredientRepository.save(ingredient);
        return IngredientMapper.toDto(updatedIngredient);
    }

    @Transactional
    public void delete(long id) {
        ingredientRepository.deleteById(id);
    }
}