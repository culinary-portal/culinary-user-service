package com.culinary.userservice.recipe.diet;

import com.culinary.userservice.recipe.model.Recipe;
import com.culinary.userservice.user.model.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "review")
public class Review {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "review_id")
    private int reviewId;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;
    @Basic
    @Column(name = "rating")
    private Integer rating;
    @Basic
    @Column(name = "opinion")
    private String opinion;
}
