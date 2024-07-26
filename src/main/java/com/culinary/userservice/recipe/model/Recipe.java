package com.culinary.userservice.recipe.model;

import com.culinary.userservice.user.model.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @ManyToOne
    @JoinColumn(name = "diet_type_id", referencedColumnName = "diet_type_id")
    private DietType dietType;

    @OneToMany(mappedBy = "recipe")
    private List<Contains> contains;

    @OneToMany(mappedBy = "recipe")
    private List<Review> reviews;

    @ManyToMany(mappedBy = "favoriteRecipes")
    private Set<User> usersWhoFavorited = new HashSet<>();
}
