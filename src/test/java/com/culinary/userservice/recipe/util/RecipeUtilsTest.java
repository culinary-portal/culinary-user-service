package com.culinary.userservice.recipe.util;

import com.culinary.userservice.ingredient.model.Ingredient;
import com.culinary.userservice.recipe.model.Contains;
import com.culinary.userservice.recipe.model.Review;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RecipeUtilsTest {

    @Test
    void testCountCalories() {
        Contains contains = mock(Contains.class);
        Ingredient ingredient = mock(Ingredient.class);
        when(contains.getIngredient()).thenReturn(ingredient);
        when(contains.getMeasure()).thenReturn("1");
        when(contains.getAmount()).thenReturn(100.0F);
        when(ingredient.getKcal()).thenReturn(200.0);

        List<Contains> containsList = Collections.singletonList(contains);
        int calories = RecipeUtils.countCalories(containsList);
        assertEquals(200, calories);
    }

    @Test
    void testCountFat() {
        Contains contains = mock(Contains.class);
        Ingredient ingredient = mock(Ingredient.class);
        when(contains.getIngredient()).thenReturn(ingredient);
        when(contains.getMeasure()).thenReturn("1");
        when(contains.getAmount()).thenReturn(100.0F);
        when(ingredient.getFat()).thenReturn(10.0);

        List<Contains> containsList = Collections.singletonList(contains);
        double fat = RecipeUtils.countFat(containsList);
        assertEquals(10.0, fat);
    }

    @Test
    void testCountProtein() {
        Contains contains = mock(Contains.class);
        Ingredient ingredient = mock(Ingredient.class);
        when(contains.getIngredient()).thenReturn(ingredient);
        when(contains.getMeasure()).thenReturn("1");
        when(contains.getAmount()).thenReturn(100f);
        when(ingredient.getProtein()).thenReturn(15.0);

        List<Contains> containsList = Collections.singletonList(contains);
        double protein = RecipeUtils.countProtein(containsList);
        assertEquals(15.0, protein);
    }

    @Test
    void testCountCarbohydrates() {
        Contains contains = mock(Contains.class);
        Ingredient ingredient = mock(Ingredient.class);
        when(contains.getIngredient()).thenReturn(ingredient);
        when(contains.getMeasure()).thenReturn("1");
        when(contains.getAmount()).thenReturn(100.0f);
        when(ingredient.getCarbohydrate()).thenReturn(20.0);

        List<Contains> containsList = Collections.singletonList(contains);
        double carbohydrates = RecipeUtils.countCarbohydrates(containsList);
        assertEquals(20.0, carbohydrates);
    }

    @Test
    void testCountRating() {
        Review review = mock(Review.class);
        when(review.getRating()).thenReturn(4);

        List<Review> reviews = Collections.singletonList(review);
        double rating = RecipeUtils.countRating(reviews);
        assertEquals(4, rating);
    }
}