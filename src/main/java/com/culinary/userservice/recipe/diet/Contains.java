package com.culinary.userservice.recipe.diet;

import com.culinary.userservice.ingridient.model.Ingredient;
import com.culinary.userservice.recipe.model.Recipe;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "contains")
public class Contains {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "contains_id")
    private int containsId;
    @Basic
    @Column(name = "name")
    private String name;
    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;
    @ManyToOne
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;
}
