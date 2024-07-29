package com.culinary.userservice.user.dto;

import com.culinary.userservice.ingredient.dto.specific.SpecificDetailsDTO;
import com.culinary.userservice.recipe.dto.general.GeneralRecipeViewDTO;
import com.culinary.userservice.recipe.dto.recipe.RecipeContainsDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetailsDTO {
    private Long id;
    private String email;
    private String userName;
    private String photoUrl;
    private boolean enabled;
    private boolean credentialsNonExpired;
    private boolean accountNonExpired;
    private boolean locked;
    private Date birthdate;
    private Date createDate;
    private Set<SpecificDetailsDTO> specifics;
    private Set<RecipeContainsDTO> modifications = new HashSet<>();
    private Set<GeneralRecipeViewDTO> favoriteRecipes = new HashSet<>();
}
