package com.culinary.userservice.recipe.diet;

import com.culinary.userservice.recipe.model.Recipe;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "general_recipe")
public class GeneralRecipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "general_recipe_id")
    private Long generalRecipeId;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "is_breakfast")
    private Boolean isBreakfast;

    @Basic
    @Column(name = "is_dinner")
    private Boolean isDinner;

    @Basic
    @Column(name = "is_lunch")
    private Boolean isLunch;

    @Basic
    @Column(name = "is_supper")
    private Boolean isSupper;

    @OneToMany(mappedBy = "generalRecipe")
    private List<Recipe> recipes;
}
