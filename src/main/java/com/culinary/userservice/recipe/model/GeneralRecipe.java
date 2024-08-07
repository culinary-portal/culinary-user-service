package com.culinary.userservice.recipe.model;

import com.culinary.userservice.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "general_recipe")
public class GeneralRecipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "general_recipe_id")
    private Long generalRecipeId;

    @Basic
    @Column(name = "name")
    private String name;

    @Lob
    @Column(name = "photo_url")
    private String photoUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "meal_type", nullable = false)
    private MealType mealType;

    @Lob
    @Column(name = "description")
    private String description;

    @Lob
    @Column(name = "steps")
    private String steps;

    @OneToOne
    @JoinColumn(name = "base_recipe_id", referencedColumnName = "recipe_id")
    private Recipe baseRecipe;

    @OneToMany(mappedBy = "generalRecipe", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<Recipe> recipes;

    @OneToMany(mappedBy = "generalRecipe", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Review> reviews;

    @ManyToMany(mappedBy = "favoriteRecipes")
    private Set<User> usersWhoFavorited = new HashSet<>();

    @Override
    public String toString() {
        return "GeneralRecipe{" +
                "description='" + description + '\'' +
                ", mealType=" + mealType +
                ", photoUrl='" + photoUrl + '\'' +
                ", name='" + name + '\'' +
                ", generalRecipeId=" + generalRecipeId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeneralRecipe that = (GeneralRecipe) o;
        return Objects.equals(generalRecipeId, that.generalRecipeId) && Objects.equals(name, that.name)
                && Objects.equals(photoUrl, that.photoUrl) && mealType == that.mealType
                && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(generalRecipeId, name, photoUrl, mealType, description);
    }

    public enum MealType {
        BREAKFAST, LUNCH, DINNER, SUPPER, DESSERT
    }
}
