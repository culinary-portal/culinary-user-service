package com.culinary.userservice.ingredient.dto.specific;

import com.culinary.userservice.ingredient.dto.ingredient.IngredientDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SpecificDetailsDTO {
    private int specificId;
    private long userId;
    private IngredientDTO ingredient;
    private Boolean likes;
}
