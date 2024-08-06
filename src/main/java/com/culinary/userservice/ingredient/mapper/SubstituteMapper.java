package com.culinary.userservice.ingredient.mapper;

import com.culinary.userservice.ingredient.dto.substitute.GetSubstituteDTO;
import com.culinary.userservice.ingredient.dto.substitute.PutSubstituteDTO;
import com.culinary.userservice.ingredient.dto.substitute.SubstituteDTO;
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

    public static GetSubstituteDTO toGetDto(Substitute substitute) {
        return GetSubstituteDTO.builder()
                .substituteId(substitute.getSubstituteId())
                .ingredient1(IngredientMapper.toDto(substitute.getIngredient1()))
                .ingredient2(IngredientMapper.toDto(substitute.getIngredient2()))
                .proportionI1I2(substitute.getProportionI1I2())
                .build();
    }

    public Substitute toEntity(PutSubstituteDTO dto) {
        Ingredient ingredient1 = ingredientRepository.findById(dto.getIngredient1Id()).orElseThrow(() -> new NotFoundException("Ingredient not found"));
        Ingredient ingredient2 = ingredientRepository.findById(dto.getIngredient2Id()).orElseThrow(() -> new NotFoundException("Ingredient not found"));
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

    public void updateEntityFromDto(Substitute substitute, PutSubstituteDTO dto) {
        if (substitute != null && dto != null) {
            Ingredient ingredient1 = ingredientRepository.findById(dto.getIngredient1Id())
                    .orElseThrow(() -> new NotFoundException("Ingredient1 not found"));
            Ingredient ingredient2 = ingredientRepository.findById(dto.getIngredient2Id())
                    .orElseThrow(() -> new NotFoundException("Ingredient2 not found"));

            substitute.setIngredient1(ingredient1);
            substitute.setIngredient2(ingredient2);
            substitute.setProportionI1I2(dto.getProportionI1I2());
        }
    }

}