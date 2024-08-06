package com.culinary.userservice.ingredient.dto.substitute;

import com.culinary.userservice.ingredient.dto.ingredient.IngredientDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetSubstituteDTO {
    private int substituteId;
    private IngredientDTO ingredient1;
    private IngredientDTO ingredient2;
    private Float proportionI1I2;
}
