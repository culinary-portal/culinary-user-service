package com.culinary.userservice.ingredient.mapper;

import com.culinary.userservice.ingredient.dto.SubstituteDTO;
import com.culinary.userservice.ingredient.model.Ingredient;
import com.culinary.userservice.ingredient.model.Substitute;
import com.culinary.userservice.ingredient.repository.IngredientRepository;
import com.culinary.userservice.user.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SubstituteMapper {

    private final IngredientRepository ingredientRepository;


    public Substitute toEntity(SubstituteDTO dto) {
        Ingredient ingredient1 = ingredientRepository.findById(dto.getIngredient1Id()).orElse(null);
        Ingredient ingredient2 = ingredientRepository.findById(dto.getIngredient2Id()).orElse(null);
        Substitute substitute = new Substitute();
        substitute.setIngredient1(ingredient1);
        substitute.setIngredient2(ingredient2);
        substitute.setProportionI1I2(dto.getProportionI1I2());
        return substitute;
    }

    public SubstituteDTO toDto(Substitute substitute) {
        return SubstituteDTO.builder()
                .substituteId(substitute.getSubstituteId())
                .ingredient1Id(substitute.getIngredient1().getIngredientId())
                .ingredient2Id(substitute.getIngredient2().getIngredientId())
                .proportionI1I2(substitute.getProportionI1I2())
                .build();
    }

    public void updateEntityFromDto(Substitute substitute, SubstituteDTO substituteDto) {
        if (substitute != null && substituteDto != null) {
            Ingredient ingredient1 = ingredientRepository.findById(substituteDto.getIngredient1Id())
                    .orElseThrow(() -> new NotFoundException("Ingredient1 not found"));
            Ingredient ingredient2 = ingredientRepository.findById(substituteDto.getIngredient2Id())
                    .orElseThrow(() -> new NotFoundException("Ingredient2 not found"));

            substitute.setIngredient1(ingredient1);
            substitute.setIngredient2(ingredient2);
            substitute.setProportionI1I2(substituteDto.getProportionI1I2());
        }
    }

}