package com.culinary.userservice.recipe.service;

import com.culinary.userservice.ingredient.service.IngredientService;
import com.culinary.userservice.recipe.dto.contains.ContainsDTO;
import com.culinary.userservice.recipe.mapper.ContainsMapper;
import com.culinary.userservice.recipe.model.Contains;
import com.culinary.userservice.recipe.repository.ContainsRepository;
import com.culinary.userservice.user.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContainsService {

    private final ContainsRepository containsRepository;
    private final RecipeService recipeService;
    private final IngredientService ingredientService;

    @Transactional
    public ContainsDTO createContains(ContainsDTO containsDTO) {
        Contains contains = createContainsEntity(containsDTO);
        contains = containsRepository.save(contains);
        return ContainsMapper.toDto(contains);
    }

    public Contains createContainsEntity(ContainsDTO containsDTO) {
        return ContainsMapper.toEntity(containsDTO,
                recipeService.getRecipeEntityById(containsDTO.getRecipeId()),
                ingredientService.findEntityById(containsDTO.getIngredientId()));
    }


    @Transactional
    public ContainsDTO updateContains(int id, ContainsDTO containsDTO) {
        Contains contains = containsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Contains not found"));
        contains.setMeasure(containsDTO.getMeasure());
        if (contains.getRecipe() != null && contains.getRecipe().getRecipeId() != containsDTO.getRecipeId()) {
            contains.setRecipe(recipeService.getRecipeEntityById(containsDTO.getRecipeId()));
        }
        if (contains.getIngredient() != null && contains.getIngredient().getIngredientId() != containsDTO.getIngredientId()) {
            contains.setIngredient(ingredientService.findEntityById(containsDTO.getIngredientId()));
        }

        contains = containsRepository.save(contains);
        return ContainsMapper.toDto(contains);
    }

    @Transactional
    public void deleteContains(int id) {
        containsRepository.deleteById(id);
    }

    public ContainsDTO getContainsById(int id) {
        Contains contains = containsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Contains not found"));
        return ContainsMapper.toDto(contains);
    }

    public List<ContainsDTO> getAllContains() {
        return containsRepository.findAll().stream()
                .map(ContainsMapper::toDto)
                .collect(Collectors.toList());
    }
}