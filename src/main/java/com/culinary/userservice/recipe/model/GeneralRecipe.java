package com.culinary.userservice.recipe.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

    @Basic
    @Column(name = "photo_url")
    private String photoUrl;

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

    @OneToMany(mappedBy = "generalRecipe", fetch = FetchType.EAGER)
    private List<Recipe> recipes;
}
