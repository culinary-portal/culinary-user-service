package com.culinary.userservice.recipe.model;

import com.culinary.userservice.user.model.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    @JoinColumn(name = "general_recipe_id")
    private GeneralRecipe generalRecipe;
    @Basic
    @Column(name = "rating")
    private Integer rating;
    @Basic
    @Column(name = "opinion")
    private String opinion;

    @Override
    public String toString() {
        return "Review{" +
                "reviewId=" + reviewId +
                ", opinion='" + opinion + '\'' +
                ", rating=" + rating +
                '}';
    }
}
