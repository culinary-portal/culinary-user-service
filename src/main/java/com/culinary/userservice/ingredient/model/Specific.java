package com.culinary.userservice.ingredient.model;

import com.culinary.userservice.user.model.User;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "specific")
public class Specific {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "specific_id")
    private long specificId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_ingredient", referencedColumnName = "ingredient_id")
    private Ingredient ingredient;

    @Basic
    @Column(name = "likes")
    private Boolean likes;

    @Override
    public String toString() {
        return "Specific{" +
                "likes=" + likes +
                ", ingredient=" + ingredient +
                ", specificId=" + specificId +
                '}';
    }
}
