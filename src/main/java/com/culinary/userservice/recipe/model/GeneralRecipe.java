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
@Table(name = "general_recipe")
public class GeneralRecipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "general_recipe_id")
    private Long generalRecipeId;

    @Basic
    @Column(name = "name")
    private String name;

    @Lob
    @Column(name = "photo_url")
    private String photoUrl;

    @Basic
    @Column(name = "is_breakfast")
    private Boolean isBreakfast;

    @Basic
    @Column(name = "is_dinner")
    private Boolean isDinner;

    @Basic
    @Column(name = "is_lunch")
    private Boolean isLunch;

    @Basic
    @Column(name = "is_supper")
    private Boolean isSupper;

    @Lob
    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "generalRecipe", fetch = FetchType.EAGER)
    private List<Recipe> recipes;

    @OneToMany(mappedBy = "generalRecipe", fetch = FetchType.EAGER)
    private List<Review> reviews;

    @ManyToMany(mappedBy = "favoriteRecipes")
    private Set<User> usersWhoFavorited = new HashSet<>();
}
