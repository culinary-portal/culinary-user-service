package com.culinary.userservice.recipe.model;

import com.culinary.userservice.user.model.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "recipe")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id")
    private long recipeId;

    @ManyToOne
    @JoinColumn(name = "general_recipe_id", referencedColumnName = "general_recipe_id")
    private GeneralRecipe generalRecipe;

    @Basic
    @Column(name = "name")
    private String name;

    @Lob
    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "diet_type_id", referencedColumnName = "diet_type_id")
    private DietType dietType;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private List<Contains> contains;

    @ManyToMany(mappedBy = "modifiedRecipes", cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<User> usersWhoModified = new HashSet<>();

    @Override
    public String toString() {
        return "Recipe{" +
                "description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", generalRecipeId=" + recipeId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return recipeId == recipe.recipeId && Objects.equals(generalRecipe, recipe.generalRecipe)
                && Objects.equals(name, recipe.name) && Objects.equals(description, recipe.description)
                && Objects.equals(dietType, recipe.dietType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipeId, generalRecipe, name, description);
    }
}