package com.culinary.userservice.recipe.diet;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "diet_type")
public class DietType {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "diet_type_id")
    private int dietTypeId;
    @Basic
    @Column(name = "diet_type")
    private String dietType;
}
