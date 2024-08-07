package com.culinary.userservice.recipe.service;

import com.culinary.userservice.recipe.dto.general.GeneralRecipeDTO;
import com.culinary.userservice.recipe.dto.general.GetGeneralRecipeDTO;
import com.culinary.userservice.recipe.dto.general.PutGeneralRecipeDTO;
import com.culinary.userservice.recipe.mapper.GeneralRecipeMapper;
import com.culinary.userservice.recipe.model.GeneralRecipe;
import com.culinary.userservice.recipe.model.Recipe;
import com.culinary.userservice.recipe.repository.GeneralRecipeRepository;
import com.culinary.userservice.user.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.culinary.userservice.recipe.mapper.GeneralRecipeMapper.updateEntity;
import static com.culinary.userservice.recipe.util.RecipeUtils.countCalories;
import static java.util.stream.Collectors.toList;

@Transactional
@Service
@RequiredArgsConstructor
public class GeneralRecipeService {

    private final GeneralRecipeRepository generalRecipeRepository;
    private final RecipeService recipeService;

    public GetGeneralRecipeDTO createGeneralRecipe(PutGeneralRecipeDTO dto) {
        GeneralRecipe entity = new GeneralRecipe();

        Recipe baseRecipe = recipeService.createRecipe(dto.getBaseRecipe(), entity);

        entity.setUsersWhoFavorited(new HashSet<>());
        entity.setRecipes(new ArrayList<>());
        entity.setReviews(new ArrayList<>());
        updateEntity(entity, dto, baseRecipe);

        generalRecipeRepository.save(entity);

        return GeneralRecipeMapper.toGetDTO(entity);
    }

    public GetGeneralRecipeDTO updateGeneralRecipe(int id, PutGeneralRecipeDTO dto) {
        GeneralRecipe generalRecipe = generalRecipeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("GeneralRecipe not found"));
        generalRecipe = updateEntity(generalRecipe, dto,
                recipeService.updateRecipe(generalRecipe.getBaseRecipe().getRecipeId(), dto.getBaseRecipe()));
        generalRecipeRepository.save(generalRecipe);
        return GeneralRecipeMapper.toGetDTO(generalRecipe);
    }

    public void deleteGeneralRecipe(int id) {
        generalRecipeRepository.deleteById(id);
    }

    public GeneralRecipe getGeneralRecipeEntityById(int id) {
        return generalRecipeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("GeneralRecipe not found"));
    }

    public GetGeneralRecipeDTO getGeneralRecipeById(int id) {
        return GeneralRecipeMapper.toGetDTO(getGeneralRecipeEntityById(id));
    }

    public List<GeneralRecipeDTO> getAllGeneralRecipes() {
        return generalRecipeRepository.findAll().stream()
                .map(GeneralRecipeMapper::toDto)
                .collect(toList());
    }

    public List<GetGeneralRecipeDTO> getFilteredGeneralRecipes(Optional<String> name,
                                                               Optional<String> mealType,
                                                               Optional<Integer> maxCalories,
                                                               Optional<String> dietType) {
        List<GeneralRecipe> allRecipes;

        if (name.isPresent()) allRecipes = generalRecipeRepository.findByNameRegex(name.get());
        else allRecipes = generalRecipeRepository.findAll();


        Predicate<GeneralRecipe> combinedPredicate = recipe -> true;

        if (mealType.isPresent()) {
            combinedPredicate = combinedPredicate.and(recipe ->
                    recipe.getMealType().toString().equalsIgnoreCase(mealType.get().trim()));
        }

        if (maxCalories.isPresent()) {
            combinedPredicate = combinedPredicate.and(recipe ->
                    countCalories(recipe.getBaseRecipe().getContains()) < maxCalories.get());

        }
        if (dietType.isPresent()) {
            String dietTypeStr = dietType.get().toUpperCase().trim();
            combinedPredicate = combinedPredicate.and(recipe ->
                    recipe.getBaseRecipe().getDietType().getDietType().equalsIgnoreCase(dietTypeStr));
        }

        return allRecipes.stream()
                .filter(combinedPredicate)
                .map(GeneralRecipeMapper::toGetDTO)
                .collect(Collectors.toList());
    }
}