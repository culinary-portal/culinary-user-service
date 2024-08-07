package com.culinary.userservice.ingredient.model;

import com.culinary.userservice.recipe.model.Contains;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Table(name = "ingredient")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Ingredient {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ingredient_id")
    private int ingredientId;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "fat")
    private Double fat;
    @Basic
    @Column(name = "protein")
    private Double protein;
    @Basic
    @Column(name = "carbohydrate")
    private Double carbohydrate;
    @Basic
    @Column(name = "kcal")
    private Double kcal;
    @Basic
    @Column(name = "is_vegan")
    private Boolean isVegan;

    @Basic
    @Column(name = "is_gluten_free")
    private Boolean isGlutenFree;

    @OneToMany(mappedBy = "ingredient")
    private List<Contains> contains;

    @OneToMany(mappedBy = "ingredient1")
    private List<Substitute> substitutes1;

    @OneToMany(mappedBy = "ingredient2")
    private List<Substitute> substitutes2;

    @OneToMany(mappedBy = "ingredient")
    private List<Specific> specifics;

    @Override
    public String toString() {
        return "Ingredient{" +
                "isGlutenFree=" + isGlutenFree +
                ", isVegan=" + isVegan +
                ", kcal=" + kcal +
                ", carbohydrate=" + carbohydrate +
                ", protein=" + protein +
                ", fat=" + fat +
                ", name='" + name + '\'' +
                ", ingredientId=" + ingredientId +
                '}';
    }
}
