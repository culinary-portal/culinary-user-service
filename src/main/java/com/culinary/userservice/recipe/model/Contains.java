package com.culinary.userservice.recipe.model;

import com.culinary.userservice.ingredient.model.Ingredient;
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
    @Column(name = "measure")
    private String measure;
    @Basic
    @Column(name = "amount")
    private float amount;
    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;
    @ManyToOne
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;
}
