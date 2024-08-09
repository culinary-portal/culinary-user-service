package com.culinary.userservice.ingredient.dto.substitute;

public record SubstituteDTO(
        long substituteId,
        long ingredient1Id,
        long ingredient2Id,
        Float proportionI1I2
) {
}
