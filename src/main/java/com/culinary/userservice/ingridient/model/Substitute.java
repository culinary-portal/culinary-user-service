package com.culinary.userservice.ingridient.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "substitute")
public class Substitute {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "substitute_id")
    private int substituteId;
    @ManyToOne
    @JoinColumn(name = "ingredient1_id")
    private Ingredient ingredient1;
    @ManyToOne
    @JoinColumn(name = "ingredient2_id")
    private Ingredient ingredient2;
    @Basic
    @Column(name = "proportion_i1_i2")
    private Float proportionI1I2;
}
