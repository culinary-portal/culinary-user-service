package com.culinary.userservice.recipe.mapper;

import com.culinary.userservice.recipe.dto.type.DietTypeDTO;
import com.culinary.userservice.recipe.model.DietType;

public class DietTypeMapper {

    public static DietType toEntity(DietTypeDTO dietTypeDTO) {
        DietType dietType = new DietType();
        dietType.setDietType(dietTypeDTO.dietType());
        return dietType;
    }

    public static DietTypeDTO toDto(DietType dietType) {
        return new DietTypeDTO(
                dietType.getDietTypeId(),
                dietType.getDietType()
        );
    }
}