package com.culinary.userservice.ingredient.dto.substitute;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PutSubstituteDTO {
    private int ingredient1Id;
    private int ingredient2Id;
    private Float proportionI1I2;
}
