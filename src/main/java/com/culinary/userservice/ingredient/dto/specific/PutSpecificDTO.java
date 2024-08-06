package com.culinary.userservice.ingredient.dto.specific;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PutSpecificDTO {
    private long userId;
    private int ingredientId;
    private Boolean likes;
}
