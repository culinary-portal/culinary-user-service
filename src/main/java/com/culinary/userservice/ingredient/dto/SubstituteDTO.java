package com.culinary.userservice.ingredient.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubstituteDTO {
    private int substituteId;
    private int ingredient1Id;
    private int ingredient2Id;
    private Float proportionI1I2;
}