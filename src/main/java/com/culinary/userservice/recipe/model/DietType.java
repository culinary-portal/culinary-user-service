package com.culinary.userservice.recipe.model;

import com.culinary.userservice.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @ManyToMany(mappedBy = "preferredDiets")
    private Set<User> users = new HashSet<>();

    @Override
    public String toString() {
        return "DietType{" +
                "dietType='" + dietType + '\'' +
                ", dietTypeId=" + dietTypeId +
                '}';
    }
}
