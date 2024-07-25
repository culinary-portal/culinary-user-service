package com.culinary.userservice.recipe.model;

import com.culinary.userservice.recipe.model.diet.Contains;
import com.culinary.userservice.recipe.model.diet.Favorite;
import com.culinary.userservice.recipe.model.diet.GeneralRecipe;
import com.culinary.userservice.recipe.model.diet.Review;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "recipe")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id")
    private int recipeId;

    @ManyToOne
    @JoinColumn(name = "general_recipe_id", referencedColumnName = "general_recipe_id")
    private GeneralRecipe generalRecipe;

    @Basic
    @Column(name = "photo_url")
    private String protoUrl;
    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "description")
    private String description;

    @Basic
    @Column(name = "diet_type")
    private String dietType;

    @OneToMany(mappedBy = "recipe")
    private List<Contains> contains;

    @OneToMany(mappedBy = "recipe")
    private List<Review> reviews;

    @OneToMany(mappedBy = "recipe")
    private List<Favorite> favorites;
}
