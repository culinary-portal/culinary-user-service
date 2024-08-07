package com.culinary.userservice.recipe.util;

import com.culinary.userservice.recipe.model.Contains;
import com.culinary.userservice.recipe.model.Review;

import java.util.List;

public class RecipeUtils {

    private RecipeUtils() {
        throw new UnsupportedOperationException("Cannot instantiate utils class");
    }

    public static int countCalories(List<Contains> contains) {
        if (contains == null || contains.isEmpty()) {
            return 0;
        }
        int sum = contains.stream()
                .mapToInt(contain -> (int) (contain.getIngredient().getKcal() * contain.getAmount() * 0.01))
                .sum();
        return sum;
    }

    public static double countFat(List<Contains> contains) {
        if (contains == null || contains.isEmpty()) {
            return 0d;
        }
        double sum = contains.stream()
                .mapToInt(contain -> (int) (contain.getIngredient().getFat() * contain.getAmount() * 0.01))
                .sum();
        return sum;
    }

    public static double countProtein(List<Contains> contains) {
        if (contains == null || contains.isEmpty()) {
            return 0d;
        }
        int sum = contains.stream()
                .mapToInt(contain -> (int) (contain.getIngredient().getProtein() * contain.getAmount() * 0.01))
                .sum();
        return sum;
    }

    public static double countCarbohydrates(List<Contains> contains) {
        if (contains == null || contains.isEmpty()) {
            return 0d;
        }
        double sum = contains.stream()
                .mapToInt(contain -> (int) (contain.getIngredient().getCarbohydrate() * contain.getAmount() * 0.01))
                .sum();
        return sum;
    }

    public static double countRating(List<Review> reviews) {

        if (reviews == null || reviews.isEmpty()) {
            return 0d;
        }

        double sum = reviews.stream()
                .mapToDouble(Review::getRating)
                .sum();

        return sum / reviews.size();
    }
}
