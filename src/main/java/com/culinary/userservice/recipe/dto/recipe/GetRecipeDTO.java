package com.culinary.userservice.recipe.dto.recipe;

import com.culinary.userservice.recipe.dto.contains.GetContainsDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetRecipeDTO {
    private String name;
    private String description;
    private String dietType;
    private long generalRecipeId;
    private List<GetContainsDTO> contains;
}