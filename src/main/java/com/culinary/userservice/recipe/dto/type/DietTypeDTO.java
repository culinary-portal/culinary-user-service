package com.culinary.userservice.recipe.dto.type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DietTypeDTO {
    private long dietTypeId;
    private String dietType;
}
