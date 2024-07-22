package com.culinary.userservice.recipe.service;

import com.culinary.userservice.recipe.diet.Favorite;
import com.culinary.userservice.recipe.model.Recipe;
import com.culinary.userservice.user.model.User;
import com.culinary.userservice.user.repository.UserRepository;
import com.culinary.userservice.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final UserService userService;
    private final UserRepository userRepository;

    @Transactional
    public void assignRecipeToUser(Recipe recipe) {
        User user = userService.getLoggedUser();
        if (user == null) {
            throw new IllegalStateException("No logged in user found");
        }

        List<Favorite> favorites = user.getFavorites();
        if (favorites == null) {
            throw new IllegalStateException("Favorites list not initialized");
        }

        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setRecipe(recipe);

        favorites.add(favorite);
        userRepository.save(user);
    }
}

