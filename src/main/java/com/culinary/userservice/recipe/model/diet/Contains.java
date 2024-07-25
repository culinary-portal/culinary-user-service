package com.culinary.userservice.recipe.model.diet;

import com.culinary.userservice.ingredient.model.Ingredient;
import com.culinary.userservice.recipe.model.Recipe;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
