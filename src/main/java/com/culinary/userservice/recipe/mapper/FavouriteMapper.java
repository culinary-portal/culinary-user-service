package com.culinary.userservice.recipe.mapper;

import com.culinary.userservice.recipe.dto.FavouriteDTO;
import com.culinary.userservice.recipe.model.Recipe;
import com.culinary.userservice.recipe.model.diet.Favorite;
import com.culinary.userservice.user.model.User;


public class FavouriteMapper {

    public static Favorite toEntity(FavouriteDTO favouriteDTO, User user, Recipe recipe) {
        Favorite favourite = new Favorite();
        favourite.setUser(user);
        favourite.setRecipe(recipe);
        return favourite;
    }

    public static FavouriteDTO toDto(Favorite favorite) {
        return FavouriteDTO.builder()
                .userId(favorite.getFavoriteId())
                .recipeId(favorite.getRecipe().getRecipeId())
                .build();
    }
}