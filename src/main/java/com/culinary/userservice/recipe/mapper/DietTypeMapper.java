package com.culinary.userservice.recipe.mapper;

import com.culinary.userservice.recipe.dto.DietTypeDTO;
import com.culinary.userservice.recipe.model.DietType;

public class DietTypeMapper {

    public static DietType toEntity(DietTypeDTO dietTypeDTO) {
        DietType dietType = new DietType();
        dietType.setDietType(dietTypeDTO.getDietType());
        return dietType;
    }

    public static DietTypeDTO toDto(DietType dietType) {
        return DietTypeDTO.builder()
                .dietTypeId(dietType.getDietTypeId())
                .dietType(dietType.getDietType())
                .build();
    }
}