package com.culinary.userservice.ingridient.model;

import com.culinary.userservice.ingridient.Specific;
import com.culinary.userservice.recipe.diet.Contains;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "ingredient")
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
}
