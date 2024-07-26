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
@Table(name = "diet_type")
public class DietType {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "diet_type_id")
    private int dietTypeId;
    @Basic
    @Column(name = "diet_type")
    private String dietType;
    @OneToMany(mappedBy = "dietType")
    private List<Recipe> recipes;
}
