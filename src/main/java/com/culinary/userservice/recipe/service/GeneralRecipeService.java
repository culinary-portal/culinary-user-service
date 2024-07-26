package com.culinary.userservice.recipe.service;

import com.culinary.userservice.recipe.dto.GeneralRecipeDTO;
import com.culinary.userservice.recipe.mapper.GeneralRecipeMapper;
import com.culinary.userservice.recipe.model.GeneralRecipe;
import com.culinary.userservice.recipe.repository.GeneralRecipeRepository;
import com.culinary.userservice.user.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GeneralRecipeService {

    private final GeneralRecipeRepository generalRecipeRepository;

    @Transactional
    public GeneralRecipeDTO createGeneralRecipe(GeneralRecipeDTO generalRecipeDTO) {
        GeneralRecipe generalRecipe = GeneralRecipeMapper.toEntity(generalRecipeDTO, new ArrayList<>());
        generalRecipe = generalRecipeRepository.save(generalRecipe);
        return GeneralRecipeMapper.toDto(generalRecipe);
    }

    @Transactional
    public GeneralRecipeDTO updateGeneralRecipe(int id, GeneralRecipeDTO generalRecipeDTO) {
        GeneralRecipe generalRecipe = generalRecipeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("GeneralRecipe not found"));
        generalRecipe = GeneralRecipeMapper.updateEntity(generalRecipe, generalRecipeDTO, new ArrayList<>());
        generalRecipeRepository.save(generalRecipe);
        return GeneralRecipeMapper.toDto(generalRecipe);
    }

    @Transactional
    public void deleteGeneralRecipe(int id) {
        generalRecipeRepository.deleteById(id);
    }

    public GeneralRecipe getGeneralRecipeEntityById(int id) {
        return generalRecipeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("GeneralRecipe not found"));
    }

    public GeneralRecipeDTO getGeneralRecipeById(int id) {
        return GeneralRecipeMapper.toDto(getGeneralRecipeEntityById(id));
    }

    public List<GeneralRecipeDTO> getAllGeneralRecipes() {
        return generalRecipeRepository.findAll().stream()
                .map(GeneralRecipeMapper::toDto)
                .collect(Collectors.toList());
    }
}